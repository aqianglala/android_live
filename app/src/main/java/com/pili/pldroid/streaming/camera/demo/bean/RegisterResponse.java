package com.pili.pldroid.streaming.camera.demo.bean;

/**
 * Created by admin on 2016/4/8.
 */
public class RegisterResponse {

    /**
     * id : 8
     * username : 1111
     * avatar_url : https://secure.gravatar.com/avatar/36c0d699c789a19cb38c7098fb902a5e?s=80
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
