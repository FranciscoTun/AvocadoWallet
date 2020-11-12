package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TransferirPaso2 extends AppCompatActivity {
    EditText ETClabe;
    EditText ETMontoaTransferir;
    Button btnConfirm;
    EditText ETPasswordTransac;
    Button btnCancelTransac;
    Button btnConfirmTransac;
    ConstraintLayout ConstLayPanelMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferir_paso2);
        Init();
    }

    public void Init(){
        ETClabe = (EditText)findViewById(R.id.ETClabe);
        ETMontoaTransferir = (EditText)findViewById(R.id.ETMontoATransferir);
        btnConfirm = (Button)findViewById(R.id.btnConfirm);
        ETPasswordTransac = (EditText)findViewById(R.id.ETPasswordTransac);
        btnCancelTransac = (Button)findViewById(R.id.btnCancelTransac);
        btnConfirmTransac = (Button)findViewById(R.id.btnConfirmTransac);

        ConstLayPanelMensaje = (ConstraintLayout)findViewById(R.id.ConstLayPanelMensaje);
        ConstLayPanelMensaje.setVisibility(View.GONE);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ETClabe.getText().toString().isEmpty() || ETMontoaTransferir.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"No deje campos vacíos",Toast.LENGTH_SHORT).show();
                }else{
                    ConstLayPanelMensaje.setVisibility(View.VISIBLE);
                    ETClabe.setEnabled(false);
                    ETMontoaTransferir.setEnabled(false);
                    btnConfirm.setEnabled(false);
                }
            }
        });

        btnCancelTransac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstLayPanelMensaje.setVisibility(View.GONE);
                ETClabe.setEnabled(true);
                ETMontoaTransferir.setEnabled(true);
                btnConfirm.setEnabled(true);
            }
        });

        btnConfirmTransac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





    }
}