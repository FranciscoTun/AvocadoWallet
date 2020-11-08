package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.avocadowallet.Clases.Usuario;

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
        ETEmail = (EditText)findViewById(R.id.EdiTextEmail);
        ETConfirmEmail = (EditText)findViewById(R.id.EdiTextConfirmEmail);
        ETPhone = (EditText)findViewById(R.id.EdiTextPhone);
        ETPassword = (EditText)findViewById(R.id.EdiTextPass);
        ETConfirmpass = (EditText)findViewById(R.id.EdiTextConfirmPass);

        Usuario user = new Usuario();


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
                    btnCrearCuenta = (Button)findViewById(R.id.btnCrearCuenta);
                    btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CrearCuenta();
                            ///En este apartado se crea un nuevo usuario y se manda a la base de datos
                        }
                    });

                }

            }
        });




    }


    public void CrearCuenta(){

    }
}