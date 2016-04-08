package com.pili.pldroid.streaming.camera.demo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.pili.pldroid.streaming.camera.demo.R;
import com.pili.pldroid.streaming.camera.demo.databinding.ActivityRegisterBinding;
import com.pili.pldroid.streaming.camera.demo.global.BaseActivity;
import com.pili.pldroid.streaming.camera.demo.viewmodels.RegisterActivityViewmodel;

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding mBinding;
    private RegisterActivityViewmodel mViewmode;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout
                .activity_register);
        mViewmode = new RegisterActivityViewmodel(this, mBinding);

    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public void register(View v){
        mViewmode.register();
    }
}
