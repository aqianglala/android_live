package com.pili.pldroid.streaming.camera.demo.bean;

/**
 * Created by admin on 2016/4/8.
 */
public class LoginBean {

    /**
     * email : abc@talkpal.cc
     * password : 123456
     */

    private SessionEntity session;

    public void setSession(SessionEntity session) {
        this.session = session;
    }

    public SessionEntity getSession() {
        return session;
    }

    public static class SessionEntity {
        private String email;
        private String password;

        public SessionEntity(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
