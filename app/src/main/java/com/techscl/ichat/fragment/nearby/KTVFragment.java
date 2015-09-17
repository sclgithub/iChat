package com.techscl.ichat.fragment.nearby;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.techscl.ichat.R;
import com.techscl.ichat.activity.BuyActivity;
import com.techscl.ichat.activity.MainActivity;
import com.techscl.ichat.activity.NearbyActivity;
import com.techscl.ichat.adapter.NearbyListAdapter;
import com.techscl.ichat.base.Nearby;
import com.techscl.ichat.base.StreamRequest;
import com.techscl.ichat.utils.L;
import com.techscl.ichat.utils.NearbyListSAX;
import com.techscl.ichat.utils.To;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by songchunlin on 15/7/30.
 * Android
 */
public class KTVFragment extends android.support.v4.app.Fragment {
    private ListView listView;
    private Handler handler;
    private NearbyListAdapter nearbyListAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nearby_fragment, null);
        initView(view);

        return view;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        getList("KTV");

    }

    /**
     * 获取网络数据
     *
     */
    public void getList(String type) {
        StreamRequest request = new StreamRequest("http://api.union.meituan.com/data/api?city=" + NearbyActivity.lbs +
                "&category=" + type +
                "&limit=10&district_name=" + NearbyActivity.region +
                "&key=28852c906cf43bec61300362d45cdd97387&sort=1",

                new Response.Listener<InputStream>() {
                    /**
                     * 请求成功
                     * @param inputStream 返回流
                     */
                    @Override
                    public void onResponse(InputStream inputStream) {
                        L.i("附近成功");
                        handler = new Handler(new Handler.Callback() {
                            /**
                             * 接收解析完成的数据
                             * @param message
                             * @return
                             */
                            @Override
                            public boolean handleMessage(Message message) {
                                if (message.what == 10) {
                                    final List<Nearby> list = (List<Nearby>) message.obj;
                                    nearbyListAdapter=new NearbyListAdapter(getActivity(),list);
                                    listView.setAdapter(nearbyListAdapter);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent buy = new Intent(getActivity(), BuyActivity.class);
                                            buy.putExtra("title", list.get(position).getDeal_title());
                                            buy.putExtra("url", list.get(position).getDeal_wap_url());
                                            startActivity(buy);
                                        }
                                    });
                                    To.show("刷新成功");
                                }

                                return false;
                            }
                        });
                        /**
                         * 解析数据
                         */
                        SAXParserFactory spx = SAXParserFactory.newInstance();
                        try {
                            SAXParser sp = spx.newSAXParser();
                            sp.parse(inputStream, new NearbyListSAX(handler));

                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "刷新失败", Toast.LENGTH_SHORT).show();

            }
        });
        MainActivity.requestQueue.add(request);
    }

}





