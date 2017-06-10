package com.dm.ycm.wechatmoments.model.bean;

/**
 * Created by ycm on 2017/6/7.
 * Description: 评论列表
 * Modified by:
 */

public class Comments {
    private String content;
    private Sender sender;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Sender getSender() {
        return sender;
    }
}
