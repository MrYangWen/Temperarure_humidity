package com.mcz.temperarure_humidity_appproject.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.mcz.temperarure_humidity_appproject.MainActivity;
import com.mcz.temperarure_humidity_appproject.R;
import com.mcz.temperarure_humidity_appproject.app.utils.BDhelper;
import com.mcz.temperarure_humidity_appproject.app.utils.Config;
import com.mcz.temperarure_humidity_appproject.app.utils.DataManager;
import com.mcz.temperarure_humidity_appproject.app.utils.NetDefult;
import com.mcz.temperarure_humidity_appproject.app.utils.PreHelper;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mcz on 2018/1/10.
 */

public class InputManualActivity extends AppCompatActivity {

    @BindView(R.id.img_manualcancle)
    ImageView imgManualcancle;
    @BindView(R.id.txt_input_result)
    TextView txt_result;
    @BindView(R.id.edt_manual_input_name)
    EditText edtManualInput_name;
    @BindView(R.id.edt_manual_input)
    EditText edtManualInput;
    @BindView(R.id.btn_manual_input)
    Button btnManualInput;
    @BindView(R.id.btn_manual_delete)
    Button btnManualdelete;
    SharedPreferences sp;
    @BindView(R.id.txt_ceshiresult)
    TextView txt;
    @BindView(R.id.lin_result)
    LinearLayout lin_res;

    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.lin_show_input_phone)
    LinearLayout linShowInputPhone;
    @BindView(R.id.lin_intoid_news)
    LinearLayout lin_intoID;
    @BindView(R.id.tx_bar)
    TextView tx_bar;
    private int position_phone = 0;
    BDhelper bd;
    SQLiteDatabase dbwriter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.manual_layout );
        ButterKnife.bind( this );
        ImmersionBar.with( this )
                .statusBarColor( R.color.blues )
                .fitsSystemWindows( true )
                .init();
        sp = PreferenceManager.getDefaultSharedPreferences( this );
        bd = new BDhelper(InputManualActivity.this,"homaywater");
        init();

    }

    private void init() {
        tx_bar.setText( "手动输入" );
        hintKbTwo();
//        Intent intent=getIntent();
//        code=intent.getStringExtra("json");
//        edtManualInput.setText(code);
//        if (!TextUtils.isEmpty(code))
//            edtManualInput.setSelection(code.length());
        txt_result.setTextIsSelectable( true );
        imgManualcancle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent( InputManualActivity.this, MainActivity.class );
                startActivity( intent );
            }
        } );
        lin_res.setVisibility( View.GONE );
        lin_intoID.setVisibility( View.GONE );
        btnManualInput.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bh = edtManualInput_name.getText().toString().trim();
                if(bh.equals( "" ) || bh.length()!=14){
                    Toast.makeText(InputManualActivity.this, "请输入正确的表号", Toast.LENGTH_SHORT).show();
                }else{
                    dbwriter = bd.getWritableDatabase();
                    try{
                        dbwriter.execSQL( "insert into homaytable values(?)",
                                new String[] {bh});
                    }catch (Exception e){
                        Toast.makeText(InputManualActivity.this, "表号已存在", Toast.LENGTH_SHORT).show();
                        dbwriter.close();bd.close();
                    }
                    dbwriter.close();bd.close();
                    finish();
                    Intent intent = new Intent( InputManualActivity.this, MainActivity.class );
                    startActivity( intent );
                }
            }
        } );
        btnManualdelete.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    InputManualActivity.this.deleteDatabase( "homaywater" );
                }catch (Exception e){
                    Toast.makeText(InputManualActivity.this, "清空失败", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(InputManualActivity.this, "清空成功", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent( InputManualActivity.this, MainActivity.class );
                startActivity( intent );
            }
        } );
    }

    /**
     * 关闭软键盘
     */
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE );
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS );
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent( InputManualActivity.this, MainActivity.class );
        startActivity( intent );
        return super.onKeyDown(keyCode, event);
    }
}