package com.pili.pldroid.streaming.camera.demo.bean;

/**
 * Created by admin on 2016/4/11.
 */
public class RoomParamsBean {

    /**
     * title : Learn English
     */

    private RoomEntity room;

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public static class RoomEntity {
        public RoomEntity(String title) {
            this.title = title;
        }

        private String title;

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
