package com.mcz.temperarure_humidity_appproject.app.utils;

import android.content.Context;
import android.database.Cursor;

public class NetDefult {

    static NetDefult netDefult = new NetDefult();
    BDhelper bd;

    public synchronized static NetDefult getInstance() {
        return netDefult;
    }

    private static final String IP = "device.api.ct10649.com"; //IP
    private static final String PORT = "8743";                  //端口
    private static final String APPID = "8xXtFhEiUnLWDFwu3QasgpnWaFAa"; //APPID
    private static final String APPPWD = "_vEzC45KWxTbLpC3ZJEbbFMu0pga";//APP密钥
    private static final String meter = "";

    private static final String IP8045 = "develop.api.ct10649.com"; //IP
    private static final String PORT8045 = "8743";                  //端口
    private static final String APPID8045 = "pi_bJ7aIZfRclA1kup67OgC1V4Aa"; //APPID
    private static final String APPPWD8045 = "i7ACx3urpsogefxeaUfpLYfyG1Aa";//APP密钥

    public String getIP8045() {
        return IP8045;
    }

    public String getPORT8045() {
        return PORT8045;
    }

    public String getAPPID8045() {
        return APPID8045;
    }

    public String getAPPPWD8045() {
        return APPPWD8045;
    }

    public String getIp(){
        return IP;
    }
    public String getPORT(){
        return PORT;
    }
    public String getAPPID(){
        return APPID;
    }
    public String getAPPPWD(){
        return APPPWD;
    }

    public boolean getMetersize(Context context){
        bd = new BDhelper( context,"homaywater");
        Cursor cursor = bd.getReadableDatabase().query("homaytable", null, null, null,null, null, null);
        boolean res = cursor.getCount()==0;
        cursor.close();bd.close();
        return res;
    }
    public String getServerPort(Context context){
        String serverPort = PreHelper.defaultCenter1(context).getStringData(PreferenceKey.BASE_PORT);
        return serverPort;
    }
    public static String getqbbhs(Context context){
        String qbbhs = PreHelper.defaultCenter1(context).getStringData(PreferenceKey.qbbhs);
        return qbbhs;
    }
}
