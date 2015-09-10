package com.techscl.lovechat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.easemob.chatuidemo.R;
import com.google.gson.Gson;
import com.techscl.lovechat.Weather;
import com.techscl.lovechat.activity.MainActivity;
import com.techscl.utils.FormatCodeUtil;
import com.techscl.utils.L;


/**
 * Created by 宋春麟 on 15/8/27.
 */
public class FindFragment extends Fragment implements View.OnClickListener {
    private TableRow news_rss;
    private TextView weather, max_temp, min_temp, wind, pm25;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_find, null);

        initView(view);

        getWeather(FormatCodeUtil.codingFormat("大连"));

        return view;

    }

    private void getWeather(String area) {
        StringRequest stringRequest = new StringRequest("http://api.lib360.net/open/weather.json?city=" + area, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();// 实例化gson对象
                Weather weathers = gson.fromJson(s, Weather.class);
                L.i("天气:" + s.length());
                L.i("weather"+s);
                if (s.length() > 2130) {
                    weather.setText(weathers.getDatanow().getWeather() + "");
                    max_temp.setText(weathers.getDatanow().getTempMax() + "℃");
                    min_temp.setText(weathers.getDatanow().getTempMin() + "℃");
                    wind.setText(weathers.getDatanow().getWind().toString());
                    pm25.setText(weathers.getPm25());
                } else {
                    weather.setText("多云");
                    max_temp.setText("25℃");
                    min_temp.setText("21℃");
                    wind.setText("东南风4-5级");
                    pm25.setText("35");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        MainActivity.requestQueue.add(stringRequest);
    }

    /**
     * 初始化
     *
     * @param view
     */
    private void initView(View view) {
        news_rss = (TableRow) view.findViewById(R.id.news_rss);
        weather = (TextView) view.findViewById(R.id.weather);
        max_temp = (TextView) view.findViewById(R.id.max_temp);
        min_temp = (TextView) view.findViewById(R.id.min_temp);
        wind = (TextView) view.findViewById(R.id.wind);
        pm25 = (TextView) view.findViewById(R.id.pm25);
        news_rss.setOnClickListener(this);
    }

    /**
     * 点击监听
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.news_rss:

                break;
        }
    }

}
