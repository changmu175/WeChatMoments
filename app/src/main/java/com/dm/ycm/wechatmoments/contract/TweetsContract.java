package com.dm.ycm.wechatmoments.contract;

import com.dm.ycm.wechatmoments.BasePresenter;
import com.dm.ycm.wechatmoments.BaseView;
import com.dm.ycm.wechatmoments.presenter.bean.TweetBean;
import com.dm.ycm.wechatmoments.presenter.bean.UserBean;

import java.io.File;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by ycm on 2017/6/7.
 * Description:
 * Modified by:
 */

public interface TweetsContract {

    interface View<D> extends BaseView<Presenter> {
        /**
         * 显示结果
         *
         * @param dataSource 数据
         */
        void showResult(D dataSource);

        void setUserInfo(UserBean user);

        void loadTweetsCallback(String url);

        void saveImageToLocal(ResponseBody responseBody, String url);

        void loadTweetsCallback(int start, int end);

        void addTweetsToCache(List<TweetBean> tweetBeanList);
    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 获取视图
         *
         * @return 视图
         */
        View getView(String method);

        /**
         * 绑定视图
         *
         * @param view 视图
         */
        void attachView(View view);

        void loadTweets(int start, int end);

        Observable<List<TweetBean>> loadTweets(String userName);

        Observable<UserBean> loadUserInfo(String userName);

        Observable<ResponseBody> loadImage(String url);

        Observable<File> saveImageToLocal(ResponseBody responseBody, String url);
    }

}
