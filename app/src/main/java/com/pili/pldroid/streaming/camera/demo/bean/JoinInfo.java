package com.pili.pldroid.streaming.camera.demo.bean;

/**
 * Created by admin on 2016/4/13.
 */
public class JoinInfo {

//    [
//            "join",
//    {
//        "avatar_url": "https://secure.gravatar.com/avatar/0f2b7b1570db6f20423cac2142646beb?s=80",
//            "id": 7,
//            "username": "qiang"
//    }
//    ]


    private String key;
    private UserInfo user;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }


    public static class UserInfo{
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
