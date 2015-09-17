package com.techscl.ichat.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.techscl.ichat.R;
import com.techscl.ichat.adapter.NearbyAdapter;
import com.techscl.ichat.fragment.nearby.FoodFragment;
import com.techscl.ichat.fragment.nearby.KFCFragment;
import com.techscl.ichat.fragment.nearby.KTVFragment;
import com.techscl.ichat.fragment.nearby.LifeFragment;
import com.techscl.ichat.fragment.nearby.MovieFragment;
import com.techscl.ichat.fragment.nearby.NearbyTravelFragment;
import com.techscl.ichat.fragment.nearby.PlayFragment;
import com.techscl.ichat.fragment.nearby.ShopFragment;
import com.techscl.ichat.fragment.nearby.TravelFragment;
import com.techscl.ichat.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchunlin on 15/9/17.
 */
public class NearbyActivity extends FragmentActivity {
    private static final int UPDATE_TIME = 5000;
    public static String lbs, region;
    private static int LOCATION_COUTNS = 0;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NearbyAdapter nearbyAdapter;
    private Toolbar toolbar;
    private LocationClient locationClient = null;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_nearby);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.mainviewPager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        getPosition();
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<Fragment> fragments = new ArrayList<>();// 加载fragment
        viewPager.setOffscreenPageLimit(1);
        fragments.add(new FoodFragment());
        fragments.add(new MovieFragment());
        fragments.add(new KTVFragment());
        fragments.add(new NearbyTravelFragment());
        fragments.add(new PlayFragment());
        fragments.add(new ShopFragment());
        fragments.add(new TravelFragment());
        fragments.add(new LifeFragment());
        fragments.add(new KFCFragment());

        String[] types = getResources().getStringArray(R.array.type);

        nearbyAdapter = new NearbyAdapter(getSupportFragmentManager(), fragments, types);

        viewPager.setAdapter(nearbyAdapter);

        tabLayout.setTabsFromPagerAdapter(nearbyAdapter);//绑定标题
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabCount();

    }

    /**
     * 获取位置
     */
    private void getPosition() {
        locationClient = new LocationClient(this);
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
                    toolbar.setTitle(temp[1]);
                    String[] address = temp[1].split("市");
                    L.i(address[0]);
                    lbs = address[0];
                    temp = address[1].split("区");
                    region = temp[0] + "区";
                    locationClient.stop();
                    viewPager.setAdapter(nearbyAdapter);

                    tabLayout.setTabsFromPagerAdapter(nearbyAdapter);//绑定标题
                    tabLayout.setupWithViewPager(viewPager);
                    tabLayout.getTabCount();
                }
            }

            @Override
            public void onReceivePoi(BDLocation location) {
            }

        });
        locationClient.start();
    }

}
