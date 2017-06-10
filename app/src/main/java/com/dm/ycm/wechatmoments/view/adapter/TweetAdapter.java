package com.dm.ycm.wechatmoments.view.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dm.ycm.wechatmoments.R;
import com.dm.ycm.wechatmoments.common.utils.ImageLoader;
import com.dm.ycm.wechatmoments.presenter.TweetPresenter;
import com.dm.ycm.wechatmoments.presenter.bean.CommentsBean;
import com.dm.ycm.wechatmoments.presenter.bean.ImagesBean;
import com.dm.ycm.wechatmoments.presenter.bean.SenderBean;
import com.dm.ycm.wechatmoments.presenter.bean.TweetBean;
import com.dm.ycm.wechatmoments.presenter.bean.UserBean;
import com.dm.ycm.wechatmoments.view.adapter.holdview.FooterViewHolder;
import com.dm.ycm.wechatmoments.view.adapter.holdview.HeaderViewHolder;
import com.dm.ycm.wechatmoments.view.adapter.holdview.ImageViewHolder;
import com.dm.ycm.wechatmoments.view.adapter.holdview.TweetViewHolder;

import java.util.List;

/**
 * Created by ycm on 2017/6/7.
 * Description:
 * Modified by:
 */

public class TweetAdapter extends BaseAdapter {
    private final static int TYPE_HEAD = 0;
    private final static int TYPE_FOOT = 1;
    private final static int TYPE_TWEET = 2;
    private static final int HEADER_SIZE = 1;

    @Override
    public int getItemViewType(int position) {
        //第一个位置是个人信息的条目
        if (position == 0) {
            return TYPE_HEAD;
        }

        //根据位置算上拉加载动画的条目
        if (position == MAX_COUNT + 1) {
            return TYPE_FOOT;
        }
        return TYPE_TWEET;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_HEAD) {
            View headView = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_circle, parent, false);
            viewHolder = new HeaderViewHolder(headView, viewType);
        } else if (viewType == TYPE_FOOT) {
            View footView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view, parent, false);
            viewHolder = new FooterViewHolder(footView, viewType);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item, parent, false);
            if (viewType == TweetViewHolder.TYPE_IMAGE) {
                viewHolder = new ImageViewHolder(view, TYPE_TWEET);
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        List<TweetBean> data = getDataSource();
        UserBean user = (UserBean) getUserInfo();
        if (user == null || !user.isValid()) {
            return;
        }
        if (data == null || data.isEmpty()) {
            return;
        }
        if (getItemViewType(position) == TYPE_HEAD) {
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            holder.setMyAvatar(user);
            holder.setMyNick(user.getNick());
        } else if (getItemViewType(position) == TYPE_FOOT) {
            FooterViewHolder holder = (FooterViewHolder) viewHolder;
            if (position == 16) {
                holder.setFootLineVisible(true);
                holder.setFootBarVisible(false);
            } else {
                holder.setFootLineVisible(false);
                holder.setFootBarVisible(true);
                final int id = data.get(position - 2).getId();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //模拟加载过程，加载5条
                        TweetPresenter.getInstance().loadTweets(id + 1, id + 6);
                        MAX_COUNT += 5;
                    }
                }, 2500);
            }
        } else if (getItemViewType(position) == TYPE_TWEET) {
            data = getDataSource();
            final int tweetPosition = position - HEADER_SIZE;
            final TweetViewHolder holder = (TweetViewHolder) viewHolder;
            if (data.size() <= tweetPosition) {
                return;
            }
            final TweetBean tweetBeanEntry = data.get(tweetPosition);
            String name = tweetBeanEntry.getSenderBean().getNick();
            String headImg = tweetBeanEntry.getSenderBean().getAvatar();
            final String content = tweetBeanEntry.getContent();
            final List<CommentsBean> commentsEntryData = tweetBeanEntry.getComments();
            boolean hasComments;
            ImageLoader.getInstance().crateBuilder()
                    .load(headImg)
                    .preLoad(360, 360)
                    .error(R.drawable.default_image)
                    .placeholder(R.drawable.default_image)
                    .centerCrop()
                    .into(holder.headIv)
                    .build();
            holder.nameTv.setText(name);

            if (!TextUtils.isEmpty(content)) {
                holder.contentTv.setText(content);
            }
            holder.contentTv.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);

            SenderBean senderBean = data.get(tweetPosition).getSenderBean();
            if (senderBean == null) {
                return;
            }
            List<CommentsBean> commentsBeanList = data.get(tweetPosition).getComments();
            hasComments = commentsBeanList != null && !commentsBeanList.isEmpty();

            if (data.get(tweetPosition).getSenderBean().getUsername().equals(user.getUsername())) {
                holder.deleteBtn.setVisibility(View.VISIBLE);
            } else {
                holder.deleteBtn.setVisibility(View.GONE);
            }
            if (hasComments) {
                holder.commentList.setData(commentsEntryData);
                holder.digCommentBody.setVisibility(View.VISIBLE);
            } else {
                holder.digCommentBody.setVisibility(View.GONE);
            }
            switch (holder.viewType) {
                case TYPE_TWEET:
                    if (holder instanceof ImageViewHolder) {
                        final List<ImagesBean> imagesBeen = tweetBeanEntry.getImages();
                        if (imagesBeen != null && imagesBeen.size() > 0) {
                            ((ImageViewHolder) holder).gridView.setVisibility(View.VISIBLE);
                            ((ImageViewHolder) holder).gridView.setList(imagesBeen);
                        } else {
                            ((ImageViewHolder) holder).gridView.setVisibility(View.GONE);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (dataSource == null) {
            return 1;
        }

        if (dataSource.size() < MAX_COUNT) {
            //有列表头还有列表足，所以加2
            return dataSource.size() + 2;
        }
        return dataSource.size() + 2;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }
}
