package com.pili.pldroid.streaming.camera.demo.bean;

import java.util.List;

/**
 * Created by admin on 2016/4/12.
 */
public class QueueBean {

    /**
     * id : 49
     * duration : 60
     * status : waiting
     * user : {"id":5,"username":"chakery","avatar_url":"https://secure.gravatar
     * .com/avatar/fc578202484636f09c2cfe5cf468a414?s=80"}
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int id;
        private int duration;
        private String status;
        /**
         * id : 5
         * username : chakery
         * avatar_url : https://secure.gravatar.com/avatar/fc578202484636f09c2cfe5cf468a414?s=80
         */

        private UserEntity user;

        public void setId(int id) {
            this.id = id;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public int getId() {
            return id;
        }

        public int getDuration() {
            return duration;
        }

        public String getStatus() {
            return status;
        }

        public UserEntity getUser() {
            return user;
        }

        public static class UserEntity {
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
