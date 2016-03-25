package com.pili.pldroid.streaming.camera.demo.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pili.pldroid.streaming.camera.demo.R;
import com.pili.pldroid.streaming.camera.demo.activity.SearchActivity;
import com.pili.pldroid.streaming.camera.demo.databinding.FragmentLiveBinding;
import com.pili.pldroid.streaming.camera.demo.global.BaseFragment;

/**
 * Created by admin on 2016/3/23.
 */
public class LiveFragment extends BaseFragment{

    private FragmentLiveBinding mBinding;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_live);
        mBinding = DataBindingUtil.bind(mContentView);

        Toolbar toolbar = (Toolbar) mContentView.findViewById(R.id.toolBar);
        mActivity.setSupportActionBar(toolbar);

        mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void setListener() {
        mBinding.ivSearch.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search:
                goActivity(SearchActivity.class);
                break;
        }
    }
}
