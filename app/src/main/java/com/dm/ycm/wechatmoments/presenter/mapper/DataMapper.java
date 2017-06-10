package com.dm.ycm.wechatmoments.presenter.mapper;

import com.dm.ycm.wechatmoments.model.bean.Comments;
import com.dm.ycm.wechatmoments.model.bean.Images;
import com.dm.ycm.wechatmoments.model.bean.Sender;
import com.dm.ycm.wechatmoments.model.bean.Tweet;
import com.dm.ycm.wechatmoments.model.bean.User;
import com.dm.ycm.wechatmoments.presenter.bean.CommentsBean;
import com.dm.ycm.wechatmoments.presenter.bean.ImagesBean;
import com.dm.ycm.wechatmoments.presenter.bean.SenderBean;
import com.dm.ycm.wechatmoments.presenter.bean.TweetBean;
import com.dm.ycm.wechatmoments.presenter.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycm on 2017/6/7.
 * Description: 数据转换器，用于不用层次解耦
 * Modified by:
 */

public class DataMapper {

    public CommentsBean mapComments(Comments comments) {
        return new CommentsBean(comments.getContent(), mapSender(comments.getSender()));
    }

    public ImagesBean mapImages(Images images) {
        return new ImagesBean(images.getUrl());
    }

    public SenderBean mapSender(Sender sender) {
        if (sender == null) {
            return null;
        }
        return new SenderBean(sender.getUsername(),
                sender.getNick(),
                sender.getAvatar());
    }

    public TweetBean mapTweet(Tweet tweet, int id) {
        TweetBean tweetBean = mapTweet(tweet);
        tweetBean.setId(id);
        return tweetBean;
    }

    public TweetBean mapTweet(Tweet tweet) {
        return new TweetBean(tweet.getContent(),
                mapImagesList(tweet.getImages()),
                mapSender(tweet.getSender()),
                mapCommentsList(tweet.getComments()));
    }

    public List<TweetBean> mapTweetList(List<Tweet> imageEntries) {
        if (imageEntries == null) {
            return null;
        }

        List<TweetBean> tweetBeanList = new ArrayList<>();
        for (int i = 0; i < imageEntries.size(); i ++) {
            tweetBeanList.add(mapTweet(imageEntries.get(i)));
        }
//        for (Tweet tweet : imageEntries) {
//            tweetBeanList.add(mapTweet(tweet));
//        }
        return tweetBeanList;
    }


    public UserBean mapUser(User user) {
        return new UserBean(user.getProfile_image(),
                user.getAvatar(),
                user.getNick(),
                user.getUsername());
    }

    private List<ImagesBean> mapImagesList(List<Images> imageEntries) {
        if (imageEntries == null) {
            return null;
        }

        List<ImagesBean> imagesList = new ArrayList<>();
        for (Images images : imageEntries) {
            imagesList.add(mapImages(images));
        }
        return imagesList;
    }

    private List<CommentsBean> mapCommentsList(List<Comments> imageEntries) {
        if (imageEntries == null) {
            return null;
        }

        List<CommentsBean> commentsList = new ArrayList<>();
        for (Comments comments : imageEntries) {
            commentsList.add(mapComments(comments));
        }
        return commentsList;
    }
}
