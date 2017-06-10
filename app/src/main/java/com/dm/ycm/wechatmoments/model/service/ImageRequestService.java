package com.dm.ycm.wechatmoments.model.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.dm.ycm.wechatmoments.MyApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ycm on 2017/6/8.
 * Description:
 * Modified by:
 */

public class ImageRequestService {
    public static <T> T createRetrofitService(final Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://encrypted-tbn3.gstatic.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //添加Rxjava
                .addConverterFactory(GsonConverterFactory.create()) // <span style="font-family: Arial, Helvetica, sans-serif;">定义转化器 可以将结果返回一个json格式</span>
                .build();
       return retrofit.create(service);
    }

//
//    public static <T> T createRetrofitService(final Class<T> service) {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        /**
//         * 获取缓存
//         */
//        Interceptor baseInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                if (!isOnline()) {
//                    /**
//                     * 离线缓存控制  总的缓存时间=在线缓存时间+设置离线缓存时间
//                     */
//                    int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周,单位:秒
//                    CacheControl tempCacheControl = new CacheControl.Builder()
//                            .onlyIfCached()
//                            .maxStale(maxStale, TimeUnit.SECONDS)
//                            .build();
//                    request = request.newBuilder()
//                            .header("Cache-Control", "public, only-if-cached")
//                            .cacheControl(tempCacheControl)
//                            .build();
//                }
//                return chain.proceed(request);
//            }
//        };
//        //只有 网络拦截器环节 才会写入缓存,在有网络的时候 设置缓存时间
//        Interceptor rewriteCacheControlInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                Response originalResponse = chain.proceed(request);
//                int maxAge = 1 * 60; // 在线缓存在1分钟内可读取 单位:秒
//                return originalResponse.newBuilder()
//                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                        .removeHeader("Cache-Control")
//                        .header("Cache-Control", "public, max-age=" + maxAge)
//                        .build();
//            }
//        };
//        //设置缓存路径 内置存储
//        //File httpCacheDirectory = new File(context.getCacheDir(), "responses");
//        //外部存储
//        File httpCacheDirectory = new File(MyApplication.getContext().getExternalCacheDir(), "responses");
//        //设置缓存 10M
//        int cacheSize = 10 * 1024 * 1024;
//        Cache cache = new Cache(httpCacheDirectory, cacheSize);
//
//
//
//
//
//
//
//
//
//
//
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request().newBuilder().build();
//                return chain.proceed(request);
//            }
//        }).cache(cache).addInterceptor(httpLoggingInterceptor)
//                .addInterceptor(baseInterceptor)
//                .addNetworkInterceptor(rewriteCacheControlInterceptor);
//
//        OkHttpClient okHttpClient = builder.build();
//        Retrofit retrofit = new Retrofit
//                .Builder()
//                .client(okHttpClient)
//                .baseUrl("https://encrypted-tbn3.gstatic.com")
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        return retrofit.create(service);
//    }
//
//    public static boolean isOnline() {
//        ConnectivityManager cm = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        return netInfo != null && netInfo.isConnectedOrConnecting();
//    }
}
