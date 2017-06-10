package com.dm.ycm.wechatmoments.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.dm.ycm.wechatmoments.MyApplication;
import com.dm.ycm.wechatmoments.common.utils.HashKeyUtils;
import com.dm.ycm.wechatmoments.common.utils.ImageLoader;
import com.dm.ycm.wechatmoments.contract.TweetsContract;
import com.dm.ycm.wechatmoments.model.LoadData;
import com.dm.ycm.wechatmoments.model.LoadDataImp;
import com.dm.ycm.wechatmoments.model.bean.Tweet;
import com.dm.ycm.wechatmoments.model.bean.User;
import com.dm.ycm.wechatmoments.presenter.bean.TweetBean;
import com.dm.ycm.wechatmoments.presenter.bean.UserBean;
import com.dm.ycm.wechatmoments.presenter.mapper.DataMapper;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ycm on 2017/6/7.
 * Description:
 * Modified by:
 */
public class TweetPresenter implements TweetsContract.Presenter {
    //    private LoadData loadData;
    private TweetsContract.View view;
    private DataMapper dataMapper;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private static TweetPresenter instance;

    public static TweetPresenter getInstance() {
        synchronized (TweetPresenter.class) {
            if (instance == null) {
                synchronized (TweetPresenter.class) {
                    if (instance == null) {
                        instance = new TweetPresenter();
                    }
                }
            }
        }
        return instance;
    }

    private TweetPresenter() {
//        loadData = new LoadDataImp();
        dataMapper = new DataMapper();
    }

    /**
     * 绑定视图
     *
     * @param view 视图
     */
    @Override
    public void attachView(TweetsContract.View view) {
        Log.d("ghghghghgh attachView  ", "  " + (view == null) + "   " + Thread.currentThread().getId());
        this.view = view;
    }

    @Override
    public TweetsContract.View getView(String method) {
        Log.d("ghghghghgh getView  ", "method  " + method + "  " + (view == null) + "   " + Thread.currentThread().getId());
        return view;
    }

