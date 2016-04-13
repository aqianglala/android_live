package com.pili.pldroid.streaming.camera.demo.viewmodels;

import android.util.Log;

import com.google.gson.Gson;
import com.pili.pldroid.streaming.camera.demo.RoomActivity;
import com.pili.pldroid.streaming.camera.demo.bean.QueueBean;
import com.pili.pldroid.streaming.camera.demo.interfaces.Urls;
import com.pili.pldroid.streaming.camera.demo.utils.L;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2016/4/12.
 */
public class QueueVM {

    private RoomActivity mActivity;

    public QueueVM(RoomActivity activity) {
        mActivity = activity;
    }

    public void getCallQueue(String roomId){

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
            return new Gson().fromJson(response.body().string(),QueueBean.class);
        }

        @Override
        public void onError(Call call, Exception e) {
            L.e(e.getMessage());
        }

        @Override
        public void onResponse(QueueBean response) {
            Log.e("queue",response.getData().size()+"");

        }
    }
}
