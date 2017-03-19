package com.sip.shortnews.model;

import java.util.List;

/**
 * Created by ssd on 3/18/17.
 */
public class SocialMediaSection {


    private HeaderModel message;

    private List<SocialMediaItem> data;

    public HeaderModel getMessage() {
        return message;
    }

    public void setMessage(HeaderModel message) {
        this.message = message;
    }

    public List<SocialMediaItem> getData() {
        return data;
    }

    public void setData(List<SocialMediaItem> data) {
        this.data = data;
    }


}
