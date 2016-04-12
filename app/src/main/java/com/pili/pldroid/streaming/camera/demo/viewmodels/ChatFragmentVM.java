package com.pili.pldroid.streaming.camera.demo.viewmodels;

import com.pili.pldroid.streaming.camera.demo.interfaces.Urls;
import com.pili.pldroid.streaming.camera.demo.utils.L;

import org.json.JSONObject;

import java.net.MalformedURLException;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

/**
 * Created by admin on 2016/4/12.
 */
public class ChatFragmentVM {

    public void connectToServer(){
        try {
            SocketIO socketIO = new SocketIO(Urls.SOCKET);
            socketIO.connect(new IOCallback() {
                @Override
                public void onDisconnect() {
                    L.e("onDisconnect");
                }

                @Override
                public void onConnect() {
                    L.e("onConnect");
                }

                @Override
                public void onMessage(String s, IOAcknowledge ioAcknowledge) {
                    L.e("onMessage");
                }

                @Override
                public void onMessage(JSONObject jsonObject, IOAcknowledge ioAcknowledge) {
                    L.e("onMessage");
                }

                @Override
                public void on(String s, IOAcknowledge ioAcknowledge, Object... objects) {
                    L.e("on");
                }

                @Override
                public void onError(SocketIOException e) {
                    L.e("onError");
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
