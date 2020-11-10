package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

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
                user = ETUser.getText().toString().toUpperCase();
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
            this.deleteDatabase("");
            conexion();
        }
    }


    public void conexion(){
        try {
            //final TextView textView = (TextView) findViewById(R.id.textView4);
            // ...

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url =Values.URL+"sesion.php?user="+user+"&pwd="+pwd;


            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.

                            pgInicioSesion.setVisibility(View.GONE);
                            if(response.length()<3){
                                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                //Contraseña o usuario incorrectos
                            }else {
                                //JSONObject jsonResponse = new JSONObject(response);
                                Intent i = new Intent(getApplicationContext(), VistaUsuario.class);
                                i.putExtra("response", response);
                                startActivity(i);
                            }


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

        }catch (Exception e){

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}