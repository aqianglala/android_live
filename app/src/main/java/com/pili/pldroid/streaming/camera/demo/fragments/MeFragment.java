package com.pili.pldroid.streaming.camera.demo.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.pili.pldroid.streaming.camera.demo.R;
import com.pili.pldroid.streaming.camera.demo.activity.SettingActivity;
import com.pili.pldroid.streaming.camera.demo.databinding.FragmentMeBinding;
import com.pili.pldroid.streaming.camera.demo.global.BaseFragment;

/**
 * Created by admin on 2016/3/23.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener{

    private FragmentMeBinding mBinding;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_me);
        mBinding = DataBindingUtil.bind(mContentView);
    }

    @Override
    protected void setListener() {
        mBinding.llSetting.setOnClickListener(this);
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
            case R.id.ll_setting:
                goActivity(SettingActivity.class);
                break;
        }
    }

    private void goActivity(Class clazz){
        mActivity.startActivity(new Intent(mActivity,clazz));
    }
}
