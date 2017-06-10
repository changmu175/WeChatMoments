package com.dm.ycm.wechatmoments.presenter.bean;

import android.text.TextUtils;

/**
 * Created by ycm on 2017/6/7.
 * Description:
 * Modified by:
 */

public class UserBean {
    private String profile_image;
    private String avatar;
    private String nick;
    private String username;
    private boolean isValid = false;

    public UserBean(String profile_image, String avatar, String nick, String username) {
        this.profile_image = profile_image;
        this.avatar = avatar;
        this.nick = nick;
        this.username = username;
        isValid = !TextUtils.isEmpty(nick) && !TextUtils.isEmpty(avatar);
    }

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

    public boolean isValid() {
        return isValid;
    }
}
