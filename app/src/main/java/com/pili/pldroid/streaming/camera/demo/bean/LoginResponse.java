package com.pili.pldroid.streaming.camera.demo.bean;

/**
 * Created by admin on 2016/4/9.
 */
public class LoginResponse {

    /**
     * id : 7
     * username : qiang
     * email : 164748581@qq.com
     * token : xifJJFhK8+woFwdqA96stbIQyhsL3+ej37jN7jPc4YmTc+VzycCvc+w
     * /W1bHvgWSr2gq6WDkHXnMWsBEM3p/2g==
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
        private String email;
        private String token;

        public void setId(int id) {
            this.id = id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getToken() {
            return token;
        }
    }
}
