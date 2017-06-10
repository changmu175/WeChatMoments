package com.dm.ycm.wechatmoments.presenter.bean;

import android.text.TextUtils;

/**
 * Created by ycm on 2017/6/7.
 * Description:
 * Modified by:
 */

public class CommentsBean {
    private String content;
    private SenderBean senderBean;
    private boolean isValid = true;

    public CommentsBean(String content, SenderBean senderBean) {
        this.content = content;
        this.senderBean = senderBean;
        isValid = senderBean!= null && senderBean.isValid();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setSenderBean(SenderBean senderBean) {
        this.senderBean = senderBean;
    }

    public SenderBean getSenderBean() {
        return senderBean;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
