package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

public class VistaUsuario extends AppCompatActivity {
String valores = "";
TextView TVNombre;
TextView TVMonto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_usuario);
        init();
    }

    public void init(){
        TVNombre = (TextView)findViewById(R.id.TVNombreSesion);
        TVMonto = (TextView)findViewById(R.id.TVMontoSesion);
        Bundle datos = this.getIntent().getExtras();
        valores = datos.getString("response");

        try {
            JSONObject jobject = new JSONObject(valores);
            JSONObject json = jobject.getJSONArray("datos").getJSONObject(0);
            Log.e("json", json.toString());

            TVNombre.setText(json.getString("Name"));
            TVMonto.setText(json.getString("monto")+" ETH");

            //TVNombre.setText(user.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}