package com.pili.pldroid.streaming.camera.demo.bean;

/**
 * Created by admin on 2016/4/15.
 */
public class CallBean {

    /**
     * id : 165
     * duration : 60
     * status : waiting
     * user : {"id":7,"username":"qiang","avatar_url":"https://secure.gravatar
     * .com/avatar/0f2b7b1570db6f20423cac2142646beb?s=80"}
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
        private int duration;
        private String status;
        /**
         * id : 7
         * username : qiang
         * avatar_url : https://secure.gravatar.com/avatar/0f2b7b1570db6f20423cac2142646beb?s=80
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
