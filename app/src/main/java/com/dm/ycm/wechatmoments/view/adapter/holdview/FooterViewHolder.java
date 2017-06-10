package com.dm.ycm.wechatmoments.view.adapter.holdview;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dm.ycm.wechatmoments.R;

/**
 * Created by ycm on 2017/6/9.
 * Description: 底部条目视图
 * Modified by:
 */

public class FooterViewHolder extends BaseViewHolder{
    private LinearLayout footBar_ll;
    private ProgressBar footBar;
    private LinearLayout footLine_ll;
    public FooterViewHolder(View itemView, int itemType) {
        super(itemView, itemType);
        footBar = (ProgressBar) itemView.findViewById(R.id.footer_bar);
        footLine_ll = (LinearLayout) itemView.findViewById(R.id.footer_line);
        footBar_ll = (LinearLayout) itemView.findViewById(R.id.footer_ll);
    }

    public void setFootBarVisible(boolean isVisible) {
        footBar_ll.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setFootLineVisible(boolean isVisible) {
        footLine_ll.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
