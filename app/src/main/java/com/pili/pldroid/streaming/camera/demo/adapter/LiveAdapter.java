package com.pili.pldroid.streaming.camera.demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pili.pldroid.streaming.camera.demo.R;
import com.pili.pldroid.streaming.camera.demo.bean.LiveBean;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by admin on 2016/4/9.
 */
public class LiveAdapter extends BGARecyclerViewAdapter<LiveBean.DataEntity>{

    private Fragment mFragment;

    public LiveAdapter(Fragment fragment, RecyclerView recyclerView, int itemLayoutId) {
        super(recyclerView, itemLayoutId);
        mFragment = fragment;
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, LiveBean.DataEntity
            entity) {
        ImageView iv_room = bgaViewHolderHelper.getView(R.id.iv_room);
        String pic_url = entity.getImage();
        if(!TextUtils.isEmpty(pic_url)){
            Glide.with(mFragment).load(pic_url).placeholder(R.mipmap.ic_launcher).into(iv_room);
        }
        bgaViewHolderHelper.setText(R.id.tv_room_title,entity.getTitle());
    }
}
