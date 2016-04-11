package com.pili.pldroid.streaming.camera.demo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.pili.pldroid.streaming.camera.demo.R;
import com.pili.pldroid.streaming.camera.demo.bean.MessageBean;
import com.pili.pldroid.streaming.camera.demo.databinding.ActivitySettingBinding;
import com.pili.pldroid.streaming.camera.demo.global.BaseActivity;
import com.pili.pldroid.streaming.camera.demo.utils.SPUtils;

import org.simple.eventbus.EventBus;

public class SettingActivity extends BaseActivity {


    private ActivitySettingBinding mBinding;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout
                .activity_setting);
    }

    @Override
    protected void setListener() {
        mBinding.btnExit.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit:
                SPUtils.clear(this);
                // post a event with tag, the tag is like broadcast's action
                EventBus.getDefault().post(new MessageBean("login success"), "updateUI");
                finish();
                break;
        }
    }
}
