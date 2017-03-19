package com.sip.shortnews.model;

/**
 * Created by ssd on 3/17/17.
 */
public class HeaderModel {

    /**
     * welcome_message : Các Tin Chính Sáng Nay 17/03/2017
     * event_message :
     * avatar : http://192.168.1.102/ShortNews_Server/public/uploads/1489208054_random_94phpCqAQVZ.png
     */

    private String welcome_message;
    private String event_message;
    private String avatar;

    public String getWelcome_message() {
        return welcome_message;
    }

    public void setWelcome_message(String welcome_message) {
        this.welcome_message = welcome_message;
    }

    public String getEvent_message() {
        return event_message;
    }

    public void setEvent_message(String event_message) {
        this.event_message = event_message;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
