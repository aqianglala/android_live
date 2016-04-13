package com.pili.pldroid.streaming.camera.demo.viewmodels;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hosopy.actioncable.ActionCableException;
import com.hosopy.actioncable.Cable;
import com.hosopy.actioncable.Channel;
import com.hosopy.actioncable.Consumer;
import com.hosopy.actioncable.Subscription;
import com.pili.pldroid.streaming.camera.demo.fragments.ChatFragment;
import com.pili.pldroid.streaming.camera.demo.interfaces.Urls;
import com.pili.pldroid.streaming.camera.demo.utils.AESUtils;
import com.pili.pldroid.streaming.camera.demo.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.chat.bean.Message;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/4/12.
 */
public class ChatFragmentVM {

    private ChatFragment mFragment;
    private Subscription subscription;
    private String tag = "socket";

    private boolean isConnected;
    private Consumer consumer;

    public ChatFragmentVM(ChatFragment fragment) {
        mFragment = fragment;
    }

    public void connect(int roomId) {
        String token = null;
        try {
            token = AESUtils.decrypt(Urls.SEED, (String) SPUtils.get(mFragment.getActivity(), Urls.TOKEN,
                        ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(TextUtils.isEmpty(token)){
            Toast.makeText(mFragment.getActivity(),"请先登录!",Toast.LENGTH_SHORT).show();
            return;
        }
// 1. Setup
        URI uri = null;
        try {
            uri = new URI(Urls.SOCKET);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Consumer.Options options = new Consumer.Options();
        Map<String, String> headers = new HashMap();
        headers.put("Origin", "http://ilikemac.local:3000");
        headers.put("Authorization", "Token token="+ token);
        options.headers = headers;

        consumer = Cable.createConsumer(uri, options);

// 2. Create subscription
        Channel roomChannel = new Channel("RoomChannel");
        roomChannel.addParam("room_id", roomId);
        subscription = consumer.getSubscriptions().create(roomChannel);

        subscription.onConnected(new Subscription.ConnectedCallback() {
            @Override
            public void call() {
                // Called when the subscription has been successfully completed
                isConnected = true;
                Log.e(tag,"连接成功");
            }

        }).onRejected(new Subscription.RejectedCallback() {
            @Override
            public void call() {
                // Called when the subscription is rejected by the server
                isConnected = false;
                Log.e(tag,"onRejected");
            }
        }).onReceived(new Subscription.ReceivedCallback() {
            @Override
            public void call(JsonElement data) {
                // Called when the subscription receives data from the server
                Log.e(tag,"接收到服务器返回的信息"+data.toString());
                mFragment.refreshAdapter(parseJson(data));
            }
        }).onDisconnected(new Subscription.DisconnectedCallback() {
            @Override
            public void call() {
                // Called when the subscription has been closed
                isConnected = false;
                Log.e(tag,"onDisconnected");
            }
        }).onFailed(new Subscription.FailedCallback() {
            @Override
            public void call(ActionCableException e) {
                // Called when the subscription encounters any error
                isConnected = false;
                Log.e(tag,"onFailed");
            }
        });

// 3. Open connection
        consumer.open();

// 4. Perform any action
//        subscription.perform("away");

// 5. Perform any action using JsonObject(GSON)
//        JsonObject params = new JsonObject();
//        params.addProperty("foo", "bar");
//        subscription.perform("appear", params);

        // 发送聊天消息
//        JsonObject params = new JsonObject();
//        params.addProperty("message", "hello");
//        subscription.perform("speak", params);
    }

    public boolean sendMessage(String content) {
        if(isConnected){
            JsonObject params = new JsonObject();
            params.addProperty("message", content);
            subscription.perform("speak", params);
            return true;
        }else{
            Toast.makeText(mFragment.getActivity(),"发送失败，未连接上服务器",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void closeSocket(){
        consumer.close();
    }

    public Message parseJson(JsonElement data) {
        Message reMessage =null;
        String username = (String) SPUtils.get(mFragment.getActivity(), Urls.USERNAME, "");
        try {
            JSONArray jsonArray = new JSONArray(data.toString());
            String key = (String) jsonArray.get(0);
            if("join".equals(key)){
                JSONObject jsonObject = jsonArray.getJSONObject(1);
                reMessage = new Message(Message.MSG_TYPE_TEXT, Message.MSG_STATE_SUCCESS,
                        jsonObject.getString("username"), jsonObject.getString("avatar_url"),
                        null,null,jsonObject.getString("username")+"进入房间" , false, true,
                        new Date());
            }else if("chat message".equals(key)){
                JSONObject jsonObject = jsonArray.getJSONObject(1);
                JSONObject sent_by = jsonObject.getJSONObject("sent_by");
                reMessage = new Message(Message.MSG_TYPE_TEXT, Message.MSG_STATE_SUCCESS,
                        sent_by.getString("username"), sent_by.getString("avatar_url"),
                        null,null,jsonObject.getString("content") , sent_by.getString
                        ("username").equals(username)?true:false, true,
                        new Date());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reMessage;
    }
}
