package com.sip.shortnews.Utilies;

/**
 * Created by ssd on 3/18/17.
 */
public enum  SocialContentType {
    YOUTUBE(3),VIDEO(0),GIF(2),IMAGE(1);
    private final  int value ;
    private SocialContentType(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
}
