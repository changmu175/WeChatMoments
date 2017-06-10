package com.dm.ycm.wechatmoments.common.utils;

import android.util.LruCache;

import com.dm.ycm.wechatmoments.presenter.bean.TweetBean;
import com.dm.ycm.wechatmoments.presenter.bean.UserBean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by ycm on 2017/6/8.
 * Description: 推文缓存
 * Modified by:
 */

public class TweetCache {
    //推文缓存
    private LruCache<String, TweetBean> tweetLruCache;
    //用户缓存
    private LruCache<String, UserBean> userLruCache;

    public TweetCache() {
        initCache();
    }

    private void initCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        tweetLruCache = new LruCache<String, TweetBean>(cacheSize) {
            @Override
            protected int sizeOf(String key, TweetBean value) {
                return super.sizeOf(key, value);
            }
        };

        userLruCache =  new LruCache<String, UserBean>(cacheSize) {
            @Override
            protected int sizeOf(String key, UserBean value) {
                return super.sizeOf(key, value);
            }
        };
    }

    public void clearCache() {
        userLruCache.evictAll();
        tweetLruCache.evictAll();
    }

    /**
     * 添加推文到内存
     * @param tweets 推文列表
     */
    public  void addTweetsToMemoryCache(List<TweetBean> tweets) {
        if (tweets == null || tweets.isEmpty()) {
            return;
        }

        for (int i = 0; i < tweets.size(); i++) {
            String key = hashKeyForDisk(String.valueOf(tweets.get(i).getId()));
            addTweetToMemoryCache(key, tweets.get(i));
        }
    }

    /**
     * 添加推文到内存
     * @param key 键值
     * @param tweet 推文
     */
    public void addTweetToMemoryCache(String key, TweetBean tweet) {
        if (tweet == null) {
            return;
        }
        if (getTweetFromMemoryCache(key) == null) {
            tweetLruCache.put(key, tweet);
        }
    }


    /**
     * 从内存获取用户信息
     * @param key 键值
     * @return 用户信息
     */
    public UserBean getUserFromMemoryCache(String key) {
        return userLruCache.get(key);
    }

    /**
     * 从内存获取推文
     * @param key key
     * @return 推文
     */
    public TweetBean getTweetFromMemoryCache(String key) {
        return tweetLruCache.get(key);
    }

    /**
     * 使用MD5算法对传入的key进行加密并返回。
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }


    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
