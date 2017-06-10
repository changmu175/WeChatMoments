package com.dm.ycm.wechatmoments.model.bean;

/**
 * Created by ycm on 2017/6/7.
 * Description: 发送者
 * Modified by:
 */

public class Sender {
    private String username;
    private String nick;
    private String avatar;

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
}
