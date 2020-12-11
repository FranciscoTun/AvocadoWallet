package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;

public class Transacciones extends AppCompatActivity {

    String userId;
    ArrayList<User> UserList;
    RecyclerView recyclerView;
    TextView TVSinTransacciones;
    ProgressBar ProgBarGetTransacciones;
    ImageView IVBackTransacciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacciones);
        Init();

    }

    public void Init()
    {
        Bundle datos = this.getIntent().getExtras();
        userId = datos.getString("userId");
        TVSinTransacciones = (TextView)findViewById(R.id.TVSinTransacciones);
        TVSinTransacciones.setVisibility(View.GONE);
        recyclerView = (RecyclerView) findViewById(R.id.rvAllUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProgBarGetTransacciones = (ProgressBar)findViewById(R.id.ProgBarGetTransactiones);
        ProgBarGetTransacciones.setVisibility(View.GONE);
        IVBackTransacciones = (ImageView) findViewById(R.id.IVBackTransacciones);

        IVBackTransacciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        UserList = new ArrayList<>();


        getTransacciones();
    }

    public class User {

        int imageResourceId;
        String userName;
        String userMobile;
        String userEmail;
        String userCreatedDate;

        public User(int imageResourceId, String userName, String userMobile, String userEmail, String userCreatedDate) {
            this.imageResourceId = imageResourceId;
            this.userName = userName;
            this.userMobile = userMobile;
            this.userEmail = userEmail;
            this.userCreatedDate = userCreatedDate;
        }
    }


    public void getTransacciones(){
        ProgBarGetTransacciones.setVisibility(View.VISIBLE);
        try {
            //final TextView textView = (TextView) findViewById(R.id.textView4);
            // ...

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = Url.OBTENERTRANSFERENCIAS +"idUsuario="+userId;
            queue.getCache().clear();

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                // Display the first 500 characters of the response string.

                //pgActualizar.setVisibility(View.GONE);
                Log.e("CONEXION", response);
                if(response.length()<3){
                    ProgBarGetTransacciones.setVisibility(View.GONE);
                    TVSinTransacciones.setVisibility(View.VISIBLE);
                    //Contraseña o usuario incorrectos
                }else {
                    try {
                        ProgBarGetTransacciones.setVisibility(View.GONE);
                        TVSinTransacciones.setVisibility(View.GONE);
                        JSONArray jobject = new JSONArray(response);
                        JSONObject json = jobject.getJSONObject(0);

                        Log.e("json por fin", ""+jobject.length());

                        for(int i =0; i<jobject.length(); i++){
                            json = jobject.getJSONObject(i);

                            UserList.add(new User(getResources().getIdentifier(String.valueOf(R.drawable.ic_transferir_little_icon),null,null),
                                    "Fecha: "+json.getString("fecha_mov"), "Origen: "+json.getString("CLABEOrigen"),
                                    "Destino: "+json.getString("CLABEDestino"),
                                    "Monto: "+json.getString("monto")));

                            //fillProyectList(idProyecto, name, Descrip);
                        }

                        AllUsersAdapter allUserAdapter = new AllUsersAdapter(getApplicationContext(), UserList);
                        recyclerView.setAdapter(allUserAdapter);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());

                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        ProgBarGetTransacciones.setVisibility(View.GONE);
                    }

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // textView.setText("That didn't work!");
                    //pgActualizar.setVisibility(View.GONE);
                    ProgBarGetTransacciones.setVisibility(View.GONE);
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



}