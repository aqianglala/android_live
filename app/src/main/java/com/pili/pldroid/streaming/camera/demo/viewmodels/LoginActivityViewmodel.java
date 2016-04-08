package com.pili.pldroid.streaming.camera.demo.viewmodels;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.pili.pldroid.streaming.camera.demo.activity.LoginActivity;
import com.pili.pldroid.streaming.camera.demo.bean.LoginBean;
import com.pili.pldroid.streaming.camera.demo.databinding.ActivityLoginBinding;
import com.pili.pldroid.streaming.camera.demo.interfaces.Urls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2016/2/2.
 */
public class LoginActivityViewmodel {
    private LoginActivity mLoginActivity;
    private ActivityLoginBinding mBinding;

    public LoginActivityViewmodel(LoginActivity loginActivity , ActivityLoginBinding binding){
        mLoginActivity=loginActivity;
        mBinding=binding;
    }

    public void login() {
        String email = mBinding.etEmail.getText().toString().trim();
        String password = mBinding.etPassword.getText().toString().trim();
        if(validateUser(email,password)){//前端验证
            mLoginActivity.showLoadingDialog();
            LoginBean loginBean = new LoginBean();
            loginBean.setSession(new LoginBean.SessionEntity(email,password));
            OkHttpUtils
                    .postString()
                    .url(Urls.login)
                    .content(new Gson().toJson(loginBean))
                    .tag(mLoginActivity)
                    .build()
                    .execute(new MyCallback());
        }
    }

    private boolean validateUser(String email, String password) {
        boolean isAccessEmail=false;
        boolean isAccessPass=false;
        if(TextUtils.isEmpty(email)){
            mBinding.tilEmail.setErrorEnabled(true);
            mBinding.tilEmail.setError("邮箱不能为空");
            isAccessEmail = false;
        }else{
            mBinding.tilEmail.setErrorEnabled(false);
            isAccessEmail=true;
        }
        if(TextUtils.isEmpty(password)){
            mBinding.tilPassword.setErrorEnabled(true);
            mBinding.tilPassword.setError("密码不能为空");
            isAccessPass = false;
        }else{
            mBinding.tilPassword.setErrorEnabled(false);
            isAccessPass = true;
        }
        return isAccessEmail & isAccessPass;
    }

    private class MyCallback extends Callback{

        @Override
        public Object parseNetworkResponse(Response response) throws Exception {
            Log.e("response",response.body().string());
            return null;
        }

        @Override
        public void onError(Call call, Exception e) {

        }

        @Override
        public void onResponse(Object response) {

        }
    }

}
