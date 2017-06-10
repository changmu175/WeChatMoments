package com.dm.ycm.wechatmoments.presenter.bean;

import android.text.TextUtils;

/**
 * Created by ycm on 2017/6/7.
 * Description:
 * Modified by:
 */

public class SenderBean {
    private String username;
    private String nick;
    private String avatar;
    private boolean isValid = true;

    public SenderBean(String username, String nick, String avatar) {
        this.username = username;
        this.nick = nick;
        this.avatar = avatar;
        isValid = !TextUtils.isEmpty(username) || !TextUtils.isEmpty(nick);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
