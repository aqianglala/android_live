package com.pili.pldroid.streaming.camera.demo.bean;

/**
 * Created by admin on 2016/4/8.
 */
public class RegisterBean {

    /**
     * username : abc
     * email : abc@talkpal.cc
     * password : 123456
     * password_confirmation : 123456
     */

    private UserEntity user;

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public static class UserEntity {
        public UserEntity(String username, String email, String password, String
                password_confirmation) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.password_confirmation = password_confirmation;
        }

        private String username;
        private String email;
        private String password;
        private String password_confirmation;

        public void setUsername(String username) {
            this.username = username;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setPassword_confirmation(String password_confirmation) {
            this.password_confirmation = password_confirmation;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getPassword_confirmation() {
            return password_confirmation;
        }
    }
}
