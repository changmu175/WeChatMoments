package com.dm.ycm.wechatmoments.view.adapter.holdview;

import android.view.View;
import android.view.ViewStub;

import com.dm.ycm.wechatmoments.R;
import com.dm.ycm.wechatmoments.view.weight.ImageGridView;

/**
 * Created by ycm on 2017/6/7.
 * Description:图片条目视图
 * Modified by:
 */
public class ImageViewHolder extends TweetViewHolder {
    public ImageGridView gridView;

    public ImageViewHolder(View itemView, int itemType){
        super(itemView, itemType);
    }

    @Override
    public void initSubView(int viewType, ViewStub viewStub) {
        if(viewStub == null){
            throw new IllegalArgumentException("viewStub is null...");
        }
        viewStub.setLayoutResource(R.layout.image_gridview);
        View subView = viewStub.inflate();
        ImageGridView imageGridView = (ImageGridView) subView.findViewById(R.id.imageGridView);
        if(imageGridView != null){
            this.gridView = imageGridView;
        }
    }
}
