package com.techscl.ichat.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.techscl.ichat.R;
import com.techscl.ichat.base.MyImageLoader;
import com.techscl.ichat.base.Nearby;

import java.util.List;

/**
 * Created by 宋春麟 on 15/7/30.
 */
public class NearbyListAdapter extends BaseAdapter {
    private Context context;
    private List<Nearby> data;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private SharedPreferences preferences;

    public NearbyListAdapter(Context context, List<Nearby> data) {
        this.context = context;
        this.data = data;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        imageLoader = new ImageLoader(requestQueue, new MyImageLoader());

    }

    @Override
    public int getCount() {// 得到数据条数
        if (data != null && data.size() > 0) {
            return data.size();// 返回数据条数为列表的大小
        }
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        if (data != null && data.size() > 0) {
            return data.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {// 加载布局文件


        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);// 定义一个布局加载器

            view = inflater.inflate(R.layout.list_description, null);// 加载布局

            viewHolder = new ViewHolder();//实例化内部类
            viewHolder.title_picture = (ImageView) view.findViewById(R.id.title_picture);// 加载组件
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.hitCount = (TextView) view.findViewById(R.id.hitCount);
            viewHolder.commentCount = (TextView) view.findViewById(R.id.commentCount);

            view.setTag(viewHolder);// 保存到内部类
        } else {
            viewHolder = (ViewHolder) view.getTag();// 从内部类读取
        }

        if (data != null && data.size() > 0) {
            Nearby nearby = data.get(i);
            String newImgUrl = nearby.getDeal_img();
            ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(viewHolder.title_picture,
                    R.mipmap.load, R.mipmap.load);// 参数说明:图片地址,默认图片,加载失败显示的图片
            imageLoader.get(newImgUrl, imageListener);//加载图片
            viewHolder.title.setText(nearby.getDeal_title());// 设置新闻标题
            viewHolder.time.setText(nearby.getDeal_subcate());// 设置更新时间
            viewHolder.hitCount.setText(nearby.getPrice());//设置新闻关注热度
            viewHolder.commentCount.setText(nearby.getValue());// 设置评论条数
        }
        return view;
    }

    public class ViewHolder {// 定义一个内部类,用于缓存组件
        ImageView title_picture;
        TextView title, time, hitCount, commentCount;
    }
}
