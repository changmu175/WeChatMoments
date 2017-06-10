package com.dm.ycm.wechatmoments.model.api;

import com.dm.ycm.wechatmoments.model.bean.Tweet;
import com.dm.ycm.wechatmoments.model.bean.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ycm on 2017/6/7.
 * Description: 推文下载api
 * Modified by:
 */

public interface TweetsApi {
    /**
     * 推文下载
     * @param user 用户名
     * @return 推文列表
     */
    @GET("/user/{user}/tweets")
    Observable<List<Tweet>> loadTweets(@Path("user") String user);

    /**
     * 下载用户信息
     * @param user 用户名
     * @return 用户
     */
    @GET("/user/{user}")
    Observable<User> loadUserInfo(@Path("user") String user);
}
