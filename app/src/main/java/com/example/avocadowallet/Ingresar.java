package com.example.avocadowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.avocadowallet.Clases.Usuario;
import com.example.avocadowallet.Clases.statics.Url;
import com.example.avocadowallet.dataaccess.remote.SingletonConnection;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.security.Provider;
import java.security.Security;
import java.sql.Array;
import java.sql.SQLData;
import java.util.concurrent.ExecutionException;

import static com.example.avocadowallet.R.drawable.ic_eye_slash_solid;
import static com.example.avocadowallet.R.drawable.ic_eye_solid;

public class Ingresar extends AppCompatActivity {
    EditText ETUser;
    EditText ETPwd;
    Button btnIngrersar;
    String user ="";
    String pwd ="";
    ProgressBar pgInicioSesion;
    Button btnViewPass;
    Drawable drawableEyeSolid;
    Drawable drawableEyeSlashed;
    Web3j web3;

    private final String password = "medium";
    private String walletPath;
    private File walletDir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_ingresar);
        super.onCreate(savedInstanceState);
        Init();
        //connectToTestNet();
        //conexionBlocChain();
        //createWallet();
    }




    public void Init(){
        setupBouncyCastle();
        walletPath = getFilesDir().getAbsolutePath();

        ETUser = (EditText)findViewById(R.id.EdiTextUserInicio);
        ETPwd = (EditText)findViewById(R.id.EdiTextPwsInicio);
        btnIngrersar = (Button)findViewById(R.id.btnIngresar);
        pgInicioSesion = (ProgressBar)findViewById(R.id.progBarInicioSesion);
        btnViewPass = (Button)findViewById(R.id.btnViewPass);
        btnViewPass.setBackgroundResource(ic_eye_solid);
        //drawableEyeSolid = getResources(R.drawable.ic_eye_solid);
        //drawableEyeSlashed = (Drawable)getDrawable(ic_eye_slash_solid);



        btnViewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //getAddress(v);
               // ETPwd.getInputType();
                if(ETPwd.getInputType()==145){
                    ETPwd.setInputType(129);
                    //btnViewPass.setCompoundDrawables(null, null, drawableEyeSolid ,null);
                    btnViewPass.setBackgroundResource(ic_eye_solid);

                }else {
                    ETPwd.setInputType(145);
                    btnViewPass.setBackgroundResource(ic_eye_slash_solid);
                    //btnViewPass.setCompoundDrawables(null, null, drawableEyeSlashed ,null);
                }
                Log.e("INPUTYPE",""+ETPwd.getInputType());
            }
        });

        btnIngrersar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = ETUser.getText().toString();
                pwd = ETPwd.getText().toString();
                iniciarSesion(user,pwd);

            }
        });
    }


    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up the provider lazily when it's first used.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            // BC with same package name, shouldn't happen in real life.
            return;
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }
    public void sendTransaction(View v){
        try{
            Credentials credentials = WalletUtils.loadCredentials(password, walletDir);
            TransactionReceipt receipt = Transfer.sendFunds(web3,credentials,"0x31B98D14007bDEe637298086988A0bBd31184523",new BigDecimal(1), Convert.Unit.ETHER).sendAsync().get();
            toastAsync("Transaction complete: " +receipt.getTransactionHash());
        }
        catch (Exception e){
            toastAsync(e.getMessage());
        }
    }

    public void getAddress(View v){
        try {
            Credentials credentials = WalletUtils.loadCredentials(password, walletDir);
            toastAsync("Your address is " + credentials.getAddress());
        }
        catch (Exception e){
            toastAsync(e.getMessage());
        }
    }

    public void toastAsync(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }


    public void connectToTestNet(){
        web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/222414e05d444209a95c2561b0c28937"));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if(!clientVersion.hasError()){
                Log.e("TEST NET", "CONECTADO!!!");





            }
            else {
                Log.e("TEST NET", "HAS ERROR!!!");
                //Show Error
            }
        }
        catch (Exception e) {
            //Show Error
            Log.e("TEST NET", "CATCHED EXCEPTION!!!");
        }
    }

    public void createWallet(){
        try{
            String fileName = WalletUtils.generateLightNewWalletFile(password, walletDir);
            walletDir = new File(walletPath + "/" + fileName);
        }
        catch (Exception e){
            //Display an Error
        }

    }

    public void iniciarSesion(String user, String pwd){
        if(user.isEmpty()|| pwd.isEmpty()){
            Toast.makeText(getApplicationContext(), "Escriba su usuario y contraseña",Toast.LENGTH_SHORT).show();
        }else {
            //Intent i = new Intent(getApplicationContext(), VistaUsuario.class);
            //startActivity(i);
            pgInicioSesion.setVisibility(View.VISIBLE);

            //resetconexion();

            conexion();

        }
    }


    public void conexionBlocChain(){


        try {

            JSONArray array = new JSONArray();

            JSONObject json = new JSONObject();
            json.put("jsonrpc","2.0");
            json.put("id", 1);
            json.put("method", "eth_blockNumber");
            json.put("params", array);


            //Log.e("idPariente",""+spParentesco.getSelectedItemPosition());

/*
            json
                    .put("data", new JSONObject()
                            .put("user",user)
                            .put("pwd", pwd)
                    );
*/

            Log.e("Jeison", ""+json);

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, Url.SERVIDORBRINKENBI, json, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonResponse) {
                            Log.e("Resp", jsonResponse.toString());

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("onErrorResponse", error.getMessage());
                        }
                    });
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            SingletonConnection.getInstance(this).addToRequestQueue(jsObjRequest);
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }



/*
    public void conexionBlocChain(){
        try {
            //final TextView textView = (TextView) findViewById(R.id.textView4);
            // ...

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url =Url.SERVIDORBRINKENBI;



            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("restConexion", response.toString());

                    }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // textView.setText("That didn't work!");
                    //pgInicioSesion.setVisibility(View.GONE);
                    Log.i("error", ""+error.getMessage());
                }
            });

            // Add the request to the RequestQueue.
            //queue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            SingletonConnection.getInstance(this).addToRequestQueue(stringRequest);


        }catch (Exception e){

        }
    }

*/

    public void conexion(){
        try {
            //final TextView textView = (TextView) findViewById(R.id.textView4);
            // ...

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url =Values.URL+"sesion.php?user="+user+"&pwd="+pwd;
            queue.getCache().clear();

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                // Display the first 500 characters of the response string.

                pgInicioSesion.setVisibility(View.GONE);
                Log.e("CONEXION", response);
                if(response.length()<3){
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    //Contraseña o usuario incorrectos
                }else {
                    Log.e("CONEXION", response.toString());
                    //JSONObject jsonResponse = new JSONObject(response);
                    Intent i = new Intent(getApplicationContext(), VistaUsuario.class);
                    i.putExtra("response", response);
                    startActivity(i);
                    finish();

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // textView.setText("That didn't work!");
                    pgInicioSesion.setVisibility(View.GONE);
                    Log.i("error", ""+error.getMessage());
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
            //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            //SingletonConnection.getInstance(this).addToRequestQueue(stringRequest);


        }catch (Exception e){

        }
    }




}