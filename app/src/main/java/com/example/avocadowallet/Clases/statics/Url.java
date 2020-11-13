package com.example.avocadowallet.Clases.statics;

public class Url {
    private static final String SERVIDOR ="https://avocadowallet.000webhostapp.com";

    public static final String INICIARSESION = SERVIDOR+"/sesion.php?";
    public static final String INICIARSESION_JSON = SERVIDOR+"/sesionjson.php?json=";
    public static final String TRANSFERENCIA = SERVIDOR+"/transaccion.php?";
}
