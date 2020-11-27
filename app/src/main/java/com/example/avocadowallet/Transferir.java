package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class Transferir extends AppCompatActivity {
    String valores = "";
    RadioButton RBAvocadoWallet;
    RadioButton RBOther;
    Button btnSigTrans;
    int seleccion =0;
    ImageView IVBack;

    ImageView ImagenAvocado;
    ImageView ImagenWallet;
    public static Transferir transfer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        transfer = this;
        setContentView(R.layout.activity_transferir);
        Init();
    }

    public void finish(){
        super.finish();
        transfer = null;
    }

    public void Init(){
        RBAvocadoWallet = (RadioButton) findViewById(R.id.RButtonAvocado);
        RBOther = (RadioButton)findViewById(R.id.RBOther);
        btnSigTrans = (Button)findViewById(R.id.BtnSigtTransfer);
        IVBack = (ImageView)findViewById(R.id.IVTransferBack);
        ImagenAvocado = (ImageView)findViewById(R.id.IVAvocadoRadio);
        ImagenWallet = (ImageView)findViewById(R.id.IVWalletRadio);

        Bundle datos = this.getIntent().getExtras();
        valores = datos.getString("valores");

        RBAvocadoWallet.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "Range"})
            @Override
            public void onClick(View v) {
                RBAvocadoWallet.setBackgroundResource(R.color.purple_pers);
                RBAvocadoWallet.setTextColor(Color.parseColor("#FFFFFF"));
                int idAvocadoBlanco = getResources().getIdentifier(String.valueOf(R.drawable.ic_inkavocado), null, null);
                ImagenAvocado.setImageResource(idAvocadoBlanco);


                RBOther.setBackgroundResource(R.color.white);
                RBOther.setTextColor(Color.parseColor("#000000"));
                int idOtherNegro = getResources().getIdentifier(String.valueOf(R.drawable.ic_other_wallet_solid_black), null, null);
                ImagenWallet.setImageResource(idOtherNegro);
            }
        });

        RBOther.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "Range"})
            @Override
            public void onClick(View v) {
                RBAvocadoWallet.setBackgroundResource(R.color.white);
                RBAvocadoWallet.setTextColor(Color.parseColor("#000000"));
                int idAvocadoNegro = getResources().getIdentifier(String.valueOf(R.drawable.ic_inkavocado_black), null, null);
                ImagenAvocado.setImageResource(idAvocadoNegro);


                RBOther.setBackgroundResource(R.color.purple_500);
                RBOther.setTextColor(Color.parseColor("#FFFFFF"));
                int idOtherBlanco = getResources().getIdentifier(String.valueOf(R.drawable.ic_other_wallet_solid), null, null);
                ImagenWallet.setImageResource(idOtherBlanco);

            }
        });

        btnSigTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //seleccion = (RBAvocadoWallet.isChecked())?1:2;
                //seleccion = (RBAvocadoWallet.isChecked())?2:;

                if(RBAvocadoWallet.isChecked()){
                    // pasar el valor que Avocado Wallet esta seleccionado
                    Log.e("Seleccionado", "AvocadoWaller");
                    seleccion = 1;
                }

                if(RBOther.isChecked()){
                    //Pasar el valor que otra wallet esta seleccionado
                    Log.e("Seleccionado", "Otro");
                    seleccion = 2;
                }

                if(seleccion!=0){
                    Intent i = new Intent(getApplicationContext(), TransferirPaso2.class);
                    i.putExtra("user","");
                    i.putExtra("pwd","");
                    i.putExtra("seleccion",seleccion);
                    i.putExtra("valores",valores);
                    startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext(),"Seleccione una opción",Toast.LENGTH_SHORT).show();
                }
                Log.e("Seleccionado", ""+seleccion);

            }
        });

        IVBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}