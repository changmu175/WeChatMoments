package com.dm.ycm.wechatmoments;

import android.app.Application;
import android.content.Context;
/**
 * Created by ycm on 2017/6/7.
 * Description:
 * Modified by:
 */
public class MyApplication extends Application {
	private static Context mContext;
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
	}

	public static Context getContext(){
		return mContext;
	}

}
