package com.pili.pldroid.streaming.camera.demo.viewmodels;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.pili.pldroid.streaming.camera.demo.activity.PushActivity;
import com.pili.pldroid.streaming.camera.demo.bean.CreateRoomBean;
import com.pili.pldroid.streaming.camera.demo.bean.RoomParamsBean;
import com.pili.pldroid.streaming.camera.demo.databinding.ActivityPushBinding;
import com.pili.pldroid.streaming.camera.demo.interfaces.Urls;
import com.pili.pldroid.streaming.camera.demo.utils.AESUtils;
import com.pili.pldroid.streaming.camera.demo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by admin on 2016/4/11.
 */
public class PushActivityVM {

    private PushActivity mActivity;
    private ActivityPushBinding mBinding;

    public PushActivityVM(PushActivity activity, ActivityPushBinding binding) {
        mActivity = activity;
        mBinding = binding;
    }

    public void createRoom(){
        String title = mBinding.etTitle.getText().toString().trim();
        if(TextUtils.isEmpty(title)){
            mActivity.showToast("房间名不能为空");
           return;
        }
        try {
            String token = AESUtils.decrypt(Urls.SEED,(String) SPUtils.get(mActivity, Urls.TOKEN, ""));
            if(TextUtils.isEmpty(token)){
                mActivity.showToast("请先登录");
            }else{
                RoomParamsBean roomParamsBean = new RoomParamsBean();
                roomParamsBean.setRoom(new RoomParamsBean.RoomEntity(title));
                OkHttpUtils
                        .postString()
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .addHeader("Authorization","Token token="+token)
                        .url(Urls.rooms)
                        .content(new Gson().toJson(roomParamsBean))
                        .build()
                        .execute(new MyStringCallBack());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyStringCallBack extends StringCallback{

        @Override
        public void onError(Call call, Exception e) {
            mActivity.showToast(e.getMessage());
        }

        @Override
        public void onResponse(String response) {
            mActivity.showToast(response);
            // 设置推流地址
            CreateRoomBean createRoomBean = new Gson().fromJson(response, CreateRoomBean.class);
            String rtmp_url = createRoomBean.getData().getRtmp_url();
            String replace = rtmp_url.replace("ilikemac.local", Urls.IP);
            mActivity.setStream(replace);
        }
    }
}
