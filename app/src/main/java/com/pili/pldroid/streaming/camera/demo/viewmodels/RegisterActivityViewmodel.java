package com.pili.pldroid.streaming.camera.demo.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.pili.pldroid.streaming.camera.demo.activity.RegisterActivity;
import com.pili.pldroid.streaming.camera.demo.bean.RegisterBean;
import com.pili.pldroid.streaming.camera.demo.databinding.ActivityRegisterBinding;
import com.pili.pldroid.streaming.camera.demo.interfaces.Urls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by admin on 2016/2/2.
 */
public class RegisterActivityViewmodel {
    private RegisterActivity mActivity;
    private ActivityRegisterBinding mBinding;
    private String regex = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
//    private String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    public RegisterActivityViewmodel(RegisterActivity registerActivity, ActivityRegisterBinding
            binding) {
        mActivity = registerActivity;
        mBinding = binding;
    }

    public void validateInfo(){
        String email = mBinding.etEmail.getText().toString().trim();
        String nickName = mBinding.etNickname.getText().toString().trim();
        String passWord = mBinding.etPassword.getText().toString().trim();
        String confirm = mBinding.etConfirm.getText().toString().trim();

    }

    public void register() {
        String email = mBinding.etEmail.getText().toString().trim();
        String nickName = mBinding.etNickname.getText().toString().trim();
        String passWord = mBinding.etPassword.getText().toString().trim();
        String confirm = mBinding.etConfirm.getText().toString().trim();

        if (validateUser(email, nickName, passWord, confirm)) {//前端验证
            mActivity.showLoadingDialog();
            RegisterBean registerBean = new RegisterBean();
            registerBean.setUser(new RegisterBean.UserEntity(nickName, email,
                    passWord, confirm));
            OkHttpUtils
                    .postString()
                    .url(Urls.register)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(new Gson().toJson(registerBean))
                    .tag(mActivity)
                    .build()
                    .execute(new MyCallBack());

        }

    }


    private boolean validateUser(String email, String nickName, String password, String confirm) {
        boolean isAccessEmail = false;
        boolean isAccessName = false;
        boolean isAccessPsw = false;
        boolean isAccessConrim = false;
        // 邮箱
        if (TextUtils.isEmpty(email)) {
            mBinding.tilEmail.setErrorEnabled(true);
            mBinding.tilEmail.setError("email不能为空");
            isAccessEmail = false;
        } else {
            if (email.matches(regex)){
                mBinding.tilEmail.setErrorEnabled(false);
                isAccessEmail = true;
            }else{
                mBinding.tilEmail.setErrorEnabled(true);
                mBinding.tilEmail.setError("邮箱格式不正确");
                isAccessEmail = false;
            }
        }
        // 昵称
        if (TextUtils.isEmpty(nickName)) {
            mBinding.tilNickname.setErrorEnabled(true);
            mBinding.tilNickname.setError("nickName不能为空");
            isAccessName = false;
        } else {
            mBinding.tilNickname.setErrorEnabled(false);
            isAccessName = true;
        }
        // 密码
        if (TextUtils.isEmpty(password)) {
            mBinding.tilPassword.setErrorEnabled(true);
            mBinding.tilPassword.setError("密码不能为空");
            isAccessPsw = false;
        } else {
            mBinding.tilPassword.setErrorEnabled(false);
            isAccessPsw = true;
        }
        // 确认密码
        if (TextUtils.isEmpty(confirm)) {
            mBinding.tilConfirm.setErrorEnabled(true);
            mBinding.tilConfirm.setError("请确认密码");
            isAccessConrim = false;
        } else {
            mBinding.tilConfirm.setErrorEnabled(false);
            isAccessConrim = true;
        }
        //  两次密码
        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirm)){
            if(password.equals(confirm)){
                mBinding.tilConfirm.setErrorEnabled(false);
                isAccessConrim = true;
            }else{
                mBinding.tilConfirm.setErrorEnabled(true);
                mBinding.tilConfirm.setError("两次密码不一致");
                isAccessConrim = false;
            }
        }

        return isAccessEmail & isAccessPsw & isAccessName & isAccessConrim;
    }

    private class MyCallBack extends StringCallback{

        @Override
        public void onError(Call call, Exception e) {
            mActivity.dismissLoadingDialog();
            mActivity.showToast(e.getMessage());
        }

        @Override
        public void onResponse(String response) {
            mActivity.dismissLoadingDialog();
            Intent intent = new Intent();
            intent.putExtra("info","注册成功");
            mActivity.setResult(Activity.RESULT_OK,intent);
            mActivity.finish();
        }
    }
}
