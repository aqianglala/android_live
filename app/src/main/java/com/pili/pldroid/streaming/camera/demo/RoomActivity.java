package com.pili.pldroid.streaming.camera.demo;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PlayerCode;
import com.pili.pldroid.player.widget.VideoView;
import com.pili.pldroid.streaming.CameraStreamingManager;
import com.pili.pldroid.streaming.CameraStreamingSetting;
import com.pili.pldroid.streaming.StreamingPreviewCallback;
import com.pili.pldroid.streaming.StreamingProfile;
import com.pili.pldroid.streaming.SurfaceTextureCallback;
import com.pili.pldroid.streaming.camera.demo.global.BaseActivity;
import com.pili.pldroid.streaming.camera.demo.utils.ScreenUtils;
import com.pili.pldroid.streaming.camera.demo.utils.StreamJsonUtils;
import com.pili.pldroid.streaming.camera.demo.widget.AspectLayout;
import com.pili.pldroid.streaming.widget.AspectFrameLayout;

import org.json.JSONObject;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class RoomActivity extends BaseActivity  implements View.OnLayoutChangeListener,
        View.OnClickListener,
        StreamingPreviewCallback,
        SurfaceTextureCallback,

        IjkMediaPlayer.OnInfoListener,
        IjkMediaPlayer.OnErrorListener,
        IjkMediaPlayer.OnPreparedListener,
        IjkMediaPlayer.OnVideoSizeChangedListener,

        CameraStreamingManager.StreamingSessionListener,
        CameraStreamingManager.StreamingStateListener{

    private StreamingProfile mProfile;
    private Context mContext;
    private View mRootView;

    private static final int REQ_DELAY_MILLS = 3000;

    private String mVideoPath = "rtmp://192.168.1.160/live/456";
    //    private String mVideoPath = "rtmp://192.168.1.160/live/123";
    private boolean mIsLiveStream = false;

    private int mReqDelayMills = REQ_DELAY_MILLS;
    private boolean mIsCompleted = false;
    private Runnable mVideoReconnect;

    private VideoView mVideoView;

    private EditText et_play;
    private EditText et_push;
    private Button btn_play;
    private Button btn_push;
    private Button mShutterButton;
    private TextView mSatusTextView;

    private String url;
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private int measuredHeight;

    private static final String TAG = "StreamingBaseActivity";

    protected boolean mShutterButtonPressed = false;

    protected static final int MSG_START_STREAMING = 0;
    protected static final int MSG_STOP_STREAMING = 1;

    protected String mStatusMsgContent;
    protected String mLogContent = "\n";

    protected CameraStreamingManager mCameraStreamingManager;

    protected boolean isEncOrientationPort = true;
    private AspectFrameLayout afl;
    private GLSurfaceView glSurfaceView;

    private boolean isShowingFlContent;

    private AspectLayout mAspectLayout;
    private ViewGroup.LayoutParams mLayoutParams;

    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_STREAMING:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // disable the shutter button before startStreaming
                            setShutterButtonEnabled(false);
                            boolean res = mCameraStreamingManager.startStreaming();
                            mShutterButtonPressed = true;
                            Log.i(TAG, "res:" + res);
                            if (!res) {
                                mShutterButtonPressed = false;
                                setShutterButtonEnabled(true);
                            }
                            setShutterButtonPressed(mShutterButtonPressed);
                        }
                    }).start();
                    break;
                case MSG_STOP_STREAMING:
                    // disable the shutter button before stopStreaming
                    setShutterButtonEnabled(false);
                    boolean res = mCameraStreamingManager.stopStreaming();
                    if (!res) {
                        mShutterButtonPressed = true;
                        setShutterButtonEnabled(true);
                    }
                    setShutterButtonPressed(mShutterButtonPressed);
                    break;
                default:
                    Log.e(TAG, "Invalid message");
            }
        }
    };
    private FrameLayout fl_content;
    private ImageView iv_getMic;
    private ImageView iv_audience;
    private LinearLayout ll_push_container;


    @Override
    protected void initView(Bundle savedInstanceState) {
        // 设置屏幕不锁屏、屏幕方向、设置actionbar覆盖在内容之上
        globalSet();
        setContentView(R.layout.activity_camera);

        et_play = getViewById(R.id.et_play);
        et_push = getViewById(R.id.et_push);

        btn_play = getViewById(R.id.btn_play);
        btn_push = getViewById(R.id.btn_push);

        fl_content = getViewById(R.id.fl_content);
        iv_getMic = getViewById(R.id.iv_getMic);
        iv_audience = getViewById(R.id.iv_audience);

        mRootView = findViewById(R.id.content);
        // player
        mVideoView = getViewById(R.id.video_view);
        // push
        afl =  getViewById(R.id.cameraPreview_afl);
        glSurfaceView =  getViewById(R.id.cameraPreview_surfaceView);

        mShutterButton = getViewById(R.id.toggleRecording_button);
        mSatusTextView =  getViewById(R.id.streamingStatus);

        ll_push_container = getViewById(R.id.ll_push_container);

    }
    // 设置屏幕不锁屏、屏幕方向、设置actionbar覆盖在内容之上
    private void globalSet() {
        // 使屏幕保持开启
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // 设置屏幕方向
        if (Config.SCREEN_ORIENTATION == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            isEncOrientationPort = true;
        } else if (Config.SCREEN_ORIENTATION == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            isEncOrientationPort = false;
        }
        setRequestedOrientation(Config.SCREEN_ORIENTATION);

        // 设置ActionBar在内容之上
//        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().hide();
    }

    // 初始化推流
    private void initPush() {
        afl.setShowMode(AspectFrameLayout.SHOW_MODE.FULL);
//        StreamingProfile.Stream stream = new StreamingProfile.Stream(mJSONObject);
        mProfile = new StreamingProfile();
        mProfile.setVideoQuality(StreamingProfile.VIDEO_QUALITY_MEDIUM1)
                .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2)
