package com.dm.ycm.wechatmoments.model.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ycm on 2017/6/8.
 * Description: 图片下载api
 * Modified by:
 */

public interface ImageApi {
    /**
     * 下载图片
     * @param fileUrl url
     * @return 字节流
     */
    @GET
    Observable<ResponseBody> downloadPicFromNet(@Url String fileUrl);
}
