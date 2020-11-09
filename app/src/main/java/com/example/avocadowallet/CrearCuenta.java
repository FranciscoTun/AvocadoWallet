package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

        JSONObject Jsonuser = new JSONObject();

        Jsonuser.put("Username", user.getUsername());
        Jsonuser.put("Name",user.getName());
        Jsonuser.put("Lastname", user.getLastname());
        Jsonuser.put("Email", user.getEmail());
        Jsonuser.put("Phone", user.getPhone());
        Jsonuser.put("Password", user.getPassword());

        Log.e("Mensaje", ""+Jsonuser);
    }



    public static void Conexion(){
        try {

        }catch (Exception e){

        }
    }
}