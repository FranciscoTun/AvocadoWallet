package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CrearCuenta extends AppCompatActivity {

    //Se va a instanciar una nueva clase de tipo usuario y se agregara cada valor
    ImageView imgClose;
    Button btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        init();
    }

    public void init(){
        btnSiguiente = (Button)findViewById(R.id.btnSiguiente);
        imgClose = (ImageView)findViewById(R.id.imageViewClose);


        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_crear_cuenta_paso2);
            }
        });




    }
}