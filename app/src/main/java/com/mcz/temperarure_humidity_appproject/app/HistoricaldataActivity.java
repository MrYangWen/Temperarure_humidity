package com.mcz.temperarure_humidity_appproject.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.gyf.barlibrary.ImmersionBar;
import com.mcz.temperarure_humidity_appproject.R;
import com.mcz.temperarure_humidity_appproject.app.model.DataInfo;
import com.mcz.temperarure_humidity_appproject.app.model.PullrefreshListviewAdapter2;
import com.mcz.temperarure_humidity_appproject.app.ui.zxing.zxing.HTTPSCerUtils;
import com.mcz.temperarure_humidity_appproject.app.ui.zxing.zxing.HttpUtil;
import com.mcz.temperarure_humidity_appproject.app.view.view.IPullToRefresh;
import com.mcz.temperarure_humidity_appproject.app.view.view.LoadingLayout;
import com.mcz.temperarure_humidity_appproject.app.view.view.PullToRefreshBase;
import com.mcz.temperarure_humidity_appproject.app.view.view.PullToRefreshFooter;
import com.mcz.temperarure_humidity_appproject.app.view.view.PullToRefreshHeader;
import com.mcz.temperarure_humidity_appproject.app.view.view.PullToRefreshListView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HistoricaldataActivity extends AppCompatActivity {

    private AlertDialog dialog;
    String deviceId = "";
    String gatewayId = "";
    String token2 = "";
    String login_appid = "";
    SharedPreferences sp2;
    String qbbh = "";
    HttpUtil hu;
    @BindView(R.id.main_pull_refresh_his)
    PullToRefreshListView mListView2;

    @BindView(R.id.main_relative_main)
    RelativeLayout rela_nodata2;

    @BindView(R.id.img_histor)
    ImageView back;

    @BindView(R.id.open)
    Button opendoor;

    @BindView(R.id.close)
    Button closedoor;

    @BindView(R.id.f001)
    Button f001;

    @BindView(R.id.f002)
    Button f002;

    private View mNoMoreView2;

    private PullrefreshListviewAdapter2 adapter2;

    private List<DataInfo> mlist2 = null;

    boolean type_choose2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historicaldata);//開始運行main_activity_layout界面
        ButterKnife.bind(this);
        ImmersionBar.with(this)
                .statusBarColor(R.color.blues)
                .fitsSystemWindows(true)
                .init();
        sp2 = PreferenceManager.getDefaultSharedPreferences(this);
        hu = new HttpUtil();
        login_appid = sp2.getString("appId", "");
        init();
        Intent intent = getIntent();
        deviceId = intent.getStringExtra("deviceId");
        qbbh = intent.getStringExtra("qbbh");
        gatewayId = intent.getStringExtra("gatewayId");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        opendoor.setOnClickListener(new View.OnClickListener() { //添加开阀指令
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(HistoricaldataActivity.this);
                alert.setTitle("提醒");
                alert.setMessage("您确定要添加开阀指令吗？");
                alert.setPositiveButton("确定", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handler.obtainMessage(0).sendToTarget();
                    }
                });
                alert.setNegativeButton("取消",null);
                alert.show();
            }
        });

        closedoor.setOnClickListener(new View.OnClickListener() { //添加关阀指令
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(HistoricaldataActivity.this);
                alert.setTitle("提醒");
                alert.setMessage("您确定要添加关阀指令吗？");
                alert.setPositiveButton("确定", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handler.obtainMessage(1).sendToTarget();
                    }
                });
                alert.setNegativeButton("取消",null);
                alert.show();
            }
        });

        f001.setOnClickListener(new View.OnClickListener() { //添加地址预设指令
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(HistoricaldataActivity.this);
                alert.setTitle("提醒");
                alert.setMessage("您确定要添加表地址预设指令吗？");
                alert.setPositiveButton("确定", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handler.obtainMessage(2).sendToTarget();
                    }
                });
                alert.setNegativeButton("取消",null);
                alert.show();
            }
        });

        f002.setOnClickListener(new View.OnClickListener() { //添加出厂设置指令
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HistoricaldataActivity.this);
                    //加载布局
                    View contentView = View.inflate(HistoricaldataActivity.this, R.layout.dialog, null);
                    //查找控件
                    final EditText etjdtb = (EditText) contentView.findViewById(R.id.et_jdtb);
                    final RadioButton open = (RadioButton) contentView.findViewById(R.id.btnOpen);
                    final RadioButton close = (RadioButton) contentView.findViewById(R.id.btnClose);
                    Button btnOk = (Button) contentView.findViewById(R.id.btn_ok);
                    Button btnCancle = (Button) contentView.findViewById(R.id.btn_cancle);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            String jdtb = etjdtb.getText().toString();
                            if (jdtb.equals("")) {
                                Toast.makeText(HistoricaldataActivity.this, "请输入同步方量", Toast.LENGTH_SHORT).show();
                            } else {
                                String turnoff = "";
                                if(open.isChecked()){
                                    turnoff="01";
                                }else{
                                    turnoff="00";
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("turnoff",turnoff);
                                bundle.putString("jdtb",jdtb);
                                Message message = Message.obtain();
                                message.what=3;
                                message.setData(bundle);
                                handler.handleMessage(message);
                                //关闭对话框
                                dialog.dismiss();
                            }
                        }
                    });
                    btnCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            //关闭对话框
                            dialog.dismiss();
                        }
                    });
                    //设置内容
                    builder.setView(contentView);
                    //创建
                    dialog = builder.create();
                    //显示
                    dialog.show();
                }
        });

    }

    int item_position = 0;

    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    private void init() {
        token2 = sp2.getString("token", "");
        hintKbTwo();
        rela_nodata2.setVisibility(View.GONE);
        //搜索設備時候監聽圖片
        bofang();
        ///listview
        mNoMoreView2 = getLayoutInflater().inflate(R.layout.no_device_more_footer, null);
        // mListView.setOnItemClickListener(this);
        mListView2.setLoadingLayoutCreator(new PullToRefreshBase.LoadingLayoutCreator() {
            @Override
            public LoadingLayout create(Context context, boolean headerOrFooter,
                                        PullToRefreshBase.Orientation orientation) {
                if (headerOrFooter) {
                    return new PullToRefreshHeader(context);
                } else {
                    return new PullToRefreshFooter(context, PullToRefreshFooter.Style.EMPTY_NO_MORE.EMPTY_NO_MORE);
                }
            }
        });
//        Drawable drawable = getResources().getDrawable(R.color.transparent);
        mListView2.getRefreshableView().setSelector(getResources().getDrawable(R.color.transparent));
        //设置可上拉刷新和下拉刷新
        mListView2.setMode(PullToRefreshBase.Mode.BOTH);
        //异步加载数据
        mListView2.setOnRefreshListener(new IPullToRefresh.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView,
                                  boolean headerOrFooter) {
                //填充数据
//                getList_AsyncTask(headerOrFooter);
                new LoadDataAsyncTask2(headerOrFooter, false).execute();
            }

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        adapter2 = new PullrefreshListviewAdapter2(this);
        mListView2.getRefreshableView().addFooterView(mNoMoreView2);
        mListView2.setAdapter(adapter2);
        mListView2.getRefreshableView().removeFooterView(mNoMoreView2);
    }

    /**
     * listview添加数据
     *
     * @return
     */
