package com.example.avocadowallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.avocadowallet.Clases.statics.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicReference;

import static android.app.PendingIntent.getActivity;

public class VistaUsuario extends AppCompatActivity {
String valores = "";
TextView TVNombre;
TextView TVMonto;
Spinner SpinCambio;
ImageView IVTransfer;
ImageView IVMigrar;
ImageView IVInfo;
ImageView IVConfig;
Button btnActualizar;
ProgressBar pgActualizar;
String userName="";
String password="";
String userId;
double montoETH;
double montoMXN;
double montoUSD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_usuario);
        init();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //Toast.makeText(getApplicationContext(), "Salir", Toast.LENGTH_SHORT).show();
        mostrarDialogo();
    }

    public AlertDialog mostrarDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getApplicationContext());

        builder.setTitle("¿Estás seguro que deseas salir de la aplicación?")
                .setMessage("Si sales, la sesión se cerrará")
                .setPositiveButton("Salir",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                finish();
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        return builder.create();
    }



    public void init(){
        mostrarDialogo();
        TVNombre = (TextView)findViewById(R.id.TVNombreSesion);
        TVMonto = (TextView)findViewById(R.id.TVMontoSesion);
        SpinCambio = (Spinner)findViewById(R.id.SpinCambio);
        IVTransfer = (ImageView)findViewById(R.id.IVTransferir);
        IVMigrar = (ImageView)findViewById(R.id.IVMigrar);
        IVInfo = (ImageView)findViewById(R.id.IVInfo);
        IVConfig = (ImageView)findViewById(R.id.IVConfig);
        btnActualizar = (Button)findViewById(R.id.btnActualizar);
        pgActualizar = (ProgressBar)findViewById(R.id.PGActualizar);
        pgActualizar.setVisibility(View.GONE);

        Bundle datos = this.getIntent().getExtras();
        valores = datos.getString("response");

        try {
            JSONObject jobject = new JSONObject(valores);
            JSONObject json = jobject.getJSONArray("datos").getJSONObject(0);
            Log.e("json", json.toString());
            userName = json.getString("Username");
            password = json.getString("Password");
            userId = json.getString("idUsuario");

            String nombres[] = json.getString("Name").split(" ");
            String apellido [] = json.getString("Lastname").split(" ");

            //TVNombre.setText(json.getString("Name")+" "+json.getString("Lastname"));
            TVNombre.setText(nombres[0]+" "+apellido[0]);
            TVMonto.setText(json.getString("monto")+" ETH");
            montoETH = Double.parseDouble(json.getString("monto"));
            Log.e("MONTO::", ""+montoETH);
            //TVNombre.setText(user.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DecimalFormat df = new DecimalFormat("#.00");


        montoMXN = Double.parseDouble(df.format(convertEtherTo("MXN", montoETH)));
        montoUSD = Double.parseDouble(df.format(convertEtherTo("USD", montoETH)));

        IVTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Transferir.class);
                i.putExtra("valores",valores);
                i.putExtra("ETH", montoETH);
                i.putExtra("MXN", montoMXN);
                i.putExtra("USD", montoUSD);
                startActivity(i);
            }
        });

        IVMigrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Migrar.class);
                startActivity(i);
            }
        });

        IVInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Transacciones.class);
                i.putExtra("userId",userId);
                startActivity(i);
            }
        });


        IVConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), config.class);
                i.putExtra("valores",valores);
                startActivity(i);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });



        SpinCambio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Item Selected::: ",SpinCambio.getSelectedItem().toString());
                if(SpinCambio.getSelectedItem().toString().equals("ETH")){
                    TVMonto.setText(""+montoETH+" ETH");
                }else {
                    getCambioUpdated(SpinCambio.getSelectedItem().toString(), montoETH);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("Item Selected::: ","NADA SELECCIONADO");
            }
        });


        /*
        SpinCambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crear el evento del Spinner al seleccionar el elemento llamar al metoso que le pega a la api
                //convertEtherTo(SpinCambio.getSelectedItem().toString());
            }
        });*/



    }

    public void actualizar(){
        pgActualizar.setVisibility(View.VISIBLE);
        conexion();
    }


    public void conexion(){
        try {
            //final TextView textView = (TextView) findViewById(R.id.textView4);
            // ...

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url =Values.URL+"sesion.php?user="+userName+"&pwd="+password;
            queue.getCache().clear();

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                // Display the first 500 characters of the response string.

                pgActualizar.setVisibility(View.GONE);
                Log.e("CONEXION", response);
                if(response.length()<3){
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    //Contraseña o usuario incorrectos
                }else {
                    try {
                        JSONObject jobject = new JSONObject(response);
                        JSONObject json = jobject.getJSONArray("datos").getJSONObject(0);
                        Log.e("json", json.toString());
                        userName = json.getString("Username");
                        password = json.getString("Password");
                        String nombres[] = json.getString("Name").split(" ");
                        String apellido [] = json.getString("Lastname").split(" ");
                        valores = response;
                        //TVNombre.setText(json.getString("Name")+" "+json.getString("Lastname"));
                        TVNombre.setText(nombres[0]+" "+apellido[0]);
                        TVMonto.setText(json.getString("monto")+" ETH");
                        montoETH = Double.parseDouble(json.getString("monto"));


                        //TVNombre.setText(user.getName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // textView.setText("That didn't work!");
                    pgActualizar.setVisibility(View.GONE);
                    Log.i("error", ""+error.getMessage());
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
            //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            //SingletonConnection.getInstance(this).addToRequestQueue(stringRequest);


        }catch (Exception e){

        }
    }


    public void getCambioUpdated(String Moneda, double eth) {

        try {
            //final TextView textView = (TextView) findViewById(R.id.textView4);
            // ...

            pgActualizar.setVisibility(View.VISIBLE);
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = Url.CONVERTIRETHER;
            url += "vs_currency=" + Moneda;
            url += "&ids=" + "ethereum";
            url += "&order=" + "market_cap_desc";
            url += "&per_page=" + "100";
            url += "&page=" + "1";
            url += "&sparkline=" + "false";

            queue.getCache().clear();

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, (String response) -> {
                // Display the first 500 characters of the response string.
                double change = 0;
                pgActualizar.setVisibility(View.GONE);
                Log.e("CONEXION", response);
                if (response.length() < 3) {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    //Contraseña o usuario incorrectos
                } else {
                    try {

                        //JSONObject jobject = new JSONObject(response);
                        JSONArray jarray = new JSONArray(response);
                        JSONObject json = jarray.getJSONObject(0);
                        String valorEnMoneda = json.getString("current_price");

                        DecimalFormat df = new DecimalFormat("#.00");





                        change = Double.parseDouble(df.format(Double.parseDouble(valorEnMoneda) * montoETH));
                        TVMonto.setText(change + " " + SpinCambio.getSelectedItem());
                        Log.e("json length", json.toString());

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // textView.setText("That didn't work!");
                    pgActualizar.setVisibility(View.GONE);
                    Log.i("error", "" + error.getMessage());
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
            //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            //SingletonConnection.getInstance(this).addToRequestQueue(stringRequest);


        } catch (Exception e) {

        }

    }



    public double convertEtherTo(String Moneda, double eth){
         AtomicReference<Double> cambio = new AtomicReference<>((double) 0);
                try {
                    //final TextView textView = (TextView) findViewById(R.id.textView4);
                    // ...

                    pgActualizar.setVisibility(View.VISIBLE);
                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String url = Url.CONVERTIRETHER;
                    url+="vs_currency="+Moneda;
                    url+="&ids="+"ethereum";
                    url+="&order="+"market_cap_desc";
                    url+="&per_page="+"100";
                    url+="&page="+"1";
                    url+="&sparkline="+"false";

                    queue.getCache().clear();

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, (String response) -> {
                        // Display the first 500 characters of the response string.
                        double change=0;
                        pgActualizar.setVisibility(View.GONE);
                        Log.e("CONEXION", response);
                        if(response.length()<3){
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            //Contraseña o usuario incorrectos
                        }else {
                            try {

                                //JSONObject jobject = new JSONObject(response);
                                JSONArray jarray = new JSONArray(response);
                                JSONObject json = jarray.getJSONObject(0);
                                String valorEnMoneda = json.getString("current_price");

                                change = Double.parseDouble(valorEnMoneda)*montoETH;

                                Log.e("change", ""+change);

                            } catch (JSONException e) {

                                e.printStackTrace();
                            }

                        }
                        cambio.set(change);

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // textView.setText("That didn't work!");
                            pgActualizar.setVisibility(View.GONE);
                            Log.i("error", ""+error.getMessage());
                        }
                    });

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                    //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    //SingletonConnection.getInstance(this).addToRequestQueue(stringRequest);


                }catch (Exception e){

                }
                return cambio.get();
    }

}