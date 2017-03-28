package com.sip.shortnews.Utilies;

/**
 * Created by ssd on 3/28/17.
 */
public enum AnalaticEnum {
    NEWS_PAGE_CATEGORY_EVENT("news_page_category"),
    NEWS_PAGE_ACTION_SUCCESS_EVENT("load_page_success"),
    NEWS_PAGE_ACTION_ERROR_EVENT("load_page_error"),
    SOCIAL_PAGE_CATEGORY_EVENT("social_page_category"),
    SOCIAL_PAGE_ACTION_SUCCESS_EVENT("load_page_success"),
    SOCIAL_PAGE_ACTION_ERROR_EVENT("load_page_error");
    private final  String value ;
    private AnalaticEnum(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
