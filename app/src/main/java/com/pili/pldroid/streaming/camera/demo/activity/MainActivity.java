package com.pili.pldroid.streaming.camera.demo.activity;

import com.pili.pldroid.streaming.camera.demo.R;

import org.kymjs.chat.ChatActivity;
import org.kymjs.kjframe.KJActivity;

public class MainActivity extends KJActivity {

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main);
        showActivity(aty, ChatActivity.class);
    }
}
