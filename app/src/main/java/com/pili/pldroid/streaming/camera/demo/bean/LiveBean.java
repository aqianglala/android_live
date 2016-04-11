package com.pili.pldroid.streaming.camera.demo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2016/4/9.
 */
public class LiveBean implements Serializable{

    /**
     * id : 18
     * title : Iiiiiiiii
     * image :
     * live : false
     * rtmp_url : rtmp://ilikemac.local/live/18
     * mic_rtmp_url : rtmp://ilikemac.local/mic/18
     * broadcaster : {"id":5,"username":"chakery","avatar_url":"https://secure.gravatar
     * .com/avatar/fc578202484636f09c2cfe5cf468a414?s=80"}
     * messages : []
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity implements Serializable{
        private int id;
        private String title;
        private String image;
        private boolean live;
        private String rtmp_url;
        private String mic_rtmp_url;
        /**
         * id : 5
         * username : chakery
         * avatar_url : https://secure.gravatar.com/avatar/fc578202484636f09c2cfe5cf468a414?s=80
         */

        private BroadcasterEntity broadcaster;
        private List<?> messages;

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setLive(boolean live) {
            this.live = live;
        }

        public void setRtmp_url(String rtmp_url) {
            this.rtmp_url = rtmp_url;
        }

        public void setMic_rtmp_url(String mic_rtmp_url) {
            this.mic_rtmp_url = mic_rtmp_url;
        }

        public void setBroadcaster(BroadcasterEntity broadcaster) {
            this.broadcaster = broadcaster;
        }

        public void setMessages(List<?> messages) {
            this.messages = messages;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getImage() {
            return image;
        }

        public boolean isLive() {
            return live;
        }

        public String getRtmp_url() {
            return rtmp_url;
        }

        public String getMic_rtmp_url() {
            return mic_rtmp_url;
        }

        public BroadcasterEntity getBroadcaster() {
            return broadcaster;
        }

        public List<?> getMessages() {
            return messages;
        }

        public static class BroadcasterEntity implements Serializable{
            private int id;
            private String username;
            private String avatar_url;

            public void setId(int id) {
                this.id = id;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }

            public int getId() {
                return id;
            }

            public String getUsername() {
                return username;
            }

            public String getAvatar_url() {
                return avatar_url;
            }
        }
    }
}
