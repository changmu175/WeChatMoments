package com.dm.ycm.wechatmoments.view.weight;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dm.ycm.wechatmoments.R;
import com.dm.ycm.wechatmoments.presenter.bean.CommentsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycm on 2017/6/7.
 * Description: 评论列表视图
 * Modified by:
 */
public class CommentListView extends LinearLayout {
    private List<CommentsBean> mData;
    private LayoutInflater layoutInflater ;

    public void setData(List<CommentsBean> commentsBeen){
        if(commentsBeen == null ){
            commentsBeen = new ArrayList<>();
        }
        mData = commentsBeen;
        notifyDataSetChanged();
    }

    public CommentListView(Context context) {
        super(context);
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void notifyDataSetChanged(){
        removeAllViews();
        if(mData == null || mData.size() == 0){
            return;
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i = 0; i < mData.size(); i++){
            View view = getView(i);
            addView(view, i, layoutParams);
        }

    }

    private View getView(final int position){
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(getContext());
        }
        View convertView = layoutInflater.inflate(R.layout.item_comment, null, false);
        TextView commentTv = (TextView) convertView.findViewById(R.id.commentTv);
//        final CircleMovementMethod circleMovementMethod = new CircleMovementMethod(itemSelectorColor, itemSelectorColor);
        final CommentsBean bean = mData.get(position);
        String name = bean.getSenderBean().getNick();
        String content = bean.getContent();
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(name);
        if (!TextUtils.isEmpty(content)) {
            builder.append(": ").append(content);
        }
        commentTv.setText(builder);
//        commentTv.setMovementMethod(circleMovementMethod);
        return convertView;
    }
}
