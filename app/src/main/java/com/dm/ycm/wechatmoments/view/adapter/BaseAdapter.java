package com.dm.ycm.wechatmoments.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

/**
 * Created by ycm on 2017/6/7.
 * Description: 基础数据适配器
 * Modified by:
 */

public abstract class BaseAdapter<T, U, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{

    List<T> dataSource;
    U userInfo;
    static int MAX_COUNT = 5;
    public List<T> getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(List<T> dataSource) {
        this.dataSource = dataSource;
    }

    public void clearDataSource() {
        if (dataSource != null) {
            dataSource.clear();
            notifyDataSetChanged();
        }
        MAX_COUNT = 5;
    }

    public void setUserInfo(U userInfo) {
        this.userInfo = userInfo;
    }

    U getUserInfo() {
        return this.userInfo;
    }
}
