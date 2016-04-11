package com.pili.pldroid.streaming.camera.demo.activity;

import android.databinding.DataBindingUtil;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.pili.pldroid.streaming.CameraStreamingManager;
import com.pili.pldroid.streaming.CameraStreamingSetting;
import com.pili.pldroid.streaming.StreamingProfile;
import com.pili.pldroid.streaming.camera.demo.R;
import com.pili.pldroid.streaming.camera.demo.databinding.ActivityPushBinding;
import com.pili.pldroid.streaming.camera.demo.global.BaseActivity;
import com.pili.pldroid.streaming.camera.demo.utils.StreamJsonUtils;
import com.pili.pldroid.streaming.camera.demo.viewmodels.PushActivityVM;
import com.pili.pldroid.streaming.widget.AspectFrameLayout;

import org.json.JSONObject;

import java.util.List;

public class PushActivity extends BaseActivity implements
        CameraStreamingManager.StreamingSessionListener,
        CameraStreamingManager.StreamingStateListener{

    private ActivityPushBinding mBinding;
    private PushActivityVM mViewmodel;

    private StreamingProfile mProfile;

    protected static final int MSG_START_STREAMING = 0;
    protected static final int MSG_STOP_STREAMING = 1;

    protected String mStatusMsgContent;
    protected TextView mSatusTextView;
    protected String mLogContent = "\n";

    protected CameraStreamingManager mCameraStreamingManager;

    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_STREAMING:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mCameraStreamingManager.startStreaming();
                        }
                    }).start();
                    break;
                case MSG_STOP_STREAMING:
                    mCameraStreamingManager.stopStreaming();
                    break;
                default:
                    Log.e(TAG, "Invalid message");
            }
        }
    };



    @Override
    protected void initView(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout
                .activity_push);
        mViewmodel = new PushActivityVM(this, mBinding);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        /****************************/

        AspectFrameLayout afl = (AspectFrameLayout) findViewById(R.id.cameraPreview_afl);
        GLSurfaceView glSurfaceView = (GLSurfaceView) findViewById(R.id.cameraPreview_surfaceView);
        afl.setShowMode(AspectFrameLayout.SHOW_MODE.FULL);


        mSatusTextView = (TextView) findViewById(R.id.streamingStatus);

//        StreamingProfile.Stream stream = new StreamingProfile.Stream(mJSONObject);
        mProfile = new StreamingProfile();
        mProfile.setVideoQuality(StreamingProfile.VIDEO_QUALITY_MEDIUM1)
                .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2)
//                .setPreferredVideoEncodingSize(720, 640)
//                .setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_720)
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
        mCameraStreamingManager.setStreamingSessionListener(this);
//        mCameraStreamingManager.setNativeLoggingEnabled(false);
    }

    @Override
    protected void setListener() {
        mBinding.btnCreate.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create:
                mViewmodel.createRoom();
                break;
        }
    }

    /**
     * 状态改变的回调
     * @param state
     * @param extra
     */
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
                break;
            case CameraStreamingManager.STATE.SHUTDOWN:
                mStatusMsgContent = getString(R.string.string_state_ready);
                break;
            case CameraStreamingManager.STATE.IOERROR:
                mLogContent += "IOERROR\n";
                mStatusMsgContent = getString(R.string.string_state_ready);
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

    protected void startStreaming() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_START_STREAMING), 50);
    }

    protected void stopStreaming() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_STOP_STREAMING), 50);
    }

    /**
     * 在 Audio 数据读取失败后，会回调该方法，如前面的代码，您可以继续继续纯视频推流
     * @param err
     * @return
     */
    @Override
    public boolean onRecordAudioFailedHandled(int err) {
        mCameraStreamingManager.updateEncodingType(CameraStreamingManager.EncodingType.SW_VIDEO_CODEC);
        mCameraStreamingManager.startStreaming();
        return true;
    }

    /**
     * 在网络链接失败之后，SDK 会回调 STATE.DISCONNECTED 消息，并在合适的时刻回调 onRestartStreamingHandled 方法，您可以在此方法中安全地实现重连策略。若网络不可达，会回调 STATE.IOERROR。
     * @param err
     * @return
     */
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
    protected void onResume() {
        super.onResume();
        mCameraStreamingManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

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

    public void setStream(String url) {
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
    }
}
