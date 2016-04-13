package com.pili.pldroid.streaming.camera.demo.bean;

/**
 * Created by admin on 2016/4/13.
 */
public class MessageInfo {
    //    [
//            "chat message",
//    {
//        "content": "哦哦",
//            "id": 116,
//            "sent_by": {
//        "avatar_url": "https://secure.gravatar.com/avatar/0f2b7b1570db6f20423cac2142646beb?s=80",
//                "id": 7,
//                "username": "qiang"
//    }
//    }
//    ]
    private String key;
    private Info info;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public static class Info{
        private String content;
        private int id;
        /**
         * avatar_url : https://secure.gravatar.com/avatar/0f2b7b1570db6f20423cac2142646beb?s=80
         * id : 7
         * username : qiang
         */

        private SentByEntity sent_by;

        public void setContent(String content) {
            this.content = content;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setSent_by(SentByEntity sent_by) {
            this.sent_by = sent_by;
        }

        public String getContent() {
            return content;
        }

        public int getId() {
            return id;
        }

        public SentByEntity getSent_by() {
            return sent_by;
        }

        public static class SentByEntity {
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
