package com.pili.pldroid.streaming.camera.demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pili.pldroid.streaming.camera.demo.bean.QueueBean;

import java.util.ArrayList;

/**
 * Created by admin on 2016/4/12.
 */
public class QueueAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<QueueBean>mData;
    public QueueAdapter(Context context, ArrayList<QueueBean> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QueueBean item = (QueueBean) getItem(position);
        if(convertView == null){
//            convertView = LayoutInflater.from(mContext).inflate()
        }
        return null;
    }

    class ViewHolder {
        ImageView iv_avatar;
        TextView tv_username;
        TextView tv_time;
    }
}
