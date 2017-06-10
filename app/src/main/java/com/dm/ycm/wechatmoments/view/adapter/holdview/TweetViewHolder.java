package com.dm.ycm.wechatmoments.view.adapter.holdview;

import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dm.ycm.wechatmoments.R;
import com.dm.ycm.wechatmoments.view.weight.CommentListView;
import com.dm.ycm.wechatmoments.view.weight.ExpandTextView;

/**
 * Created by ycm on 2017/6/7.
 * Description: 推文条目视图抽象类
 * Modified by:
 */
public abstract class TweetViewHolder extends BaseViewHolder {
    public final static int TYPE_IMAGE = 2;
    public ImageView headIv;
    public TextView nameTv;
    public ExpandTextView contentTv;
    public TextView timeTv;
    public TextView deleteBtn;
    public ImageView snsBtn;
    /** 点赞列表*/
//    public PraiseListView praiseListView;

    public LinearLayout digCommentBody;
//    public View digLine;

    /** 评论列表 */
    public CommentListView commentList;
//    // ===========================
//    public SnsPopupWindow snsPopupWindow;

    public TweetViewHolder(View itemView, int viewType) {
        super(itemView, viewType);
//        this.viewType = viewType;
        ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewStub);
        initSubView(viewType, viewStub);
        headIv = (ImageView) itemView.findViewById(R.id.headIv);
        nameTv = (TextView) itemView.findViewById(R.id.nameTv);
//        digLine = itemView.findViewById(R.id.lin_dig);
        contentTv = (ExpandTextView) itemView.findViewById(R.id.contentTv);
//        urlTipTv = (TextView) itemView.findViewById(R.id.urlTipTv);
//        timeTv = (TextView) itemView.findViewById(R.id.timeTv);
        deleteBtn = (TextView) itemView.findViewById(R.id.deleteBtn);
        snsBtn = (ImageView) itemView.findViewById(R.id.snsBtn);
//        praiseListView = (PraiseListView) itemView.findViewById(R.id.praiseListView);

        digCommentBody = (LinearLayout) itemView.findViewById(R.id.digCommentBody);
        commentList = (CommentListView)itemView.findViewById(R.id.commentList);

//        snsPopupWindow = new SnsPopupWindow(itemView.getContext());

    }

    public abstract void initSubView(int viewType, ViewStub viewStub);

}
