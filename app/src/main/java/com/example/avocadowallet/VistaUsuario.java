package com.example.avocadowallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import static android.app.PendingIntent.getActivity;

public class VistaUsuario extends AppCompatActivity {
String valores = "";
TextView TVNombre;
TextView TVMonto;
Spinner SpinCambio;
ImageView IVTransfer;
ImageView IVMigrar;
ImageView IVInfo;
ImageView IVConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_usuario);
        init();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(getApplicationContext(), "Salir", Toast.LENGTH_SHORT).show();
        mostrarDialogo();
    }

    public AlertDialog mostrarDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getApplicationContext());

        builder.setTitle("¿Estás seguro que deseas salir de la aplicación?")
                .setMessage("Si sales, la sesión se cerrará")
                .setPositiveButton("Salir",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                finish();
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        return builder.create();
    }



    public void init(){
        mostrarDialogo();
        TVNombre = (TextView)findViewById(R.id.TVNombreSesion);
        TVMonto = (TextView)findViewById(R.id.TVMontoSesion);
        SpinCambio = (Spinner)findViewById(R.id.SpinCambio);
        IVTransfer = (ImageView)findViewById(R.id.IVTransferir);
        IVMigrar = (ImageView)findViewById(R.id.IVMigrar);
        IVInfo = (ImageView)findViewById(R.id.IVInfo);
        IVConfig = (ImageView)findViewById(R.id.IVConfig);

        Bundle datos = this.getIntent().getExtras();
        valores = datos.getString("response");

        try {
            JSONObject jobject = new JSONObject(valores);
            JSONObject json = jobject.getJSONArray("datos").getJSONObject(0);
            Log.e("json", json.toString());
            String nombres[] = json.getString("Name").split(" ");
            String apellido [] = json.getString("Lastname").split(" ");

            //TVNombre.setText(json.getString("Name")+" "+json.getString("Lastname"));
            TVNombre.setText(nombres[0]+" "+apellido[0]);
            TVMonto.setText(json.getString("monto")+" ETH");

            //TVNombre.setText(user.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        IVTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Transferir.class);
                i.putExtra("valores",valores);
                startActivity(i);
            }
        });

        IVMigrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        IVInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        IVConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void fillSpinner(){

    }

}