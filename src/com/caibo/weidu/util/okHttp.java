package com.caibo.weidu.util;

import android.graphics.Bitmap;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class okHttp {
	
	public static String getAppData(String address) {
		OkHttpClient  client = new OkHttpClient();
		try {
			Request request = new Request.Builder().url(address).build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public static Bitmap getBitmap(String address) {
		OkHttpClient  client = new OkHttpClient();
		
		try {
			Request request = new Request.Builder().url(address).build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
}
