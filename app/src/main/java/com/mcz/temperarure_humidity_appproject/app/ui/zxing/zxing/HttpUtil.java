package com.mcz.temperarure_humidity_appproject.app.ui.zxing.zxing;

import android.util.Log;
import android.widget.Toast;

import com.mcz.temperarure_humidity_appproject.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    //使用Get方式获取服务器上数据
    public static void sendOkHttpRequest(final String address, final okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //使用Post方式向服务器上提交数据并获取返回提示数据
    public static void sendOkHttpResponse(final String address, final RequestBody requestBody, final okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
//        JSONObject object;
        Request request = new Request.Builder()
                .url(address).post(requestBody).build();
        HTTPSCerUtils.setTrustAllCertificate(client.newBuilder());
        client.newCall(request).enqueue(callback);
    }
    /**
     * 发送异步post()请求
     */
    public String postAsynsRequest(String bh) {
        OkHttpClient okhttpClient = new OkHttpClient();
        final String[] res = {""};
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        //formBody.add("protocolCode", bh);
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .post(formBody.build())
                .build();
        Call call2 = okhttpClient.newCall(request);
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("test","失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                res[0] = response.body().string();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("test","成功"+ res[0]);
                    }
                });
            }
        });
        return res[0];
    }

    public String getHistory(final String qbbh) throws IOException, InterruptedException {

        final String[] result = new String[1];
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient.Builder okHttpClient =new OkHttpClient.Builder();

                RequestBody requestBody=new FormBody.Builder()
                        .add("protocolCode",qbbh)
                        .build();

                Request request = new Request.Builder()
                        .url("https://222.180.163.205:8045/homay-nbiot-api/api/nbiot/datacollection/list")
                        .post(requestBody)
                        .build();
                HTTPSCerUtils.setTrustAllCertificate(okHttpClient);
                Call call = okHttpClient.build().newCall(request);
                call.enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("test","------------:"+e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //Log.i("test","------------:"+response.body().string());
                        result[0] = response.body().string();
                    }
                });
            }
        }).start();
        Thread.sleep(500);
        //Log.i("test","*********************:"+ result[0]);
        return result[0];
    }
}
