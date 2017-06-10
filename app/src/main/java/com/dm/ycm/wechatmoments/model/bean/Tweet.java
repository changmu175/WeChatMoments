package com.dm.ycm.wechatmoments.model.bean;

import java.util.List;

/**
 * Created by ycm on 2017/6/7.
 * Description: 推文
 * Modified by:
 */

public class Tweet {
    private String content;
    private List<Images> images;
    private Sender sender;
    private List<Comments> comments;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Sender getSender() {
        return sender;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public List<Comments> getComments() {
        return comments;
    }
}
