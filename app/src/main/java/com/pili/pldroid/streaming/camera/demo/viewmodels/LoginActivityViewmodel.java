package com.pili.pldroid.streaming.camera.demo.viewmodels;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.pili.pldroid.streaming.camera.demo.activity.LoginActivity;
import com.pili.pldroid.streaming.camera.demo.bean.LoginBean;
import com.pili.pldroid.streaming.camera.demo.bean.LoginResponse;
import com.pili.pldroid.streaming.camera.demo.bean.MessageBean;
import com.pili.pldroid.streaming.camera.demo.databinding.ActivityLoginBinding;
import com.pili.pldroid.streaming.camera.demo.interfaces.Urls;
import com.pili.pldroid.streaming.camera.demo.utils.AESUtils;
import com.pili.pldroid.streaming.camera.demo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.simple.eventbus.EventBus;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by admin on 2016/2/2.
 */
public class LoginActivityViewmodel {
    private LoginActivity mActivity;
    private ActivityLoginBinding mBinding;

    public LoginActivityViewmodel(LoginActivity loginActivity , ActivityLoginBinding binding){
        mActivity=loginActivity;
        mBinding=binding;
    }

    public void login() {
        String email = mBinding.etEmail.getText().toString().trim();
        String password = mBinding.etPassword.getText().toString().trim();
        if(validateUser(email,password)){//前端验证
            mActivity.showLoadingDialog();
            LoginBean loginBean = new LoginBean();
            loginBean.setSession(new LoginBean.SessionEntity(email,password));
            OkHttpUtils
                    .postString()
                    .url(Urls.login)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(new Gson().toJson(loginBean))
                    .tag(mActivity)
                    .build()
                    .execute(new MyCallBack());
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

    private class MyCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e) {
            mActivity.dismissLoadingDialog();
            mActivity.showToast(e.getMessage());
        }

        @Override
        public void onResponse(String response) {
            mActivity.dismissLoadingDialog();
            LoginResponse data = new Gson().fromJson(response.toString(), LoginResponse
                    .class);
            try {
                SPUtils.put(mActivity,Urls.ID,data.getData().getId());
                SPUtils.put(mActivity,Urls.USERNAME,data.getData().getUsername());
                SPUtils.put(mActivity,Urls.EMAIL,data.getData().getEmail());
                SPUtils.put(mActivity,Urls.TOKEN, AESUtils.encrypt(Urls.SEED,data.getData().getToken
                        ()));
            }catch (Exception e){
                e.printStackTrace();
            }
            // post a event with tag, the tag is like broadcast's action
            EventBus.getDefault().post(new MessageBean("login success"), "updateUI");
            mActivity.finish();
        }
    }

}
