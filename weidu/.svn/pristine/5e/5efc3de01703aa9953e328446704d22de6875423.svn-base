package com.caibo.weidu.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class okHttp {
	
	public static String getAppData(String address) {
		OkHttpClient  client = new OkHttpClient();
		DES des = new DES();
		try {
			Request request = new Request.Builder().url(address).build();
			Response response = client.newCall(request).execute();
			return des.decrypt(response.body().string());
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
			InputStream is = response.body().byteStream();
			Bitmap bm = BitmapFactory.decodeStream(is);
			
			//Í¼Æ¬Ñ¹Ëõ
//			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			
			return bm;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public static JSONArray parseDataWithGson(String Data) {
		try {
			JSONObject jsonObject = new JSONObject(Data);
			JSONArray data = jsonObject.getJSONArray("data");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
//		JSONObject weatherInfo = jsonObject.getJSONObject("data");
//		Gson gson = new Gson();
//		appJson appJsonDatas = gson.fromJson(Data, appJson.class);
//		return appJsonDatas.get_appJsonData();
	}
}
