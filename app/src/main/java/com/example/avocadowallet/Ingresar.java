package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ingresar extends AppCompatActivity {
    Button btnIngrersar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_ingresar);
        super.onCreate(savedInstanceState);
        Init();
    }

    public void Init(){
        btnIngrersar = (Button)findViewById(R.id.btnIngresar);
        btnIngrersar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), VistaUsuario.class);
                startActivity(i);
            }
        });
    }
}