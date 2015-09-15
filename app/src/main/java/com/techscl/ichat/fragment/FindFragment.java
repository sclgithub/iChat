package com.techscl.ichat.fragment;

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
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.techscl.ichat.R;
import com.techscl.ichat.activity.CodeScanActivity;
import com.techscl.ichat.activity.MainActivity;
import com.techscl.ichat.activity.NewsListActivity;
import com.techscl.ichat.base.MyStringRequest;
import com.techscl.ichat.base.Weather;
import com.techscl.ichat.utils.FormatCodeUtil;
import com.techscl.ichat.utils.L;

import pl.droidsonroids.gif.GifImageView;


/**
 * Created by 宋春麟 on 15/8/27.
 */
public class FindFragment extends Fragment implements View.OnClickListener {
    private static final int UPDATE_TIME = 5000;
    private static int LOCATION_COUTNS = 0;
    private TableRow news_rss, nearby, shakes, scanner_code;
    private TextView weather, max_temp, min_temp, wind, pm25, address, aqi_notice;
    private Toolbar toolbar;
    private LocationClient locationClient = null;
    private GifImageView gifImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_find, null);

        initView(view);

        getPosition();

        return view;

    }

    /**
     * 获取位置
     */
    private void getPosition() {
        locationClient = new LocationClient(getActivity());
        //设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        //是否打开GPS
        option.setCoorType("bd09ll");       //设置返回值的坐标类型。
        option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级
        option.setProdName("LocationDemo"); //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setScanSpan(UPDATE_TIME);    //设置定时定位的时间间隔。单位毫秒
        option.setAddrType("all");// 返回定位地址
        locationClient.setLocOption(option);

        //注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                // TODO Auto-generated method stub
                if (location == null) {
                    return;
                }
                StringBuffer sb = new StringBuffer(256);
                sb.append("Time : ");
                sb.append(location.getTime());
                sb.append("\nError code : ");
                sb.append(location.getLocType());
                sb.append("\nLatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nLontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nRadius : ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    sb.append("\nSpeed : ");
                    sb.append(location.getSpeed());
                    sb.append("\nSatellite : ");
                    sb.append(location.getSatelliteNumber());
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    sb.append("\nAddress : ");
                    sb.append(location.getAddrStr());
                }
                LOCATION_COUTNS++;
                sb.append("\n检查位置更新次数：");
                sb.append(String.valueOf(LOCATION_COUTNS));
                if (location != null) {
                    L.i(location.getAddrStr());
                    String[] temp = location.getAddrStr().split("省");
                    L.i(temp[0]);
                    address.setText(location.getAddrStr());
                    String[] address = temp[1].split("市");
                    L.i(address[0]);
                    getWeather(FormatCodeUtil.codingFormat(address[0]));

                    locationClient.stop();
                }
            }

            @Override
            public void onReceivePoi(BDLocation location) {
            }

        });
        locationClient.start();
    }

    private void getWeather(String area) {
        MyStringRequest stringRequest = new MyStringRequest("http://v.juhe.cn/weather/index?format=2&cityname=" + area + "&key=74391d620131c3f27625cde9fab699af", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();// 实例化gson对象
                Weather weathers = gson.fromJson(s, Weather.class);
                L.i("天气:" + s.length());
                L.i("weather" + s);
                if (s.length() > 50) {
                    weather.setText("天气:" + weathers.getResult().getToday().getWeather());
                    if (weathers.getResult().getToday().getWeather().toString().equals("晴")) {
                        gifImageView.setImageResource(R.drawable.sunny);
                    } else if (weathers.getResult().getToday().getWeather().toString().contains("多云")) {
                        gifImageView.setImageResource(R.drawable.cloudy);
                    } else if (weathers.getResult().getToday().getWeather().toString().contains("雪")) {
                        gifImageView.setImageResource(R.drawable.snow);
                    } else if (weathers.getResult().getToday().getWeather().toString().contains("雷阵雨")) {
                        gifImageView.setImageResource(R.drawable.rain);
                    } else if (weathers.getResult().getToday().getWeather().toString().contains("雨")) {
                        gifImageView.setImageResource(R.drawable.lighting);
                    }
                    max_temp.setText("气温:" + weathers.getResult().getSk().getTemp() + "℃");
                    min_temp.setText("温差:" + weathers.getResult().getToday().getTemperature());
                    wind.setText(weathers.getResult().getSk().getWind_direction()+weathers.getResult().getSk().getWind_strength());
                    pm25.setText("紫外线强度:"+weathers.getResult().getToday().getUv_index());
                    aqi_notice.setText(weathers.getResult().getToday().getDressing_advice());
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

//    /**
//     * 获取空气污染指数
//     *
//     * @param area
//     */
//    private void getAQI(String area) {
//        MyStringRequest stringRequest = new MyStringRequest("http://v.juhe.cn/weather/index?format=2&cityname=" + area + "&key=74391d620131c3f27625cde9fab699af", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                Gson gson = new Gson();// 实例化gson对象
//                AQI aqi = gson.fromJson(s, AQI.class);
//                pm25.setText("AQI指数:" + aqi.getRetData().getAqi() + "," + aqi.getRetData().getLevel());
//                if (aqi.getRetData().getAqi() <= 50) {
//                    aqi_notice.setText(getString(R.string.aqi_a));
//                } else if (aqi.getRetData().getAqi() <= 100) {
//                    aqi_notice.setText(getString(R.string.aqi_b));
//                } else if (aqi.getRetData().getAqi() <= 150) {
//                    aqi_notice.setText(getString(R.string.aqi_c));
//                } else if (aqi.getRetData().getAqi() <= 200) {
//                    aqi_notice.setText(getString(R.string.aqi_d));
//                } else if (aqi.getRetData().getAqi() <= 300) {
//                    aqi_notice.setText(getString(R.string.aqi_e));
//                } else {
//                    aqi_notice.setText(getString(R.string.aqi_f));
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//        });
//        MainActivity.requestQueue.add(stringRequest);
//    }

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
        gifImageView = (GifImageView) view.findViewById(R.id.gifImageView);
        address = (TextView) view.findViewById(R.id.address);
        aqi_notice = (TextView) view.findViewById(R.id.aqi_notice);
        scanner_code = (TableRow) view.findViewById(R.id.scanner_code);
        nearby = (TableRow) view.findViewById(R.id.nearby);
        shakes = (TableRow) view.findViewById(R.id.shakes);
        scanner_code.setOnClickListener(this);
        nearby.setOnClickListener(this);
        shakes.setOnClickListener(this);
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
                startActivity(new Intent(getActivity(), NewsListActivity.class));
                break;
            case R.id.scanner_code:
                startActivity(new Intent(getActivity(), CodeScanActivity.class));
                break;
            case R.id.nearby:

                break;
        }
    }


}