//                .setPreferredVideoEncodingSize(720, 405);//发现这个设置了没效果
//                .setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_480)
                .setEncoderRCMode(StreamingProfile.EncoderRCModes.QUALITY_PRIORITY)
//                .setStream(stream)
//                .setEncodingOrientation(StreamingProfile.ENCODING_ORIENTATION.PORT)
                .setSendingBufferProfile(new StreamingProfile.SendingBufferProfile(0.2f, 0.8f, 3.0f, 20 * 1000));

        CameraStreamingSetting setting = new CameraStreamingSetting();
        setting.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT)
                .setContinuousFocusModeEnabled(true)
                .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
                .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_4_3);

        mCameraStreamingManager = new CameraStreamingManager(this, afl, glSurfaceView,
                CameraStreamingManager.EncodingType.SW_VIDEO_WITH_SW_AUDIO_CODEC);  // soft codec
        mCameraStreamingManager.onPrepare(setting, mProfile);
        // update the StreamingProfile
//        mProfile.setStream(new Stream(mJSONObject1));
//        mCameraStreamingManager.setStreamingProfile(mProfile);
        mCameraStreamingManager.setStreamingStateListener(this);
        mCameraStreamingManager.setStreamingPreviewCallback(this);
        mCameraStreamingManager.setSurfaceTextureCallback(this);
        mCameraStreamingManager.setStreamingSessionListener(this);
