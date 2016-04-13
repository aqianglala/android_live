package com.pili.pldroid.streaming.camera.demo.bean;

import java.util.List;

/**
 * Created by admin on 2016/4/11.
 */
public class CreateRoomBean {

    /**
     * broadcaster : {"avatar_url":"https://secure.gravatar
     * .com/avatar/0f2b7b1570db6f20423cac2142646beb?s=80","id":7,"username":"qiang"}
     * id : 49
     * messages : []
     * title : 啊啊啊
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * avatar_url : https://secure.gravatar.com/avatar/0f2b7b1570db6f20423cac2142646beb?s=80
         * id : 7
         * username : qiang
         */

        private BroadcasterEntity broadcaster;
        private int id;
        private String title;
        private List<?> messages;

        public void setBroadcaster(BroadcasterEntity broadcaster) {
            this.broadcaster = broadcaster;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setMessages(List<?> messages) {
            this.messages = messages;
        }

        public BroadcasterEntity getBroadcaster() {
            return broadcaster;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public List<?> getMessages() {
            return messages;
        }

        public static class BroadcasterEntity {
            private String avatar_url;
            private int id;
            private String username;

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAvatar_url() {
                return avatar_url;
            }

            public int getId() {
                return id;
            }

            public String getUsername() {
                return username;
            }
        }
    }
}
