package com.pili.pldroid.streaming.camera.demo.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.pili.pldroid.streaming.camera.demo.R;
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

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onUserVisible() {

    }
}