    @Override
    public Observable<List<TweetBean>> loadTweets(String userName) {
        Log.d("kkkkkkk loadTweets", Thread.currentThread().getName());
        LoadData loadData = new LoadDataImp();
        mSubscriptions
                .add(loadData.loadTweets(userName)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
//                                getView().showLoading();
                            }
                        })
                        .doOnTerminate(new Action0() {
                            @Override
                            public void call() {
//                                getView().dismiss();
                            }
                        })
                        .subscribe(new Observer<List<Tweet>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<Tweet> tweetEntries) {
//                                //解析搜索结果
//                                GitUser user = SearchUtils.parseReposInfoJson(name, gitUser);
//                                getView().setIsSearching(false);
//                                //显示搜索结果
                                List<TweetBean> tweetBeanList = filterValidTweet(dataMapper.mapTweetList(tweetEntries));
                                getView("loadTweets").addTweetsToCache(tweetBeanList);
//                                getView().showResult(tweetBeanList);
//                                getView().showResultCallback();
                            }
                        }));
        return null;
    }

    private List<TweetBean> filterValidTweet(List<TweetBean> tweetBeanList) {
        if (tweetBeanList == null || tweetBeanList.isEmpty()) {
            return tweetBeanList;
        }

        int id = 0;
        Iterator<TweetBean> tweetBeanIterator = tweetBeanList.iterator();
        while (tweetBeanIterator.hasNext()) {
            TweetBean tweetBean = tweetBeanIterator.next();
            if (!tweetBean.isValid()) {
                tweetBeanIterator.remove();
            } else {
                tweetBean.setId(id);
                id++;
            }
        }
        return tweetBeanList;
    }

    @Override
    public Observable<UserBean> loadUserInfo(final String userName) {
        Log.d("kkkkkkk loadUserInfo  ", Thread.currentThread().getId() + " ");
        LoadData loadData = new LoadDataImp();
        mSubscriptions
                .add(loadData.loadUserInfo(userName)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
//                                getView().showLoading();
                            }
                        })
                        .doOnTerminate(new Action0() {
                            @Override
                            public void call() {
//                                getView().dismiss();
                            }
                        })
                        .subscribe(new Observer<User>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(User userEntry) {
                                UserBean user = dataMapper.mapUser(userEntry);
                                Log.d("ghghghghgh loadUserInfo", (getView("loadUserInfo") == null) + "");
                                getView("loadUserInfo").setUserInfo(user);
                                loadTweets(0,5);
//                                getView("loadUserInfo").loadTweetsCallback(0, 5);
                            }
                        }));
        Log.d("ghghghghgh loadUserInfo","  " + (view == null) + "   " + Thread.currentThread().getId());
        return null;
    }

    public void loadTweets(int start, int end) {
        getView("loadUserInfo").loadTweetsCallback(start, end);
    }

    @Override
    public Observable<ResponseBody> loadImage(final String url) {
        final String uu = "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg";
        Log.d("ghghghghgh loadImage111","  " + (view == null) + "   " + Thread.currentThread().getId());
        LoadData loadData = new LoadDataImp();
        mSubscriptions
                .add(loadData.loadImage(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
//                                getView().showLoading();
                            }
                        })
                        .doOnTerminate(new Action0() {
                            @Override
                            public void call() {
//                                getView().dismiss();
                            }
                        })
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                if (uu.equals(url)) {
                                    Log.d("wechatmomentuu ", "loadImage and  error" + e.getMessage());
                                }
                                Log.d("wechatmoment", "loadImage and  error" + e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody userEntry) {
                                Log.d("ghghghghgh loadImage", (getView("loadImage") == null) + "");
//                                getView("loadImage").saveImageToLocal(userEntry, url);
                                if (uu.equals(url)) {
                                    Log.d("wechatmomentuu ", "loadImage ");
                                }
                                saveImageToLocal(userEntry, url);
//                                getView().loadTweetsCallback();
                            }
                        }));
        return null;
    }

    @Override
    public Observable<File> saveImageToLocal(ResponseBody responseBody, final String url) {
        mSubscriptions
                .add(Observable.just(responseBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Bitmap bitmap;
                        String key = HashKeyUtils.hashImageKeyForDisk(url);
                        String DEFAULT_SAVE_IMAGE_PATH = MyApplication.getContext().getExternalCacheDir() + File.separator + "image_manager_disk_cache" ;
                        try {
                            byte[] bytes = responseBody.bytes();
                            bitmap = byteToBitmap(bytes);
                            File saveFile = new File(DEFAULT_SAVE_IMAGE_PATH, key);
                            saveBitmap(saveFile, bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("wechatmoment", "saveImageToLocal and  call" + responseBody.toString());
                        File file = new File(DEFAULT_SAVE_IMAGE_PATH +File.separator + key);
                        if (file.exists()) {
                            String filePath = DEFAULT_SAVE_IMAGE_PATH + File.separator + url;
                            Bitmap bm = ImageLoader.getInstance().decodeSampledBitmapFromResource(filePath, 360, 360);
                            //加入内存
//                            String key = HashKeyUtils.hashKeyForDisk(filePath);
                            ImageLoader.getInstance().addBitmapToLruCache(key, bm);
                            //存入缓存
                            ImageLoader.getInstance().putBitmapToDiskCache(filePath, bm, 360, 360);
                            getView("saveImageToLocal").loadTweetsCallback(url);
                        }
                    }
                }));
        return null;
    }
    private boolean saveBitmap(File saveFile, Bitmap bitmap) {
        if (saveFile == null || bitmap == null) {
            return false;
        }
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        BufferedOutputStream bos = null;
        boolean bSaveRet = false;
        try {
            //创建文件
            saveFile.createNewFile();
            //写入文件
            bos = new BufferedOutputStream(new FileOutputStream(saveFile));
            bSaveRet = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bSaveRet;
    }

    public static Bitmap byteToBitmap(byte[] imgByte) {
        InputStream input = null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        input = new ByteArrayInputStream(imgByte);
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
        bitmap = (Bitmap) softRef.get();
        if (imgByte != null) {
            imgByte = null;
        }

        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }

    public interface ShowResult{
        void loadTweetsCallback1(String url);
    }
}

