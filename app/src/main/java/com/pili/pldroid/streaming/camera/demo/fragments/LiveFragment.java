package com.pili.pldroid.streaming.camera.demo.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.pili.pldroid.streaming.camera.demo.R;
import com.pili.pldroid.streaming.camera.demo.RoomActivity;
import com.pili.pldroid.streaming.camera.demo.activity.SearchActivity;
import com.pili.pldroid.streaming.camera.demo.adapter.LiveAdapter;
import com.pili.pldroid.streaming.camera.demo.bean.LiveBean;
import com.pili.pldroid.streaming.camera.demo.databinding.FragmentLiveBinding;
import com.pili.pldroid.streaming.camera.demo.global.BaseFragment;
import com.pili.pldroid.streaming.camera.demo.interfaces.Urls;
import com.pili.pldroid.streaming.camera.demo.viewmodels.LiveFragmentVm;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by admin on 2016/3/23.
 */
public class LiveFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate,
        BGAOnRVItemClickListener{

    private FragmentLiveBinding mBinding;
    private LiveFragmentVm liveFragmentVm;
    private LiveAdapter mAdapter;

    private boolean isRefresh;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_live);

        mBinding = DataBindingUtil.bind(mContentView);
        liveFragmentVm = new LiveFragmentVm(this, mBinding);

        initToolBar();

        initRefreshViewHolder();

        mBinding.idRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,2,
                LinearLayoutManager.VERTICAL,false));
        mAdapter = new LiveAdapter(this, mBinding.idRecyclerView, R.layout
                .item_grid);
        mBinding.idRecyclerView.setAdapter(mAdapter);

    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) mContentView.findViewById(R.id.toolBar);
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initRefreshViewHolder() {
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder
                (mActivity, false);
        mBinding.rlRefresh.setRefreshViewHolder(bgaNormalRefreshViewHolder);
    }

    @Override
    protected void setListener() {
        mBinding.ivSearch.setOnClickListener(this);
        mBinding.rlRefresh.setDelegate(this);
        mAdapter.setOnRVItemClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // 获取房间数据
        mBinding.rlRefresh.beginRefreshing();
    }

    @Override
    protected void onUserVisible() {

    }

    public void setAdapter(List<LiveBean.DataEntity> data){
        if(isRefresh){
            mAdapter.clear();
        }else{

        }
        mAdapter.addMoreDatas(data);
        stopWait();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search:
                goActivity(SearchActivity.class);
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if(!liveFragmentVm.isLoading){
            isRefresh=true;
            //加载数据
//            liveFragmentVm.setPage(1);
            liveFragmentVm.loadData();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    public void stopWait(){
        mBinding.rlRefresh.endRefreshing();
        mBinding.rlRefresh.endLoadingMore();
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
        Intent intent = new Intent(mActivity, RoomActivity.class);
        intent.putExtra(Urls.ROOM_INFO,mAdapter.getItem(i));
        mActivity.startActivity(intent);
    }
}
