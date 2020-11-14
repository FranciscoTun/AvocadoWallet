package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class config extends AppCompatActivity {

    ImageView IVBack;
    TextView TVPersonaNombreUsuario;
    TextView TVPersonaNombre;
    TextView TVPersonaApellidos;
    TextView TVPersonaCLABE;
    Button btnCerrarSesion;
    String valores="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Init();
    }

    public void Init(){
        IVBack = (ImageView)findViewById(R.id.IVBackConfig);

        TVPersonaNombreUsuario = (TextView) findViewById(R.id.TVPersonaNombreDeUsuario);
        TVPersonaNombre = (TextView)findViewById(R.id.TVPersonaNombre);
        TVPersonaApellidos = (TextView)findViewById(R.id.TVPersonaApellidos);
        TVPersonaCLABE = (TextView)findViewById(R.id.TVPersonaClabe);

        btnCerrarSesion = (Button)findViewById(R.id.btnCerrarSesion);
        Bundle datos = this.getIntent().getExtras();
        valores = datos.getString("valores");



        try {
            JSONObject jobject = new JSONObject(valores);
            JSONObject json = jobject.getJSONArray("datos").getJSONObject(0);
            Log.e("json", json.toString());
            String nombres[] = json.getString("Name").split(" ");
            String apellido [] = json.getString("Lastname").split(" ");

            TVPersonaNombreUsuario.setText(json.getString("Username"));
            TVPersonaNombre.setText(json.getString("Name"));
            TVPersonaApellidos.setText(json.getString("Lastname"));
            TVPersonaCLABE.setText(json.getString("Phone"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        IVBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotoB = new Intent(config.this,VistaUsuario.class);
                startActivityForResult(GotoB,1);
                Intent GotoC = new Intent(config.this,VistaUsuario.class);
                startActivityForResult(GotoB,1);
                finish();
                Intent i = new Intent(getApplicationContext(), Ingresar.class);
                startActivity(i);
            }
        });
    }
}