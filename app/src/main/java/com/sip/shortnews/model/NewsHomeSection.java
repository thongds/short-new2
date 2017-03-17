package com.sip.shortnews.model;

import java.util.List;

/**
 * Created by ssd on 3/15/17.
 */
public class NewsHomeSection {

    private NewsHomeHeader message;

    private List<NewsHomeItem> data;

    public NewsHomeHeader getNewsHomeHeader() {
        return message;
    }

    public void setMessage(NewsHomeHeader message) {
        this.message = message;
    }

    public List<NewsHomeItem> getData() {
        return data;
    }

    public void setData(List<NewsHomeItem> data) {
        this.data = data;
    }

}
