package com.pili.pldroid.streaming.camera.demo.bean;

/**
 * Created by admin on 2016/4/8.
 */
public class RegisterResponse {
    /**
     * email : ...
     * username : ...
     */
    public static class Error{

        private String email;
        private String username;

        public void setEmail(String email) {
            this.email = email;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }

    }


    /**
     * id : 1
     * username : abc
     */
    public static class Data{

        private int id;
        private String username;

        public void setId(int id) {
            this.id = id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

    }
}
