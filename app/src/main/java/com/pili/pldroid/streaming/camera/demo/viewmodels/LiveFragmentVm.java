package com.pili.pldroid.streaming.camera.demo.viewmodels;

import com.google.gson.Gson;
import com.pili.pldroid.streaming.camera.demo.bean.LiveBean;
import com.pili.pldroid.streaming.camera.demo.databinding.FragmentLiveBinding;
import com.pili.pldroid.streaming.camera.demo.fragments.LiveFragment;
import com.pili.pldroid.streaming.camera.demo.interfaces.Urls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2016/3/23.
 */
public class LiveFragmentVm {

    private FragmentLiveBinding mBinding;
    private LiveFragment mFragment;

    public boolean isLoading;

    public LiveFragmentVm(LiveFragment fragment, FragmentLiveBinding binding) {
        mFragment = fragment;
        this.mBinding = binding;
    }

    public void loadData(){
        isLoading = true;
        OkHttpUtils
                .get()
                .url(Urls.rooms)
                .tag(mFragment.getActivity())
                .build()
                .execute(new MyCallBack());
    }
    
    private class MyCallBack extends Callback<LiveBean>{

        @Override
        public LiveBean parseNetworkResponse(Response response) throws Exception {
            String data = response.body().string();
            return new Gson().fromJson(data,LiveBean.class);
        }

        @Override
        public void onError(Call call, Exception e) {
            isLoading = false;
            mFragment.showToast("获取数据失败");
            mFragment.stopWait();
        }

        @Override
        public void onResponse(LiveBean response) {
            isLoading = false;
            mFragment.setAdapter(response.getData());
        }
    }
}