//查詢當個設備方法
    private List<DataInfo> ListviewADD_Data() {
        try {
            login_appid = sp2.getString("appId", "");
            ///////////////////////////************************2.3.2 查询单个设备信息***********************////////////////////////////
//            String add_url = Config.all_url + "/iocm/app/dm/v1.3.0/devices?appId=" + login_appid
//                    + "&pageNo=" + Fnum + "&pageSize=" + Onum;
            ////////////////////////////////////////////************************查询设备历史数据*****************/////////////////////////////////////
            //String add_url = Config.all_url + "/iocm/app/data/v1.1.0/deviceDataHistory?deviceId=" + deviceId + "&gatewayId=" + gatewayId;
            String json = hu.getHistory(qbbh,sp2.getString("sport", ""));
            JSONArray joa = new JSONArray();
            joa = JSONArray.parseArray(json);
            Log.i("bbbbbbbbbbbbbbbbbbbbbbb", "josn1" + json);
            mlist2 = new ArrayList<DataInfo>();
            //JSONObject jo = new JSONObject();
            /*JSONArray jsonArray = jo.getJSONArray("deviceDataHistoryDTOs");*/
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DecimalFormat decimalFormat=new DecimalFormat("0.000");
            for (int i = 0; i < joa.size(); i++) {
                DataInfo dataInfo = new DataInfo();

                String time =  sdf.format(new Date(Long.valueOf(joa.getJSONObject(i).getString("xcsj"))));
                String timestamp = time;
                dataInfo.setDevicetimestamp(timestamp);
                //String ser_data = joa.getJSONObject(i).getString("data");
                //   Log.i("bbbbbbbbbbbbbbbbbbbbbb","timestamp            "+timestamp);
//                dataInfo.setDevicetimestamp(timestamp);
                //JSONObject jsonObject = new JSONObject(ser_data);
//                dataInfo.setDevicehumidity(jsonObject.optString("Humidity"));
//                dataInfo.setDevicetemperature(jsonObject.optString("Temperature"));

                //String json1 = "";
                //JSONArray jo1 = new JSONArray();

                try {
                    /*json1 = hu.getHistory(jsonObject.optString("message").substring(14, 28));
                    jo1 = new JSONObject(json1);*/

                    //String add_url1 = "https://222.180.163.205:8045/homay-nbiot-api/api/nbiot/datacollection/list?protocolCode=" + jsonObject.optString("message").substring(14, 28);
                    //json1 = hu.getHistory(jsonObject.optString("message").substring(14, 28));
                    //jo1 = JSONArray.parseArray(json1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dataInfo.setDevicetemperature(joa.getJSONObject(i).getString("qbll")+"");//m³
                String scds = joa.getJSONObject(i).getString("scds");
                Float dayll = Float.parseFloat(joa.getJSONObject(i).getString("qbll")) - Float.parseFloat(scds);
                dataInfo.setDevicehumidity(decimalFormat.format(dayll)+"");//m³
                dataInfo.setMessage(joa.getJSONObject(i).toString());
                dataInfo.setBattery(joa.getJSONObject(i).getString("batteryVoltage"));
                dataInfo.setSoftware(joa.getJSONObject(i).getString("software"));
                dataInfo.setNetwork(joa.getJSONObject(i).getString("network"));
                mlist2.add(dataInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mlist2;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 异步加载任务
     */
    //下标标识
    private class LoadDataAsyncTask2 extends AsyncTask<Void, Void, List<DataInfo>> {
        private boolean mHeaderOrFooter;
        private boolean is_Add;

        public LoadDataAsyncTask2(boolean headerOrFooter, boolean isadd) {
            mHeaderOrFooter = headerOrFooter;
            is_Add = isadd;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mHeaderOrFooter) {
                mListView2.setVisibility(View.VISIBLE);
            }
            mListView2.getRefreshableView().removeFooterView(mNoMoreView2);
        }

        @Override
        protected List<DataInfo> doInBackground(Void... params) {
            if (HistoricaldataActivity.this.isFinishing()) {
                return null;
            }
            List<DataInfo> result = null;
            try {
                //首先判断是否查询所有还是单个
                if (!is_Add) {
                    if (mHeaderOrFooter && type_choose2) {
                        //下拉刷新走的方法
                        if (type_choose2 == false) {
                            adapter2.clearItem();
                            mlist2 = new ArrayList<DataInfo>();
                        }
                        type_choose2 = true;
                        result = ListviewADD_Data();
//                    begin=7;
                    } else {
//                    //上拉加载的方法
                        if (type_choose2 == false) {
                            adapter2.clearItem();
                            mlist2 = new ArrayList<DataInfo>();
                            result = ListviewADD_Data();
                            type_choose2 = true;
                        } else {
//                            adapter.clearItem();
                            result = ListviewADD_Data();
                            type_choose2 = true;
                        }
                    }
                } else {
                    //更据ID查询
                    //  result = Listview_Inputmanual(datajson);
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * 完成时的方法
         */
        @Override
        protected void onPostExecute(List<DataInfo> result) {
            super.onPostExecute(result);
            mListView2.onRefreshComplete();
            if (HistoricaldataActivity.this.isFinishing()) {
                return;
            }
            if (result != null) {
                if (mHeaderOrFooter) {
                    CharSequence dateText = DateFormat.format("yyyy-MM-dd kk:mm:ss", new Date());
                    for (LoadingLayout layout : mListView2.getLoadingLayoutProxy(true, false).getLayouts()) {
                        ((PullToRefreshHeader) layout).setLastRefreshTime(":" + dateText);
                    }
                    adapter2.clearItem();
                    mlist2 = new ArrayList<DataInfo>();
                }
                if (adapter2.getCount() == 0 && result.size() == 0) {
                    mListView2.setVisibility(View.GONE);
                    mListView2.getRefreshableView().removeFooterView(mNoMoreView2);
                    rela_nodata2.setVisibility(View.VISIBLE);
                } else if (result.size() > 5) {
                    //在这里判断数据的多少确定下一步能否上拉加载
//                    if (result.size()<5){
//                        mListView.getRefreshableView().removeFooterView(mNoMoreView);
//                    }else{
                    if (result.size() % 5 == 0) {
                    } else {
                        mListView2.setFooterRefreshEnabled(false);
                        mListView2.getRefreshableView().addFooterView(mNoMoreView2);
                        rela_nodata2.setVisibility(View.GONE);
                        mListView2.setVisibility(View.VISIBLE);
                    }
//                    }
                } else if (mHeaderOrFooter) {
                    if (result.size() > 0) {
                        rela_nodata2.setVisibility(View.GONE);
                        mListView2.setVisibility(View.VISIBLE);
                    }
                    mListView2.setFooterRefreshEnabled(true);
                    mListView2.getRefreshableView().removeFooterView(mNoMoreView2);
                }
                addlistdata(result);
//
                adapter2.notifyDataSetChanged();//刷新完成
//                }
            } else {
                rela_nodata2.setVisibility(View.VISIBLE);
                Toast.makeText(HistoricaldataActivity.this, "暂无设备信息", Toast.LENGTH_SHORT).show();
            }
//            new  Thread  (new Runnable() {
//                @Override
//                public void run() {
//                    new LoadDataAsyncTask2(true, false).execute();//查询所有
//                }
//            }) .start();
        }
    }

    /**
     * 数据循环添加
     *
     * @param result
     */
    private void addlistdata(List<DataInfo> result) {
        int count = result.size();
        if (result.size() > 0) {
            sp2.edit().putBoolean("isplayer", true).commit();
            try {
                mediaPlayer.stop();
                mediaPlayer.prepare();
                mediaPlayer.seekTo(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        adapter2.clearItem();
        for (int i = 0; i < count; i++) {
            adapter2.addItem(result.get(i));
            mlist2.add(result.get(i));
            //mlist集合是用于存放界面中的值  并在跳转时传入item界面
        }
    }

    MediaPlayer mediaPlayer = null;

    private void bofang() {
//        为activity注册的默认 音频通道 。
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        AudioManager audioService = (AudioManager) this
                .getSystemService(Context.AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
        }
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // 注册事件。当播放完毕一次后，重新指向流文件的开头，以准备下次播放。
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });
        AssetFileDescriptor file = this.getResources().openRawResourceFd(
                R.raw.airpp);
        try {
            mediaPlayer.setDataSource(file.getFileDescriptor(),
                    file.getStartOffset(), file.getLength());
            file.close();
            mediaPlayer.setVolume(0.10f, 200L);
            mediaPlayer.prepare();
        } catch (IOException ioe) {
            Log.w("eeeee", ioe);
            mediaPlayer = null;
        }
    }

    /**
     * 在进入界面的时候自动加载一遍
     * 第一次有数据显示
     */
    private void refreshButtonClicked() {
        mListView2.setVisibility(View.VISIBLE);
        mListView2.setMode(IPullToRefresh.Mode.BOTH);
        mListView2.setRefreshing();
    }

    //MyThread线程任务类
    @Override
    protected void onResume() {
        super.onResume();
        //实列话MyThread类
        if ((adapter2 != null && adapter2.getCount() == 0)) {
            refreshButtonClicked();
        }
        hintKbTwo();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshButtonClicked();
        hintKbTwo();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            String instructions = "";
            switch (msg.what){
                case 0:
                    instructions="3051";
                    break;
                case 1:
                    instructions="3052";
                    break;
                case 2:
                    instructions="F001";
                    break;
                case 3:
                    instructions="F002";
                    break;
            }
            if(instructions.equals("") || instructions == ""){
                return;
            }
            OkHttpClient.Builder okHttpClient =new OkHttpClient.Builder();
            Request request = new Request.Builder()
                    .url("https://222.180.163.205:"+ sp2.getString("sport", "")+"/homay-nbiot-api/api/nbiot/datacollection/"+instructions+"/"+qbbh)
                    .get()
                    .build();
            if(instructions.equals("F002")){
                Bundle bundle = msg.getData();
                request = new Request.Builder()
                        .url("https://222.180.163.205:"+ sp2.getString("sport", "")+"/homay-nbiot-api/api/nbiot/datacollection/"+instructions+"/"+qbbh+"?turnOff="+bundle.getString("turnoff")+"&synchronize="+bundle.getString("jdtb"))
                        .get()
                        .build();
            }
            HTTPSCerUtils.setTrustAllCertificate(okHttpClient);
            Call call = okHttpClient.build().newCall(request);
            final String finalInstructions = instructions;
            call.enqueue(new okhttp3.Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("test","------------:"+e.toString());
                    Looper.prepare();
                    Toast.makeText(HistoricaldataActivity.this,"网络请求失败",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //Log.i("test","------------:"+response.body().string());
                    int code = response.code();
                    if(code == 200){
                        Looper.prepare();
                        if(finalInstructions == "3051"){
                            Toast.makeText(HistoricaldataActivity.this,"开阀指令添加成功",Toast.LENGTH_LONG).show();
                        }else if(finalInstructions == "3052"){
                            Toast.makeText(HistoricaldataActivity.this,"关阀指令添加成功",Toast.LENGTH_LONG).show();
                        }else if(finalInstructions == "F001"){
                            Toast.makeText(HistoricaldataActivity.this,"表地址预设指令添加成功",Toast.LENGTH_LONG).show();
                        }else if(finalInstructions == "F002"){
                            Toast.makeText(HistoricaldataActivity.this,"表出厂设置指令添加成功",Toast.LENGTH_LONG).show();
                        }
                        Looper.loop();

                        /*AlertDialog.Builder builder  = new AlertDialog.Builder(HistoricaldataActivity.this);
                        builder.setTitle("消息" ) ;
                        if(finalInstructions == "3051"){
                            builder.setMessage("开阀指令添加成功" ) ;
                        }else if(finalInstructions == "3052"){
                            builder.setMessage("关阀指令添加成功" ) ;
                        }
                        builder.setPositiveButton("确定" ,  null );
                        AlertDialog localAlertDialog = builder.create();
                        //localAlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                        localAlertDialog.show();*/
                    }else{
                        Log.i("code","------------:"+code);
                        Looper.prepare();
                        Toast.makeText(HistoricaldataActivity.this,"指令添加失败："+code,Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }
            });
            super.handleMessage(msg);
        }
    };
}
