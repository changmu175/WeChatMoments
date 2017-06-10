package com.dm.ycm.wechatmoments.model.bean;

/**
 * Created by ycm on 2017/6/7.
 * Description: 用户
 * Modified by:
 */

public class User {
    private String profile_image;
    private String avatar;
    private String nick;
    private String username;

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
