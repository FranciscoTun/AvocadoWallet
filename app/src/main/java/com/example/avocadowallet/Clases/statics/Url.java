package com.example.avocadowallet.Clases.statics;

public class Url {
    private static final String SERVIDOR ="https://avocadowallet.000webhostapp.com";
    public static final String SERVIDORBRINKENBI ="https://rinkeby.infura.io/v3/222414e05d444209a95c2561b0c28937";
    public static final String CREARUSERBLOCKCHAIN = "https://api.blockcypher.com/v1/eth/main/addrs?token=52021828d13449409096579728d4f73b";


    public static final String INICIARSESION = SERVIDOR+"/sesion.php?";
    public static final String INICIARSESION_JSON = SERVIDOR+"/sesionjson.php?json=";
    public static final String TRANSFERENCIA = SERVIDOR+"/transaccion.php?";
    public static final String OBTENERTRANSFERENCIAS = SERVIDOR+"/getTransacciones.php?";
    public static final String CONVERTIRETHER = "https://api.coingecko.com/api/v3/coins/markets?";


    public static final String APIKEYETHERSCAN= "KAW8JGWIHMYP222JZ6MIKBUXZR2B7C3ZKB";
    public static final String APIKEYRINKEBY= "222414e05d444209a95c2561b0c28937";



}
