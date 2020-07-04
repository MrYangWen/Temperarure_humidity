package com.mcz.temperarure_humidity_appproject.app.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mcz.temperarure_humidity_appproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by pc on 2018/5/12.
 */

public class PullrefreshListviewAdapter2 extends BaseAdapter {
    private Context mContext2;
    private List<DataInfo> mlist2 = null;
    private String time;

    public static String timestampToDateString(String timestamp) {//throws ParseException {
        String strDate = "";
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Object obj = sdf.parse(timestamp).getTime();
            String s1 = obj.toString();
            long lastTime = Long.parseLong(s1);
            lastTime = lastTime + 28800000;
            Date date = new Date(lastTime);
            strDate = sim.format(date.getTime());
        } catch (Exception e) {
            return "";
        }
        return strDate;
    }

    public PullrefreshListviewAdapter2(Context context) {
        this.mContext2 = context;
        mlist2 = new ArrayList<DataInfo>();
    }

    public void clearItem() {
        mlist2.clear();
    }

    public void addItem(DataInfo item) {
        mlist2.add(item);
    }

    @Override
    public int getCount() {
        return mlist2.size();
    }

    @Override
    public DataInfo getItem(int position) {
        DataInfo item = null;
        if (position >= 0 && getCount() > position) {
            item = mlist2.get(position);
        }
        return item;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext2).inflate(R.layout.hisitem, parent, false);
            vh = new ViewHolder();
            vh.temperature = (TextView) convertView.findViewById(R.id.temperature);
            vh.timestamp = (TextView) convertView.findViewById(R.id.timestamp);
            vh.humidity = (TextView) convertView.findViewById(R.id.humidity);
            vh.network = (TextView) convertView.findViewById(R.id.network);
            vh.software = (TextView) convertView.findViewById(R.id.software);
            vh.battery = (TextView) convertView.findViewById(R.id.battery);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        DataInfo info = getItem(position);
        //时间格式转换
        time = info.getDevicetimestamp();
        String TIME = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + time.substring(8, 9) + time.substring(9, 11) + ":" + time.substring(11, 13) + ":" + time.substring(13, 15) + time.substring(15, 16);

        vh.humidity.setText(info.getDevicehumidity());
        vh.temperature.setText(info.getDevicetemperature());//
        vh.timestamp.setText(time);
        vh.network.setText(info.getNetwork());
        vh.software.setText(info.getSoftware());
        vh.battery.setText(info.getBattery());
        return convertView;
    }

    class ViewHolder {
        TextView temperature;
        TextView timestamp;
        TextView humidity;
        TextView network;
        TextView software;
        TextView battery;
    }
}
