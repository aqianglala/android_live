package com.pili.pldroid.streaming.camera.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;

import com.pili.pldroid.streaming.camera.demo.R;
import com.pili.pldroid.streaming.camera.demo.global.BaseActivity;

public class SearchActivity extends BaseActivity {

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.id_search).getActionView();
        // 设置searchView总是展开，当然，menu文件中的showAsAction不能设置collapseActionView才有效
        searchView.onActionViewExpanded();
        // 设置提示语
        searchView.setQueryHint("请输入...");
        // 设置确认监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showToast(query);
                // 消费了事件
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        EditText editText = (EditText) searchView.findViewById(R.id.search_src_text);
        // 设置提示语颜色
        editText.setHintTextColor(Color.WHITE);
        // 设置输入文字的颜色
        editText.setTextColor(Color.WHITE);

        return true;
    }
}
