package com.pili.pldroid.streaming.camera.demo.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.pili.pldroid.streaming.camera.demo.R;
import com.pili.pldroid.streaming.camera.demo.databinding.ActivityLoginBinding;
import com.pili.pldroid.streaming.camera.demo.global.BaseActivity;
import com.pili.pldroid.streaming.camera.demo.interfaces.Urls;
import com.pili.pldroid.streaming.camera.demo.viewmodels.LoginActivityViewmodel;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    private LoginActivityViewmodel viewmodel;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ActivityLoginBinding binding=DataBindingUtil.setContentView(this, R.layout.activity_login);

        viewmodel = new LoginActivityViewmodel(this, binding);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public void register(View view){
        startActivityForResult(new Intent(this,RegisterActivity.class), Urls.registerRequestCode);
    }

    public void login(View view){
        viewmodel.login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==Urls.registerRequestCode){
            if(resultCode ==RESULT_OK){
                showToast(data.getStringExtra("info"));
            }
        }
    }
}

