package com.pili.pldroid.streaming.camera.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.Window;

import com.pili.pldroid.streaming.camera.demo.R;


public class SplashActivity extends Activity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        delayEnterHome();
    }

    /** 延迟3秒后进入首页 */
    private void delayEnterHome() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHome();
            }
        }, 2000);
    }

    /** 进入首页 */
    protected void enterHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacksAndMessages(null);
                enterHome();
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

}