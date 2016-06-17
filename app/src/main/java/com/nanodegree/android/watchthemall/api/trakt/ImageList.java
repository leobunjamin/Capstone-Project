package com.nanodegree.android.watchthemall.api.trakt;

/**
 * Class representing Trakt image list
 */
public class ImageList {

    private Image screenshot;
    private Image headshot;
    private Image poster;
    private Image banner;

    public Image getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(Image screenshot) {
        this.screenshot = screenshot;
    }

    public Image getHeadshot() {
        return headshot;
    }

    public void setHeadshot(Image headshot) {
        this.headshot = headshot;
    }

    public Image getPoster() {
        return poster;
    }

    public void setPoster(Image poster) {
        this.poster = poster;
    }

    public Image getBanner() {
        return banner;
    }

    public void setBanner(Image banner) {
        this.banner = banner;
    }

    @Override
    public String toString() {
        return "ImageList{" +
                "screenshot=" + screenshot.toString() +
                ", headshot=" + headshot.toString() +
                ", poster=" + poster.toString() +
                ", banner=" + banner.toString() +
                '}';
    }
}
