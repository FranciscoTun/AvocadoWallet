package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.example.avocadowallet.Clases.Usuario;
import com.example.avocadowallet.Clases.statics.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Queue;

public class CrearCuenta extends AppCompatActivity {

    //Se va a instanciar una nueva clase de tipo usuario y se agregara cada valor
    ImageView imgClose;
    Button btnSiguiente;
    Button btnCrearCuenta;

    EditText ETNombre;
    EditText ETApellido;
    EditText ETEmail;
    EditText ETConfirmEmail;
    EditText ETPhone;
    EditText ETPassword;
    EditText ETConfirmpass;
    Usuario user;
    ProgressBar PBcreandocuenta;
    CheckBox checkBoxAcuerdo;

    String BlockPrivated;
    String BlockPublic;
    String BlockAddress;

    Boolean validado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        init();
    }

    public void init(){
        //INSTANCIAS
        btnSiguiente = (Button)findViewById(R.id.btnSiguiente);
        imgClose = (ImageView)findViewById(R.id.imageViewClose);


        ETNombre = (EditText)findViewById(R.id.EdiTexNombre);
        ETApellido = (EditText)findViewById(R.id.EdiTextApellidos);

        //CLIC LISTENERS
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ETNombre.getText().toString().isEmpty() || ETApellido.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"No deje campos vacíos", Toast.LENGTH_SHORT).show();
                }else {
                    setContentView(R.layout.activity_crear_cuenta_paso2);

                    ETEmail = (EditText)findViewById(R.id.EdiTextEmail);
                    ETConfirmEmail = (EditText)findViewById(R.id.EdiTextConfirmEmail);
                    ETPhone = (EditText)findViewById(R.id.EdiTextPhone);
                    ETPassword = (EditText)findViewById(R.id.EdiTextPass);
                    ETConfirmpass = (EditText)findViewById(R.id.EdiTextConfirmPass);
                    PBcreandocuenta = (ProgressBar)findViewById(R.id.pbCreandoCuenta);
                    checkBoxAcuerdo = (CheckBox)findViewById(R.id.checkBoxAcuerdo);

                    btnCrearCuenta = (Button)findViewById(R.id.btnCrearCuenta);
                    btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                if(!ETEmail.getText().toString().isEmpty() && !ETConfirmEmail.getText().toString().isEmpty()){

                                    if(ETEmail.getText().toString().equals(ETConfirmEmail.getText().toString())){
                                        validado = true;
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Los correos no coinciden", Toast.LENGTH_SHORT).show();
                                        validado = false;
                                    }
                                }else {
                                    //Toast.makeText(getApplicationContext(), "No deje campos vacíos", Toast.LENGTH_SHORT).show();
                                    validado=false;
                                }


                                if(!ETPhone.getText().toString().isEmpty()){
                                    validado = true;
                                }else{
                                    validado = false;
                                }

                                if(!ETPassword.getText().toString().isEmpty() && !ETConfirmpass.getText().toString().isEmpty()){
                                    if(ETPassword.getText().toString().equals(ETConfirmpass.getText().toString())){
                                        validado = true;
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                                        validado = false;
                                    }
                                }else {
                                    //Toast.makeText(getApplicationContext(), "No deje", Toast.LENGTH_SHORT).show();
                                    validado = false;
                                }





                                if(checkBoxAcuerdo.isChecked()){
                                    if(validado){
                                        Toast.makeText(getApplicationContext(), "Creando Cuenta", Toast.LENGTH_SHORT).show();

                                        CrearCuenta();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Llene correctamente los campos para poder continuar", Toast.LENGTH_LONG).show();
                                    }

                                }else{
                                    Toast.makeText(getApplicationContext(), "Acepte los términos y condiciones para poder continuar", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }

            }
        });

    }


    public void CrearCuenta() throws JSONException {
        user = new Usuario();
        user.setUsername(ETEmail.getText().toString());
        user.setName(ETNombre.getText().toString().toUpperCase());
        user.setLastname(ETApellido.getText().toString().toUpperCase());
        user.setEmail(ETEmail.getText().toString());
        user.setPassword(ETPassword.getText().toString());
        user.setPhone(ETPhone.getText().toString());
        user.setStatus(1);
        user.setMonto(0);
        crearUserBlockchain();

    }


    public void crearUserBlockchain(){
        try {
            PBcreandocuenta.setVisibility(View.VISIBLE);
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = Url.CREARUSERBLOCKCHAIN;
            //String url =Values.URL+"crearusuario.php?idUsuario=null&Username="+user.getUsername()+"&Name="+user.getName()+"&Lastname="+user.getLastname()+"&Email="+user.getEmail()+"&Phone="+user.getPhone()+"&Password="+user.getPassword()+"&status="+user.getStatus()+"&monto="+user.getMonto()+"&CLABE="+user.getPhone();
            Log.e("URLO", url);
            queue.getCache().clear();
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject json = new JSONObject(response);
                        BlockPrivated = json.getString("private");
                        BlockPublic = json.getString("public");
                        BlockAddress = "0x"+json.getString("address");

                        conexion();

                    }catch (Exception e){
                        Log.e("Error atrapado", e.getMessage());
                    }
                    Log.e("Response blockchain", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error, vuelva a intentarlo mas tarde", Toast.LENGTH_SHORT).show();
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }catch (Exception e){
            Log.e("Error", e.getMessage().toString());
        }
    }



    public void conexion(){
        try {

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url =Values.URL+"crearusuario.php?idUsuario=null&Username="+user.getUsername()+"&Name="+user.getName()+"&Lastname="+user.getLastname()+"&Email="+user.getEmail()+"&Phone="+user.getPhone()+"&Password="+user.getPassword()+"&status="+user.getStatus()+"&monto="+user.getMonto()+"&CLABE="+user.getPhone()+"&private="+BlockPrivated+"&public="+BlockPublic+"&address="+BlockAddress;
            Log.e("URLO", url);
            queue.getCache().clear();
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    PBcreandocuenta.setVisibility(View.GONE);
                    Intent i = new Intent(getApplicationContext(), Ingresar.class);
                    startActivity(i);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error, vuelva a intentarlo mas tarde", Toast.LENGTH_SHORT).show();
                    Log.e("VolleyError", error.getMessage());
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }catch (Exception e){
            Log.e("Exception Try conexion() Error", e.getMessage().toString());
        }
    }
}