package com.dm.ycm.wechatmoments.view.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dm.ycm.wechatmoments.R;
import com.dm.ycm.wechatmoments.common.utils.DensityUtil;
import com.dm.ycm.wechatmoments.common.utils.ImageLoader;
import com.dm.ycm.wechatmoments.presenter.bean.ImagesBean;

import java.util.List;

/**
 * Created by ycm on 2017/6/7.
 * Description:九宫格显示图片
 * Modified by:
 */
public class ImageGridView extends LinearLayout {
    public static int MAX_WIDTH = 0;
    // 照片的Url列表
    private List<ImagesBean> imageEntryList;
//    private int pxOneMaxWandH;  // 单张图最大允许宽高
    private int pxMoreWandH = 100;// 多张图的宽高
    private int pxImagePadding = DensityUtil.dip2px(getContext(), 3);// 图片间的间距
    private int MAX_PER_ROW_COUNT = 3;// 每行显示最大数

//    private LinearLayout.LayoutParams onePicPara;
    private LinearLayout.LayoutParams morePara;
    private LinearLayout.LayoutParams moreParaColumnFirst;
    private LinearLayout.LayoutParams singlePara;
    private LinearLayout.LayoutParams rowPara;
    public ImageGridView(Context context) {
        super(context);
    }

    public ImageGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
    }

    public void setList(List<ImagesBean> lists) throws IllegalArgumentException{
        if(lists == null){
            throw new IllegalArgumentException("imageList is null...");
        }
        imageEntryList = lists;

        if(MAX_WIDTH > 0){
            pxMoreWandH = (MAX_WIDTH - pxImagePadding*2 )/3; //解决右侧图片和内容对不齐问题
//            pxOneMaxWandH = MAX_WIDTH * 2 / 3;
            initImageLayoutParams();
        }
        initView();
    }

    private void initImageLayoutParams() {
        int wrap = LinearLayout.LayoutParams.WRAP_CONTENT;
        int match = LinearLayout.LayoutParams.MATCH_PARENT;
//        onePicPara = new LinearLayout.LayoutParams(wrap, wrap);
        moreParaColumnFirst = new LinearLayout.LayoutParams(pxMoreWandH, pxMoreWandH);
        morePara = new LinearLayout.LayoutParams(pxMoreWandH, pxMoreWandH);
        morePara.setMargins(pxImagePadding, 0, 0, 0);
        singlePara = new LinearLayout.LayoutParams(500, wrap);
        rowPara = new LinearLayout.LayoutParams(match, wrap);
    }

    // 根据imageView的数量初始化不同的View布局,还要为每一个View作点击效果
    private void initView() {
        this.setOrientation(VERTICAL);
        this.removeAllViews();
        if(MAX_WIDTH == 0){
            //为了触发onMeasure()来测量MultiImageView的最大宽度，MultiImageView的宽设置为match_parent
            addView(new View(getContext()));
            return;
        }

        if (imageEntryList == null || imageEntryList.size() == 0) {
            return;
        }

        if (imageEntryList.size() == 1) {
            addView(createImageView(0, false));
        } else {
            int allCount = imageEntryList.size();
            if(allCount == 4){
                MAX_PER_ROW_COUNT = 2;
            }else{
                MAX_PER_ROW_COUNT = 3;
            }
            int rowCount = allCount / MAX_PER_ROW_COUNT
                    + (allCount % MAX_PER_ROW_COUNT > 0 ? 1 : 0);// 行数
            for (int rowCursor = 0; rowCursor < rowCount; rowCursor++) {
                LinearLayout rowLayout = new LinearLayout(getContext());
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setLayoutParams(rowPara);
                if (rowCursor != 0) {
                    rowLayout.setPadding(0, pxImagePadding, 0, 0);
                }

                int columnCount = allCount % MAX_PER_ROW_COUNT == 0 ? MAX_PER_ROW_COUNT
                        : allCount % MAX_PER_ROW_COUNT;//每行的列数
                if (rowCursor != rowCount - 1) {
                    columnCount = MAX_PER_ROW_COUNT;
                }
                addView(rowLayout);

                int rowOffset = rowCursor * MAX_PER_ROW_COUNT;// 行偏移
                for (int columnCursor = 0; columnCursor < columnCount; columnCursor++) {
                    int position = columnCursor + rowOffset;
                    rowLayout.addView(createImageView(position, true));
                }
            }
        }
    }

    private ImageView createImageView(int position, final boolean isMultiImage) {
        ImagesBean photoInfo = imageEntryList.get(position);
        ImageView imageView = new ImageView(getContext());
        if(isMultiImage){
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(position % MAX_PER_ROW_COUNT == 0 ? moreParaColumnFirst : morePara);
        }else {
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(singlePara);
        }

        imageView.setId(photoInfo.getUrl().hashCode());

        Log.d("wechatmoment", "gridview  :url    " + photoInfo.getUrl());
        ImageLoader.getInstance().crateBuilder()
                .load(photoInfo.getUrl())
                .preLoad(360, 360)
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .centerCrop()
                .into(imageView)
                .build();
        return imageView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(MAX_WIDTH == 0){
            int width = measureWidth(widthMeasureSpec);
            if(width>0){
                MAX_WIDTH = width;
                if(imageEntryList!=null && imageEntryList.size()>0){
                    setList(imageEntryList);
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec
     *            A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            // result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
            // + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
}
