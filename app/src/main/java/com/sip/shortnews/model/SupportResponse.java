package com.sip.shortnews.model;

/**
 * Created by ssd on 3/19/17.
 */
public class SupportResponse {

    /**
     * is_support : true
     * link_update :
     * message_update :
     */

    private boolean is_support;
    private String link_update;
    private String message_update;

    public boolean isIs_support() {
        return is_support;
    }

    public void setIs_support(boolean is_support) {
        this.is_support = is_support;
    }

    public String getLink_update() {
        return link_update;
    }

    public void setLink_update(String link_update) {
        this.link_update = link_update;
    }

    public String getMessage_update() {
        return message_update;
    }

    public void setMessage_update(String message_update) {
        this.message_update = message_update;
    }
}
