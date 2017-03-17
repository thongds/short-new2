package com.sip.shortnews.model;

/**
 * Created by ssd on 8/27/16.
 */
public class NewsHomeItem {


    /**
     * post_title : Nhân viên Arsenal xin thôi việc sau phát biểu của Wenger
     * post_content : “Giáo sư” cho biết sẵn sàng chi tới 300 triệu bảng nếu có một cầu thủ phù hợp. Mặc khác, ông cũng nhắc nhở rằng Arsenal có tới 600 nhân viên để trả lương hàng tháng.Sau phát biểu của Wenger, một nhân viên bán hàng cảm thấy tự ái và quyết định thôi việc
     * post_image : http://img.f1.thethao.vnecdn.net/2016/08/25/arsenal-5229-1472097305.jpg
     * is_video : 0
     * created : 1472177283
     * video_link :
     * full_link :
     * title_color : #984A53
     * paper_logo : http://192.168.1.101/blog/public/uploads/1472095416phprYnTUo.png
     * paper_tag_color : #984A53
     * video_tag_image : http://localhost/blog/public/uploads/1472095416phpkDYz4D.png
     */

    private int id;
    private String post_title;
    private String post_content;
    private String post_image;
    private int is_video;
    private String created;
    private String video_link;
    private String full_link;
    private String title_color;
    private String paper_logo;
    private String paper_tag_color;
    private String video_tag_image;
    private int is_ads;
    private Object ads_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public int getIs_video() {
        return is_video;
    }

    public void setIs_video(int is_video) {
        this.is_video = is_video;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public String getFull_link() {
        return full_link;
    }

    public void setFull_link(String full_link) {
        this.full_link = full_link;
    }

    public String getTitle_color() {
        return title_color;
    }

    public void setTitle_color(String title_color) {
        this.title_color = title_color;
    }

    public String getPaper_logo() {
        return paper_logo;
    }

    public void setPaper_logo(String paper_logo) {
        this.paper_logo = paper_logo;
    }

    public String getPaper_tag_color() {
        return paper_tag_color;
    }

    public void setPaper_tag_color(String paper_tag_color) {
        this.paper_tag_color = paper_tag_color;
    }

    public String getVideo_tag_image() {
        return video_tag_image;
    }

    public void setVideo_tag_image(String video_tag_image) {
        this.video_tag_image = video_tag_image;
    }

    public int getIs_ads() {
        return is_ads;
    }

    public void setIs_ads(int is_ads) {
        this.is_ads = is_ads;
    }

    public Object getAds_code() {
        return ads_code;
    }

    public void setAds_code(Object ads_code) {
        this.ads_code = ads_code;
    }
}
