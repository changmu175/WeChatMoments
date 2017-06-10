package com.dm.ycm.wechatmoments.model;

import com.dm.ycm.wechatmoments.model.bean.Tweet;
import com.dm.ycm.wechatmoments.model.bean.User;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by ycm on 2017/6/7.
 * Description:
 * Modified by:
 */

public interface LoadData {

    Observable<List<Tweet>> loadTweets(String user);

    Observable<User> loadUserInfo(String user);

    Observable<ResponseBody> loadImage(String url);
}
