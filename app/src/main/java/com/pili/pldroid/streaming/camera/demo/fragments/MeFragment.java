package com.pili.pldroid.streaming.camera.demo.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.pili.pldroid.streaming.camera.demo.R;
import com.pili.pldroid.streaming.camera.demo.activity.LoginActivity;
import com.pili.pldroid.streaming.camera.demo.activity.SettingActivity;
import com.pili.pldroid.streaming.camera.demo.activity.ShowUserInfoActivity;
import com.pili.pldroid.streaming.camera.demo.bean.MessageBean;
import com.pili.pldroid.streaming.camera.demo.databinding.FragmentMeBinding;
import com.pili.pldroid.streaming.camera.demo.global.BaseFragment;
import com.pili.pldroid.streaming.camera.demo.interfaces.Urls;
import com.pili.pldroid.streaming.camera.demo.utils.SPUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/**
 * Created by admin on 2016/3/23.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener{

    private FragmentMeBinding mBinding;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_me);
        // register the receiver object
        EventBus.getDefault().register(this);

        mBinding = DataBindingUtil.bind(mContentView);
    }

    @Override
    protected void setListener() {
        mBinding.llSetting.setOnClickListener(this);
        mBinding.llUserMessage.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        updateUI();
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
            case R.id.ll_user_message:
                if(mBinding.tvLogin.getVisibility()==View.VISIBLE){
                    mActivity.startActivityForResult(new Intent(mActivity,LoginActivity.class), Urls
                            .loginRequestCode);
                }else{
                    goActivity(ShowUserInfoActivity.class);
                }
                break;
        }
    }

    public void updateUI(){
        // 检查sp
        int id = (int) SPUtils.get(mActivity, Urls.ID, 0);
        String userName = (String) SPUtils.get(mActivity, Urls.USERNAME, "");
        if(!TextUtils.isEmpty(userName)){
            mBinding.llLoginVisible.setVisibility(View.GONE);
            mBinding.llUserInfo.setVisibility(View.VISIBLE);
            mBinding.tvId.setText(id+"");
            mBinding.tvName.setText(userName);
            mBinding.tvLogin.setVisibility(View.GONE);
        }else{
            mBinding.llLoginVisible.setVisibility(View.VISIBLE);
            mBinding.llUserInfo.setVisibility(View.GONE);
            mBinding.tvLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        // Don’t forget to unregister !!
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    // When there is a “my_tag”, only events designated with “my_tag” can
    // trigger the function and execute on UI thread when a user posts an event.
    @Subscriber(tag = "updateUI")
    private void updateUserWithTag(MessageBean message) {
        Log.e("", "### update user with my_tag, name = " + message.message);
        updateUI();
    }


}