//        mCameraStreamingManager.setNativeLoggingEnabled(false);
    }

    @Override
    protected void setListener() {
        final View rl_parent = findViewById(R.id.rl_parent);
        rl_parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rl_parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                measuredHeight = ScreenUtils.getScreenHeight(RoomActivity.this) - rl_parent
                        .getMeasuredHeight() - ScreenUtils.getStatusBarHeidht(RoomActivity.this);
                // 设置chatFragment的高度
                setChatFragmentHeight();
                // 设置frameLayout的高度
                setFlHeight();
            }
        });

        ll_push_container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll_push_container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int measuredWidth = ll_push_container.getMeasuredWidth();
                int measuredHeight = ll_push_container.getMeasuredHeight();
                int height = measuredWidth * 4 / 3;

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) afl.getLayoutParams();
                layoutParams.width = measuredWidth;
                layoutParams.height = height;
                afl.setLayoutParams(layoutParams);

                ViewGroup.LayoutParams layoutParams1 = glSurfaceView.getLayoutParams();
                layoutParams1.width = measuredWidth;
                layoutParams1.height = height;
                glSurfaceView.setLayoutParams(layoutParams1);

            }
        });
        btn_play.setOnClickListener(this);
        btn_push.setOnClickListener(this);
        iv_getMic.setOnClickListener(this);
        iv_audience.setOnClickListener(this);

        mRootView.addOnLayoutChangeListener(this);

        mVideoView.setOnErrorListener(this);
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnVideoSizeChangedListener(this);

        mShutterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShutterButtonPressed) {
                    stopStreaming();
                } else {
                    startStreaming();
                }
            }
        });
    }

    private void setFlHeight() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) fl_content.getLayoutParams();
        params.height = measuredHeight;
        fl_content.setLayoutParams(params);
    }

    // 设置chatFragment的高度
    private void setChatFragmentHeight() {
        Fragment chatFragment = getFragmentManager().findFragmentById(R.id.id_fragment);
        // 获取fragment的根视图
        View rootView = chatFragment.getView();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rootView
                .getLayoutParams();
        layoutParams.height = measuredHeight;
        rootView.setLayoutParams(layoutParams);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initPlayer();
        // 初始化推流
        initPush();
    }

    private void showLayout() {
        fl_content.setVisibility(View.VISIBLE);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation
                .RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(this,android.R.interpolator.anticipate_overshoot);
        fl_content.startAnimation(translateAnimation);
        isShowingFlContent = true;
    }

    private void hideLayout() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation
                .RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(this,android.R.interpolator.anticipate_overshoot);
        fl_content.startAnimation(translateAnimation);
        isShowingFlContent = false;
    }

    private void initPlayer() {
        mAspectLayout = (AspectLayout)findViewById(R.id.aspect_layout);
        AVOptions options = new AVOptions();
        options.setInteger(AVOptions.KEY_MEDIACODEC, 0); // 1 -> enable, 0 -> disable

        Log.i(TAG, "mIsLiveStream:" + mIsLiveStream);
        if (mIsLiveStream) {
            options.setInteger(AVOptions.KEY_BUFFER_TIME, 1000); // the unit of buffer time is ms
            options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000); // the unit of
            // timeout is ms
            options.setString(AVOptions.KEY_FFLAGS, AVOptions.VALUE_FFLAGS_NOBUFFER); // "nobuffer"
            options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        }

        mVideoView.setAVOptions(options);
        mVideoView.setVideoPath(mVideoPath);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShutterButtonPressed = false;
        mCameraStreamingManager.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Log.i(TAG, "onPause");
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraStreamingManager.onDestroy();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        Log.i(TAG, "view!!!!:" + v);
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {

    }

    @Override
    public boolean onPreviewFrame(byte[] bytes, int width, int height) {
//        deal with the yuv data.
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < bytes.length; i++) {
//            bytes[i] = 0x00;
//        }
//        Log.i(TAG, "old onPreviewFrame cost :" + (System.currentTimeMillis() - start));
        return true;
    }

    @Override
    public void onSurfaceCreated() {
        Log.i(TAG, "onSurfaceCreated");
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        Log.i(TAG, "onSurfaceChanged width:" + width + ",height:" + height);
    }

    @Override
    public void onSurfaceDestroyed() {
        Log.i(TAG, "onSurfaceDestroyed");
    }

    @Override
    public int onDrawFrame(int texId, int texWidth, int texHeight) {
        // newTexId should not equal with texId. texId is from the SurfaceTexture.
        // Otherwise, there is no filter effect.
        int newTexId = texId;
//        Log.i(TAG, "onDrawFrame texId:" + texId + ",newTexId:" + newTexId + ",texWidth:" + texWidth + ",texHeight:" + texHeight);
        return newTexId;
    }

    /**
     * 自适应码率
     * @param state
     * @param extra
     * @return
     */
    @Override
    public boolean onStateHandled(final int state, Object extra) {
        switch (state) {
            case CameraStreamingManager.STATE.SENDING_BUFFER_HAS_FEW_ITEMS:
                mProfile.improveVideoQuality(1);
                mCameraStreamingManager.notifyProfileChanged(mProfile);
                return true;
            case CameraStreamingManager.STATE.SENDING_BUFFER_HAS_MANY_ITEMS:
                mProfile.reduceVideoQuality(1);
                mCameraStreamingManager.notifyProfileChanged(mProfile);
                return true;
        }
        return false;
    }

    @Override
    public boolean onError(IMediaPlayer mp, int what, int extra) {
        Log.d(TAG, "onError what=" + what + ", extra=" + extra);
        if (what == -10000) {
            if (extra == PlayerCode.EXTRA_CODE_INVALID_URI || extra == PlayerCode.EXTRA_CODE_EOF) {
                return true;
            }
            if (mIsCompleted && extra == PlayerCode.EXTRA_CODE_EMPTY_PLAYLIST) {
                Log.d(TAG, "mVideoView reconnect!!!");
                mVideoView.removeCallbacks(mVideoReconnect);
                mVideoReconnect = new Runnable() {
                    @Override
                    public void run() {
                        mVideoView.setVideoPath(mVideoPath);
                    }
                };
                mVideoView.postDelayed(mVideoReconnect, mReqDelayMills);
                mReqDelayMills += 200;
            } else if (extra == PlayerCode.EXTRA_CODE_404_NOT_FOUND) {
                // NO ts exist
            } else if (extra == PlayerCode.EXTRA_CODE_IO_ERROR) {
                // NO rtmp stream exist
            }
        }
        // return true means you handle the onError, hence System wouldn't handle it again(popup
        // a dialog).
        return true;
    }

    @Override
    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        Log.d(TAG, "onInfo what=" + what + ", extra=" + extra);


        if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_START) {
            Log.i(TAG, "onInfo: (MEDIA_INFO_BUFFERING_START)");
        } else if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
            Log.i(TAG, "onInfo: (MEDIA_INFO_BUFFERING_END)");
        } else if (what == IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START) {
            Toast.makeText(this, "Audio Start", Toast.LENGTH_LONG).show();
            Log.i(TAG, "duration:" + mVideoView.getDuration());
        } else if (what == IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
            Toast.makeText(this, "Video Start", Toast.LENGTH_LONG).show();
            Log.i(TAG, "duration:" + mVideoView.getDuration());
        }
