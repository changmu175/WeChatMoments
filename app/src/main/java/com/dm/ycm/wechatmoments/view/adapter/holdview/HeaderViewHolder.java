package com.dm.ycm.wechatmoments.view.adapter.holdview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dm.ycm.wechatmoments.R;
import com.dm.ycm.wechatmoments.common.utils.ImageLoader;
import com.dm.ycm.wechatmoments.presenter.bean.UserBean;

/**
 * Created by ycm on 2017/6/7.
 * Description:
 * Modified by:
 */

public class HeaderViewHolder  extends BaseViewHolder{
    private TextView myNick;
    private ImageView myAvatar;
    private ImageView myHeader;
    public HeaderViewHolder(View headerView, int viewType) {
        super(headerView, viewType);
        myNick = (TextView) headerView.findViewById(R.id.my_nick);
        myAvatar = (ImageView) headerView.findViewById(R.id.my_avatar);
        myHeader = (ImageView) headerView.findViewById(R.id.my_header);
    }

    public void setMyNick(String nick) {
        myNick.setText(nick);
    }

    /**
     * 设置头像
     * @param userBean
     */
    public void setMyAvatar( UserBean userBean) {
        if (userBean == null) {
            return;
        }

        ImageLoader.getInstance().crateBuilder()
                .load(userBean.getAvatar())
                .preLoad(360, 360)
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .centerCrop()
                .into(myAvatar)
                .build();
    }
}
