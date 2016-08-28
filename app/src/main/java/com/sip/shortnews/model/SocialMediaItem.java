package com.sip.shortnews.model;

/**
 * Created by ssd on 8/20/16.
 */
public class SocialMediaItem {


    /**
     * title : Chắc ít ai biết điều này
     * post_image_url : https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-9/14102497_1675637895817997_7794646477993400307_n.png?oh=127c556b50fff85de61289223135bfd1&oe=583D3612--inshortnew-- https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-9/14089115_1675311992517254_5659323232279288246_n.jpg?oh=14e1069238dcc9c75e505eb2c2e5055c&oe=583FCD42
     * separate_image_tag : --inshortnews--
     * is_video : 1
     * full_link : https://www.facebook.com/haivl.com/photos/a.384077014974098.86259.383923618322771/1675637895817997/?type=3&theater
     * video_link : http://vredir.nixcdn.com/dde6c3fe170e038d2bdb580f2c25164f/57bf0182/GoNCT1/ChungTaKhongThuocVeNhau-SonTungMTP-4529069.mp4
     * fanpage_name : Hội những người đỡ không nổi những người khó đỡ
     * fanpage_logo : http://localhost/blog/public/uploads/1472044616phpVhJqU5.png
     * social_name : facebook
     * social_logo : http://localhost/blog/public/uploads/1472024975phpue6oxD.png
     * color_tag :
     * video_tag : http://localhost/blog/public/uploads/1472024975php6NYnCW.png
     */

    private String title;
    private String post_image_url;
    private String separate_image_tag;
    private int is_video;
    private String full_link;
    private String video_link;
    private String fanpage_name;
    private String fanpage_logo;
    private String social_name;
    private String social_logo;
    private String color_tag;
    private String video_tag;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost_image_url() {
        return post_image_url;
    }

    public void setPost_image_url(String post_image_url) {
        this.post_image_url = post_image_url;
    }

    public String getSeparate_image_tag() {
        return separate_image_tag;
    }

    public void setSeparate_image_tag(String separate_image_tag) {
        this.separate_image_tag = separate_image_tag;
    }

    public int getIs_video() {
        return is_video;
    }

    public void setIs_video(int is_video) {
        this.is_video = is_video;
    }

    public String getFull_link() {
        return full_link;
    }

    public void setFull_link(String full_link) {
        this.full_link = full_link;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public String getFanpage_name() {
        return fanpage_name;
    }

    public void setFanpage_name(String fanpage_name) {
        this.fanpage_name = fanpage_name;
    }

    public String getFanpage_logo() {
        return fanpage_logo;
    }

    public void setFanpage_logo(String fanpage_logo) {
        this.fanpage_logo = fanpage_logo;
    }

    public String getSocial_name() {
        return social_name;
    }

    public void setSocial_name(String social_name) {
        this.social_name = social_name;
    }

    public String getSocial_logo() {
        return social_logo;
    }

    public void setSocial_logo(String social_logo) {
        this.social_logo = social_logo;
    }

    public String getColor_tag() {
        return color_tag;
    }

    public void setColor_tag(String color_tag) {
        this.color_tag = color_tag;
    }

    public String getVideo_tag() {
        return video_tag;
    }

    public void setVideo_tag(String video_tag) {
        this.video_tag = video_tag;
    }
}