//        rl_parent.bringChildToFront(rl_child);
        return true;
    }

    @Override
    public void onPrepared(IMediaPlayer mp) {
        Log.d(TAG, "onPrepared");
        mReqDelayMills = REQ_DELAY_MILLS;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCameraStreamingManager.onResume();
        mReqDelayMills = REQ_DELAY_MILLS;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_play:
                url = et_play.getText().toString().trim();
                if(TextUtils.isEmpty(url)){
                    Toast.makeText(this,"地址不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                mVideoView.setVideoPath(url);
                mVideoView.start();
                break;
            case R.id.btn_push:
                url = et_push.getText().toString().trim();
                if(TextUtils.isEmpty(url)){
                    Toast.makeText(this,"地址不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                String streamJson = null;
                JSONObject jsonObject = null;
                try {
                    streamJson = StreamJsonUtils.createStreamJson(url);
                    jsonObject = new JSONObject(streamJson);
                }catch (Exception e){
                    Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

                // update the StreamingProfile
                mProfile.setStream(new StreamingProfile.Stream(jsonObject));
                mCameraStreamingManager.setStreamingProfile(mProfile);

                // 开始推流
                startStreaming();
                break;
            case R.id.iv_getMic:
                // 弹出对话框
                if(isShowingFlContent){
                    hideLayout();
                }else{
                    showLayout();
                }
                break;
            case R.id.iv_audience:
                // 弹出对话框
                if(isShowingFlContent){
                    hideLayout();
                }else{
                    showLayout();
                }
                break;
        }
    }

    @Override
    public void onStateChanged(final int state, Object extra) {
        Log.i(TAG, "onStateChanged state:" + state);
        switch (state) {
            case CameraStreamingManager.STATE.PREPARING:
                mStatusMsgContent = getString(R.string.string_state_preparing);
                break;
            case CameraStreamingManager.STATE.READY:
                mStatusMsgContent = getString(R.string.string_state_ready);
                // start streaming when READY
                startStreaming();
                break;
            case CameraStreamingManager.STATE.CONNECTING:
                mStatusMsgContent = getString(R.string.string_state_connecting);
                break;
            case CameraStreamingManager.STATE.STREAMING:
                mStatusMsgContent = getString(R.string.string_state_streaming);
                setShutterButtonEnabled(true);
                setShutterButtonPressed(true);
                break;
            case CameraStreamingManager.STATE.SHUTDOWN:
                mStatusMsgContent = getString(R.string.string_state_ready);
                setShutterButtonEnabled(true);
                setShutterButtonPressed(false);
                break;
            case CameraStreamingManager.STATE.IOERROR:
                mLogContent += "IOERROR\n";
                mStatusMsgContent = getString(R.string.string_state_ready);
                setShutterButtonEnabled(true);
                break;
            case CameraStreamingManager.STATE.NETBLOCKING:
                mLogContent += "NETBLOCKING\n";
                mStatusMsgContent = getString(R.string.string_state_netblocking);
                break;
            case CameraStreamingManager.STATE.CONNECTION_TIMEOUT:
                mLogContent += "CONNECTION_TIMEOUT\n";
                mStatusMsgContent = getString(R.string.string_state_con_timeout);
                break;
            case CameraStreamingManager.STATE.UNKNOWN:
                mStatusMsgContent = getString(R.string.string_state_ready);
                break;
            case CameraStreamingManager.STATE.SENDING_BUFFER_EMPTY:
                break;
            case CameraStreamingManager.STATE.SENDING_BUFFER_FULL:
                break;
            case CameraStreamingManager.STATE.AUDIO_RECORDING_FAIL:
                break;
            case CameraStreamingManager.STATE.OPEN_CAMERA_FAIL:
                Log.e(TAG, "Open Camera Fail. id:" + extra);
                break;
            case CameraStreamingManager.STATE.DISCONNECTED:
                mLogContent += "DISCONNECTED\n";
                break;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSatusTextView.setText(mStatusMsgContent);
            }
        });
    }

    protected void setShutterButtonPressed(final boolean pressed) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShutterButtonPressed = pressed;
                mShutterButton.setPressed(pressed);
            }
        });
    }

    protected void setShutterButtonEnabled(final boolean enable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShutterButton.setFocusable(enable);
                mShutterButton.setClickable(enable);
                mShutterButton.setEnabled(enable);
            }
        });
    }

    protected void startStreaming() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_START_STREAMING), 50);
    }

    protected void stopStreaming() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_STOP_STREAMING), 50);
    }

    @Override
    public boolean onRecordAudioFailedHandled(int err) {
        mCameraStreamingManager.updateEncodingType(CameraStreamingManager.EncodingType.SW_VIDEO_CODEC);
        mCameraStreamingManager.startStreaming();
        return true;
    }

    @Override
    public boolean onRestartStreamingHandled(int err) {
        Log.i(TAG, "onRestartStreamingHandled");
        return mCameraStreamingManager.startStreaming();
    }

    @Override
    public Camera.Size onPreviewSizeSelected(List<Camera.Size> list) {
        if (list != null) {
            for (Camera.Size s : list) {
                Log.i(TAG, "w:" + s.width + ", h:" + s.height);
            }
//            return "your choice";
        }
        return null;
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int width, int height, int sarNum, int sarDen) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        mLayoutParams = mAspectLayout.getLayoutParams();
        mLayoutParams.width = displayMetrics.widthPixels;
        mLayoutParams.height = (int) (displayMetrics.widthPixels * ((float)height/width));
        mAspectLayout.setLayoutParams(mLayoutParams);

        ViewGroup.LayoutParams layoutParams = mVideoView.getLayoutParams();
        layoutParams.width = displayMetrics.widthPixels;
        layoutParams.height = (int) (displayMetrics.widthPixels * ((float)height/width));
        mVideoView.setLayoutParams(layoutParams);
    }
}
