package com.pili.pldroid.streaming.camera.demo.bean;

import java.util.List;

/**
 * Created by admin on 2016/4/11.
 */
public class CreateRoomBean {

    /**
     * id : 22
     * title : Learn English
     * image : null
     * live : null
     * rtmp_url : rtmp://ilikemac.local/live/22
     * mic_rtmp_url : rtmp://ilikemac.local/mic/22
     * broadcaster : {"id":7,"username":"qiang","avatar_url":"https://secure.gravatar
     * .com/avatar/0f2b7b1570db6f20423cac2142646beb?s=80"}
     * messages : []
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int id;
        private String title;
        private Object image;
        private Object live;
        private String rtmp_url;
        private String mic_rtmp_url;
        /**
         * id : 7
         * username : qiang
         * avatar_url : https://secure.gravatar.com/avatar/0f2b7b1570db6f20423cac2142646beb?s=80
         */

        private BroadcasterEntity broadcaster;
        private List<?> messages;

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public void setLive(Object live) {
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

        public Object getImage() {
            return image;
        }

        public Object getLive() {
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

        public static class BroadcasterEntity {
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
