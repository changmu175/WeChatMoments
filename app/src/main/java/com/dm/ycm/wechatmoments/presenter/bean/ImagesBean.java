package com.dm.ycm.wechatmoments.presenter.bean;

/**
 * Created by ycm on 2017/6/7.
 * Description:
 * Modified by:
 */

public class ImagesBean {
    private String url;
    private int width;
    private int height;

    public ImagesBean(String url) {
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
