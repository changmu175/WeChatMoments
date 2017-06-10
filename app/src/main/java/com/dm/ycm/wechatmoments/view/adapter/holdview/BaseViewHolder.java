package com.dm.ycm.wechatmoments.view.adapter.holdview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ycm on 2017/6/7.
 * Description: 基础条目视图
 * Modified by:
 */

public class BaseViewHolder extends RecyclerView.ViewHolder{
    public int viewType;
    public BaseViewHolder(View itemView, int itemType) {
        super(itemView);
        this.viewType = itemType;
    }
}
