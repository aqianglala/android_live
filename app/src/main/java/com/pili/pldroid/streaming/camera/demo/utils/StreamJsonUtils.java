package com.pili.pldroid.streaming.camera.demo.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by admin on 2016/3/15.
 */
public class StreamJsonUtils {

    public static String createStreamJson(String url)throws RuntimeException {
        String[] split = url.split("/");
        for(String str: split){
            Log.i("split",str);
        }
        Log.i("split",split.length+"");
        String host = split[2];
        String hub = split[3];
        String title = split[4];
        if(TextUtils.isEmpty(host)|| TextUtils.isEmpty(hub)|| TextUtils.isEmpty(title)){
            throw new RuntimeException("url不合法");
        }
//        String streamJson="{" +
//                "        \"id\":\"z1.test-hub.55d80075e3ba5723280000d2\",\n" +
//                "            \"createdAt\":\"2015-08-22T04:54:13.539Z\",\n" +
//                "            \"updatedAt\":\"2015-08-22T04:54:13.539Z\",\n" +
//                "            \"title\":"+title+",\n" +
//                "            \"hub\":"+hub+",\n" +
//                "            \"disabled\":false,\n" +
//                "            \"publishKey\":\"ca11e07f094c3a6e\",\n" +
//                "            \"publishSecurity\":\"dynamic\",\n" +
//                "            \"hosts\":{\n" +
//                "        \"publish\":{\n" +
//                "            \"rtmp\":"+host+"\n" +
//                "        },\n" +
//                "        \"live\":{\n" +
//                "            \"hdl\":"+host+",\n" +
//                "                    \"hls\":"+host+",\n" +
//                "                    \"rtmp\":"+host+"\n" +
//                "        },\n" +
//                "        \"playback\":{\n" +
//                "            \"hls\":\"ey636h.playback1.z1.pili.qiniucdn.com\"\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }";
        String streamJson = "{\n" +
                "    \"createdAt\": \"2015-08-22T04:54:13.539Z\",\n" +
                "    \"disabled\": false,\n" +
                "    \"hosts\": {\n" +
                "        \"live\": {\n" +
                "            \"hdl\": \"192.168.1.102\",\n" +
                "            \"hls\": \"192.168.1.102\",\n" +
                "            \"rtmp\": \"192.168.1.102\"\n" +
                "        },\n" +
                "        \"playback\": {\n" +
                "            \"hls\": \"ey636h.playback1.z1.pili.qiniucdn.com\"\n" +
                "        },\n" +
                "        \"publish\": {\n" +
                "            \"rtmp\": \"192.168.1.102\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"hub\": \"mic\",\n" +
                "    \"id\": \"z1.test-hub.55d80075e3ba5723280000d2\",\n" +
                "    \"publishKey\": \"ca11e07f094c3a6e\",\n" +
                "    \"publishSecurity\": \"dynamic\",\n" +
                "    \"title\": " + "\"+token+\",\n" +
                "    \"updatedAt\": \"2015-08-22T04:54:13.539Z\"\n" +
                "}";
        return streamJson;
    }
}