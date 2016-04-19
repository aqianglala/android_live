package com.pili.pldroid.streaming.camera.demo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pili.pldroid.streaming.camera.demo.R;
import com.pili.pldroid.streaming.camera.demo.bean.QueueBean;
import com.pili.pldroid.streaming.camera.demo.utils.GlideCircleTransform;

import java.util.List;

/**
 * Created by admin on 2016/4/12.
 */
public class QueueAdapter extends BaseAdapter{

    private Context mContext;
    private List<QueueBean.DataEntity> mData;
    public QueueAdapter(Context context, List<QueueBean.DataEntity> data) {
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
        QueueBean.DataEntity item = (QueueBean.DataEntity) getItem(position);
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_queue,parent,false);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            holder.tv_duration = (TextView) convertView.findViewById(R.id.tv_duration);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        String avatar_url = item.getUser().getAvatar_url();
        if(!TextUtils.isEmpty(avatar_url)){
            Glide.with(mContext).load(avatar_url).transform(new GlideCircleTransform(mContext))
                    .placeholder(R.mipmap.ic_launcher).into(holder.iv_avatar);
        }
        holder.tv_username.setText(item.getUser().getUsername());
        holder.tv_duration.setText(item.getDuration()+"");
        return convertView;
    }

    class ViewHolder {
        ImageView iv_avatar;
        TextView tv_username;
        TextView tv_duration;
    }
}
