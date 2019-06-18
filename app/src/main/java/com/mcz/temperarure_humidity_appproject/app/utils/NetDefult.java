package com.mcz.temperarure_humidity_appproject.app.utils;

import android.content.Context;
import android.database.Cursor;

import com.mcz.temperarure_humidity_appproject.app.ui.activity.InputManualActivity;

import java.util.ArrayList;

public class NetDefult {

    static NetDefult netDefult = new NetDefult();
    BDhelper bd;

    public synchronized static NetDefult getInstance() {
        return netDefult;
    }

    private static final String IP = "device.api.ct10649.com"; //IP
    private static final String PORT = "8743";                  //端口
    private static final String APPID = "0kjvV5MCmsvxdJsuKey8zfxOYT0a"; //APPID
    private static final String APPPWD = "YH9wZbbErSmefcNFgiBDon43gfca";//APP密钥
    private static final String meter = "";

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
}
