package com.pili.pldroid.streaming.camera.demo.viewmodels;

import android.util.Log;

import com.google.gson.Gson;
import com.pili.pldroid.streaming.camera.demo.RoomActivity;
import com.pili.pldroid.streaming.camera.demo.bean.CallBean;
import com.pili.pldroid.streaming.camera.demo.bean.QueueBean;
import com.pili.pldroid.streaming.camera.demo.interfaces.Urls;
import com.pili.pldroid.streaming.camera.demo.utils.AESUtils;
import com.pili.pldroid.streaming.camera.demo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2016/4/12.
 */
public class QueueVM {

    private RoomActivity mActivity;
    private String roomId;

    public QueueVM(RoomActivity activity) {
        mActivity = activity;
    }

    public void getCallQueue(String roomId){
        this.roomId = roomId;
        OkHttpUtils
                .get()
                .url(Urls.queued.replace("room_id",roomId))
                .tag(mActivity)
                .build()
                .execute(new MyCallBack());
    }

    private class MyCallBack extends Callback<QueueBean>{

        @Override
        public QueueBean parseNetworkResponse(Response response) throws Exception {
            String string = response.body().string();
            Log.e("queue",string);
            return new Gson().fromJson(string,QueueBean.class);
        }

        @Override
        public void onError(Call call, Exception e) {
            Log.e("queue",e.getMessage());
            mActivity.showToast(e.getMessage());
        }

        @Override
        public void onResponse(QueueBean response) {
            Log.e("queue",response.getData().size()+"");
            // 更新数据
            mActivity.setAdapter(response.getData());
        }
    }
    // 抢麦
    public void call(){
        // 获取token
        String token = null;
        try {
            token = AESUtils.decrypt(Urls.SEED,(String) SPUtils.get(mActivity, Urls.TOKEN, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .post()
                .url(Urls.queued.replace("room_id",roomId))
                .addHeader("Authorization","Token token="+ token)
                .tag(mActivity)
                .build()
                .execute(new Callback<CallBean>() {
                    @Override
                    public CallBean parseNetworkResponse(Response response) throws Exception {
                        return new Gson().fromJson(response.body().string(),CallBean.class);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        mActivity.showToast("抢麦失败"+e.getMessage());
                    }

                    @Override
                    public void onResponse(CallBean response) {
                        // 更新队列
                        getCallQueue(roomId);
                    }
                });
    }

    public void quitQueue(){
        // 获取token
        String token = null;
        try {
            token = AESUtils.decrypt(Urls.SEED,(String) SPUtils.get(mActivity, Urls.TOKEN, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .delete()
                .url(Urls.queued.replace("room_id",roomId))
                .addHeader("Authorization","Token token="+ token)
                .tag(mActivity)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws Exception {
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {
                        // 退出成功更新列表
                        Log.e("quit","退出成功");
                        getCallQueue(roomId);
                    }
                });
    }

}
