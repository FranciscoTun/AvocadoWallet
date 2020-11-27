package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.avocadowallet.Clases.statics.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransferirPaso2 extends AppCompatActivity {
    String valores = "";
    String userpaswordreal="";
    EditText ETClabe;
    EditText ETMontoaTransferir;
    Button btnConfirm;
    EditText ETPasswordTransac;
    Button btnCancelTransac;
    Button btnConfirmTransac;
    ConstraintLayout ConstLayPanelMensaje;
    ImageView IVBack;
    ProgressBar PBTransfer;

    String idUser;
    String User;
    String CLABEOrigen;
    String CLABEDestino;
    String Fecha;
    String MONTO;
    String PASSW;
    double MontoEnMiCuenta;
    double MontoATransferir;


    //primero que no te puedas transferir mas de lo que tienes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferir_paso2);
        Init();
    }

    public void Init(){
        ETClabe = (EditText)findViewById(R.id.ETClabe);
        ETMontoaTransferir = (EditText)findViewById(R.id.ETMontoATransferir);
        btnConfirm = (Button)findViewById(R.id.btnConfirm);
        ETPasswordTransac = (EditText)findViewById(R.id.ETPasswordTransac);
        btnCancelTransac = (Button)findViewById(R.id.btnCancelTransac);
        btnConfirmTransac = (Button)findViewById(R.id.btnConfirmTransac);
        IVBack = (ImageView)findViewById(R.id.IVBackPaso2);
        PBTransfer = (ProgressBar)findViewById(R.id.PBTransfer);
        PBTransfer.setVisibility(View.INVISIBLE);

        ConstLayPanelMensaje = (ConstraintLayout)findViewById(R.id.ConstLayPanelMensaje);
        ConstLayPanelMensaje.setVisibility(View.GONE);
        Bundle datos = this.getIntent().getExtras();
        valores = datos.getString("valores");

        try {
            JSONObject jobject = new JSONObject(valores);
            JSONObject json = jobject.getJSONArray("datos").getJSONObject(0);
            Log.e("json", json.toString());
            String nombres[] = json.getString("Name").split(" ");
            String apellido [] = json.getString("Lastname").split(" ");

            idUser = json.getString("idUsuario");
            User = json.getString("Username");
            CLABEOrigen = json.getString("CLABE");
            userpaswordreal = json.getString("Password");
            MontoEnMiCuenta = Double.parseDouble(json.getString("monto"));

            Date anotherCurDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss");
            Fecha = formatter.format(anotherCurDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.e("Los", valores);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ETClabe.getText().toString().isEmpty() || ETMontoaTransferir.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"No deje campos vacíos",Toast.LENGTH_SHORT).show();
                }else{

                    if(ETClabe.getText().toString().equals(CLABEOrigen)){
                        Toast.makeText(getApplicationContext(),"La CLABE de Destino no debee ser igual a la CLAVE de Origen",Toast.LENGTH_SHORT).show();
                    }else {
                        MontoATransferir = Double.parseDouble(ETMontoaTransferir.getText().toString());
                        if(MontoATransferir>MontoEnMiCuenta){
                            Toast.makeText(getApplicationContext(),"No tienes suficiente Dinero",Toast.LENGTH_SHORT).show();
                        }else {
                            ConstLayPanelMensaje.setVisibility(View.VISIBLE);
                            ETClabe.setEnabled(false);
                            ETMontoaTransferir.setEnabled(false);
                            btnConfirm.setEnabled(false);
                        }

                    }

                }
            }
        });

        btnCancelTransac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstLayPanelMensaje.setVisibility(View.GONE);
                ETClabe.setEnabled(true);
                ETMontoaTransferir.setEnabled(true);
                btnConfirm.setEnabled(true);
            }
        });

        btnConfirmTransac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLABEDestino = ETClabe.getText().toString();
                MONTO = ETMontoaTransferir.getText().toString();
                PASSW = ETPasswordTransac.getText().toString();
                validarTransaccion();
            }
        });


        IVBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    public void validarTransaccion(){
        if(PASSW.equals(userpaswordreal)){
            PBTransfer.setVisibility(View.VISIBLE);
            conexion();
        }else {

        }
    }



    public void conexion(){
        try {
            //final TextView textView = (TextView) findViewById(R.id.textView4);
            // ...

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = Url.TRANSFERENCIA +"Username="+User+"&Password="+PASSW+"&CLABEOrigen="+CLABEOrigen+"&CLABEDestino="+CLABEDestino+"&monto="+MONTO+"&fecha_mov="+Fecha+"&idUsuario="+idUser;
            queue.getCache().clear();
            Log.e("URL de la app", url);
            //Cadena = &status=1&monto=0

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Lo que devuelve",response);
                    PBTransfer.setVisibility(View.GONE);

                    Transferir.transfer.finish();
                    finish();
                    Intent i = new Intent(getApplicationContext(), VistaUsuario.class);
                    i.putExtra("response", response);
                    startActivity(i);

                    // Display the first 500 characters of the response string.
                    //Excelente
                    //textView.setText("Response is: "+response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error, vuelva a intentarlo mas tarde", Toast.LENGTH_SHORT).show();
                    //textView.setText("That didn't work!");
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }catch (Exception e){

        }
    }
}