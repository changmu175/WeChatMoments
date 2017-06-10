package com.dm.ycm.wechatmoments.presenter.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ycm on 2017/6/7.
 * Description:
 * Modified by:
 */

public class TweetBean implements Serializable {
    private String content;
    private List<ImagesBean> images;
    private SenderBean senderBean;
    private List<CommentsBean> comments;
    private int id;
    private boolean isValid = true;

    public TweetBean(String content, List<ImagesBean> images, SenderBean senderBean, List<CommentsBean> comments) {
        this.content = content;
        this.images = images;
        this.senderBean = senderBean;
        this.comments = comments;
        isValid = (senderBean != null && senderBean.isValid()) && (!TextUtils.isEmpty(content) || !(images == null || images.isEmpty()));
    }

    public TweetBean(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setSenderBean(SenderBean senderBean) {
        this.senderBean = senderBean;
    }

    public SenderBean getSenderBean() {
        return senderBean;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
