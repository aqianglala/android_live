package com.pili.pldroid.streaming.camera.demo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.pili.pldroid.streaming.camera.demo.R;
import com.pili.pldroid.streaming.camera.demo.fragments.LiveFragment;
import com.pili.pldroid.streaming.camera.demo.fragments.MeFragment;
import com.pili.pldroid.streaming.camera.demo.global.BaseActivity;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{


    private RadioGroup mRadioGroup;
    private LiveFragment mLiveFragment;
    private MeFragment mMeFragment;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        mRadioGroup = getViewById(R.id.id_radioGroup);
    }

    @Override
    protected void setListener() {
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mRadioGroup.check(R.id.rb_live);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        hideFragments(ft);//先隐藏掉所有的fragment
        switch (checkedId){
            case R.id.rb_live:
                if(mLiveFragment == null){
                    mLiveFragment = new LiveFragment();
                    ft.add(R.id.fl_content,mLiveFragment);
                }else{
                    ft.show(mLiveFragment);
                }
                break;
            case R.id.rb_me:
                if(mMeFragment == null){
                    mMeFragment = new MeFragment();
                    ft.add(R.id.fl_content,mMeFragment);
                }else{
                    ft.show(mMeFragment);
                }
                break;
        }
        ft.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mLiveFragment != null) {
            transaction.hide(mLiveFragment);
        }
        if (mMeFragment != null) {
            transaction.hide(mMeFragment);
        }
    }
}
