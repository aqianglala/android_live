package com.pili.pldroid.streaming.camera.demo.interfaces;

/**
 * Created by admin on 2016/4/8.
 */
public interface Urls {
    String baseUrl = "http://192.168.1.153:3000";
    String register = baseUrl+"/users";
    String login = baseUrl+"/sessions";
    String rooms = baseUrl+"/rooms";
    String queued = baseUrl+"/rooms/room_id/calls";
    String push = "rtmp://192.168.1.153/live/";

    String SEED = "live";

    String USERNAME = "username";
    String TOKEN = "token";
    String EMAIL = "email";
    String ID = "id";

    int loginRequestCode = 1;
    int registerRequestCode = 2;

    String ROOM_INFO = "room_info";
    String IP="192.168.1.153";

    String SOCKET = "ws://192.168.1.153:3000/cable";
}
