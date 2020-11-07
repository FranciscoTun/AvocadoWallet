package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ingresar extends AppCompatActivity {
    Button btnIngrersar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);
    }

    public void Init(){
        btnIngrersar = (Button)findViewById(R.id.btnIngresar);
        btnIngrersar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //En esta seccion debo validar las credenciales del usiario al igual que conectarme con blockchain jsdbcvkjjsdbfkjsdfjhjh
            }
        });
    }
}