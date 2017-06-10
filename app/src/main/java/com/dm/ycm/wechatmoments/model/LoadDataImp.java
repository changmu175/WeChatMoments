package com.dm.ycm.wechatmoments.model;

import com.dm.ycm.wechatmoments.model.api.ImageApi;
import com.dm.ycm.wechatmoments.model.api.TweetsApi;
import com.dm.ycm.wechatmoments.model.bean.Tweet;
import com.dm.ycm.wechatmoments.model.bean.User;
import com.dm.ycm.wechatmoments.model.service.ImageRequestService;
import com.dm.ycm.wechatmoments.model.service.TweetRequestService;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by ycm on 2017/6/7.
 * Description:
 * Modified by:
 */

public class LoadDataImp implements LoadData {
    private TweetsApi requestService;
    private ImageApi requestImageService;

    public LoadDataImp(){
        requestService = TweetRequestService.createRetrofitService(TweetsApi.class);
        requestImageService = ImageRequestService.createRetrofitService(ImageApi.class);
    }

    @Override
    public Observable<List<Tweet>> loadTweets(String user) {
        return requestService.loadTweets(user);
    }

    @Override
    public Observable<User> loadUserInfo(String user) {
//        return requestImageService.loadImage("tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w");

        return requestService.loadUserInfo(user);
    }

    public Observable<ResponseBody> loadImage(String url) {
        return requestImageService.downloadPicFromNet(url);
    }
}
