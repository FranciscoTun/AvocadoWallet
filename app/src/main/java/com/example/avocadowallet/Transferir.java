package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class Transferir extends AppCompatActivity {
    RadioButton RBAvocadoWallet;
    RadioButton RBOther;
    Button btnSigTrans;
    int seleccion =0;
    ImageView IVBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferir);
        Init();
    }

    public void Init(){
        RBAvocadoWallet = (RadioButton) findViewById(R.id.RButtonAvocado);
        RBOther = (RadioButton)findViewById(R.id.RBOther);
        btnSigTrans = (Button)findViewById(R.id.BtnSigtTransfer);
        IVBack = (ImageView)findViewById(R.id.IVTransferBack);

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