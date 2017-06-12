package com.dm.ycm.wechatmoments.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dm.ycm.wechatmoments.R;
import com.dm.ycm.wechatmoments.common.Constdef;
import com.dm.ycm.wechatmoments.common.utils.HashKeyUtils;
import com.dm.ycm.wechatmoments.common.utils.TweetCache;
import com.dm.ycm.wechatmoments.contract.TweetsContract;
import com.dm.ycm.wechatmoments.presenter.TweetPresenter;
import com.dm.ycm.wechatmoments.presenter.bean.TweetBean;
import com.dm.ycm.wechatmoments.presenter.bean.UserBean;
import com.dm.ycm.wechatmoments.view.adapter.TweetAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by ycm on 2017/6/7.
 * Description: 主界面
 * Modified by:
 */
public class MainActivity extends AppCompatActivity implements TweetsContract.View<List<TweetBean>>,
        SwipeRefreshLayout.OnRefreshListener {
    private TweetAdapter tweetAdapter;//数据适配器
    private TweetPresenter tweetPresenter;//MVP的presenter
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;
    private TweetCache tweetCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        tweetPresenter.attachView(this);
        recyclerView.setAdapter(tweetAdapter);
        tweetCache = new TweetCache();
        refreshLayout.setRefreshing(true);
        loadUserInfo(Constdef.USER_NAME);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void initView() {
        tweetAdapter = new TweetAdapter();
        tweetPresenter = TweetPresenter.getInstance();
        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.moments_rv);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.freshlayout);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tweetPresenter.attachView(this);
    }

    /**
     * 设置UserInfo到适配器中
     * @param user 用户信息
     */
    @Override
    public void setUserInfo(UserBean user) {
        tweetAdapter.setUserInfo(user);
    }

    /**
     * 加载推文信息
     * @param url 链接
     */
    @Override
    public void loadTweetsCallback(String url) {
        if (tweetAdapter != null) {
            tweetAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 保存图片到本地
     * @param responseBody 网络数据
     * @param url 链接
     */
    @Override
    public void saveImageToLocal(ResponseBody responseBody, String url) {
        tweetPresenter.saveImageToLocal(responseBody, url);
    }

    /**
     * 加载图文到缓存
     * @param tweetBeanList 推文列表
     */
    @Override
    public void addTweetsToCache(List<TweetBean> tweetBeanList) {
        tweetCache.addTweetsToMemoryCache(tweetBeanList);
        loadTweetsCallback(0, 5);
    }

    /**
     * 显示数据
     * @param dataSource 数据
     */
    @Override
    public void showResult(List<TweetBean> dataSource) {
        List<TweetBean> tweetBeanList = tweetAdapter.getDataSource();
        if (tweetAdapter != null) {
            if (tweetBeanList == null) {
                refreshLayout.setRefreshing(false);
                tweetAdapter.setDataSource(dataSource);
            } else if (tweetBeanList.isEmpty()) {
                tweetAdapter.setDataSource(dataSource);
            } else if (tweetBeanList.size() != 15) {
                tweetAdapter.getDataSource().addAll(dataSource);
            }
            tweetAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 加载用户信息
     * @param userName 用户名
     */
    public void loadUserInfo(String userName) {
        final String key = HashKeyUtils.hashKeyForDisk(userName);
        //从内存中获取
        UserBean userBean = tweetCache.getUserFromMemoryCache(key);
        if (userBean == null) {
            //内存中没有数据，去网络加载
            tweetPresenter.loadUserInfo(userName);
            return;
        }
        setUserInfo(userBean);
        loadTweetsCallback(0, 5);
    }

    /**
     * 加载一定范围的内的推文
     * @param start 开始ID
     * @param end 结束ID
     */
    @Override
    public void loadTweetsCallback(int start, int end) {
        List<TweetBean> tweetBeanList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            //以ID作为缓存的key
            final String key = HashKeyUtils.hashKeyForDisk(String.valueOf(i));
            //先从内存中获取推文
            TweetBean tweetBean = tweetCache.getTweetFromMemoryCache(key);
            if (tweetBean == null) {
                //内存中没有则从网上下载
                tweetPresenter.loadTweets(Constdef.USER_NAME);
                return;
            }
            tweetBeanList.add(tweetBean);
        }
        showResult(tweetBeanList);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        if (tweetAdapter != null) {
            tweetAdapter.clearDataSource();
        }

        loadTweetsCallback(0, 5);
        refreshLayout.setRefreshing(false);
    }
}
