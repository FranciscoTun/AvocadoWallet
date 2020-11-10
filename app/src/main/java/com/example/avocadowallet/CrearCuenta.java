package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.avocadowallet.Clases.Usuario;

import org.json.JSONException;
import org.json.JSONObject;
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

                    btnCrearCuenta = (Button)findViewById(R.id.btnCrearCuenta);
                    btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Toast.makeText(getApplicationContext(), "Creando Cuenta", Toast.LENGTH_SHORT).show();
                                CrearCuenta();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ///En este apartado se crea un nuevo usuario y se manda a la base de datos
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


        conexion();

    }



    public void conexion(){
        try {
            //final TextView textView = (TextView) findViewById(R.id.textView4);
            // ...

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url =Values.URL+"crearusuario.php?idUsuario=null&Username="+user.getUsername()+"&Name="+user.getName()+"&Lastname="+user.getLastname()+"&Email="+user.getEmail()+"&Phone="+user.getPhone()+"&Password="+user.getPassword()+"&status="+user.getStatus()+"&monto="+user.getMonto();
            //Cadena = &status=1&monto=0

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    //Excelente
                    //textView.setText("Response is: "+response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //textView.setText("That didn't work!");
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }catch (Exception e){

        }
    }
}