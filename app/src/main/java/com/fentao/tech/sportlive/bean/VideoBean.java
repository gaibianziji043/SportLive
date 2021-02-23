package com.fentao.tech.sportlive.bean;

/**
 * @ProjectName: SportLive
 * @Package: com.fentao.tech.sportlive.bean
 * @ClassName: VideoBean
 * @Description: java类作用描述
 * @Author: allen
 * @CreateDate: 2021/2/22 13:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/22 13:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class VideoBean {

    private String title;
    private String url;
    private String thumb;

    public VideoBean(String title,  String thumb,String url) {
        this.title = title;
        this.url = url;
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
