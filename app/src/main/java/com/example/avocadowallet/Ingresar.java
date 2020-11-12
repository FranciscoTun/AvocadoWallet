package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.avocadowallet.Clases.Usuario;
import com.example.avocadowallet.Clases.statics.Url;
import com.example.avocadowallet.dataaccess.remote.SingletonConnection;

import org.json.JSONObject;

import java.net.URL;
import java.sql.SQLData;
import java.util.concurrent.ExecutionException;

public class Ingresar extends AppCompatActivity {
    EditText ETUser;
    EditText ETPwd;
    Button btnIngrersar;
    String user ="";
    String pwd ="";
    ProgressBar pgInicioSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_ingresar);
        super.onCreate(savedInstanceState);
        Init();
    }




    public void Init(){
        ETUser = (EditText)findViewById(R.id.EdiTextUserInicio);
        ETPwd = (EditText)findViewById(R.id.EdiTextPwsInicio);
        btnIngrersar = (Button)findViewById(R.id.btnIngresar);
        pgInicioSesion = (ProgressBar)findViewById(R.id.progBarInicioSesion);

        btnIngrersar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = ETUser.getText().toString();
                pwd = ETPwd.getText().toString();
                iniciarSesion(user,pwd);

            }
        });
    }

    public void iniciarSesion(String user, String pwd){
        if(user.isEmpty()|| pwd.isEmpty()){
            Toast.makeText(getApplicationContext(), "Escriba su usuario y contraseña",Toast.LENGTH_SHORT).show();
        }else {
            //Intent i = new Intent(getApplicationContext(), VistaUsuario.class);
            //startActivity(i);
            pgInicioSesion.setVisibility(View.VISIBLE);

            //resetconexion();

            conexion();

        }
    }

/*
    public void conexion2(){


        try {
            JSONObject json = new JSONObject();
            Usuario us = new Usuario();
            //Log.e("idPariente",""+spParentesco.getSelectedItemPosition());


            json
                    .put("data", new JSONObject()
                            .put("user",user)
                            .put("pwd", pwd)
                    );

            Log.e("Jeison", ""+json);

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, Url.INICIARSESION_JSON, json, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonResponse) {
                            Log.e("Resp", jsonResponse.toString());
                            if(jsonResponse.length()<3){
                                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                //Contraseña o usuario incorrectos
                            }else {
                                //JSONObject jsonResponse = new JSONObject(response);
                                Intent i = new Intent(getApplicationContext(), VistaUsuario.class);
                                i.putExtra("response", jsonResponse.toString());
                                startActivity(i);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("onErrorResponse", error.getMessage());
                        }
                    });
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            SingletonConnection.getInstance(this).addToRequestQueue(jsObjRequest);
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

*/


    public void resetconexion(){
        try {
            //final TextView textView = (TextView) findViewById(R.id.textView4);
            // ...

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url =Values.URL+"sesion.php?user="+user+"&pwd=14"+pwd;



            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("restConexion", response.toString());
                    conexion();
                    }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // textView.setText("That didn't work!");
                    //pgInicioSesion.setVisibility(View.GONE);
                    Log.i("error", ""+error.getMessage());
                }
            });

            // Add the request to the RequestQueue.
            //queue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            SingletonConnection.getInstance(this).addToRequestQueue(stringRequest);


        }catch (Exception e){

        }
    }

    public void conexion(){
        try {
            //final TextView textView = (TextView) findViewById(R.id.textView4);
            // ...

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url =Values.URL+"sesion.php?user="+user+"&pwd="+pwd;
            queue.getCache().clear();

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                // Display the first 500 characters of the response string.

                pgInicioSesion.setVisibility(View.GONE);
                Log.e("CONEXION", response);
                if(response.length()<3){
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    //Contraseña o usuario incorrectos
                }else {
                    Log.e("CONEXION", response.toString());
                    //JSONObject jsonResponse = new JSONObject(response);
                    Intent i = new Intent(getApplicationContext(), VistaUsuario.class);
                    i.putExtra("response", response);
                    startActivity(i);

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // textView.setText("That didn't work!");
                    pgInicioSesion.setVisibility(View.GONE);
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