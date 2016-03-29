package com.caibo.weidu.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.caibo.weidu.R;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

public class MyAsyncTask extends AsyncTask<String, Integer, String> {
	
	private String appDatas;
	private JSONArray appJsonDatas, recommendAccounts;
	private ArrayList<ArrayList<HashMap<String, Object>>> mArrayList;
	
	
	//onPreExecute����������ִ�к�̨����ǰ��һЩUI����  
	@Override
	protected void onPreExecute() {
		
	}
	
	//doInBackground�����ڲ�ִ�к�̨����,�����ڴ˷������޸�UI
	@Override 
	protected String doInBackground(String... address) {
//		Log.i("MyAsyncTask", "doInBackground(Params... params) called");  
		try {
			appDatas = okHttp.getAppData(address[0]);
//			Log.i("MyappDatas", appDatas);
			return appDatas;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 //onProgressUpdate�������ڸ��½�����Ϣ
	@Override
	protected void onProgressUpdate(Integer... progress) {
		
	}
	
	//onPostExecute����������ִ�����̨��������UI,��ʾ���
	@Override
	protected void onPostExecute(String result) {
		mArrayList = new ArrayList<ArrayList<HashMap<String, Object>>>();
		HashMap<String, Object> hashMap = null;
		ArrayList<HashMap<String, Object>> arrayListForEveryGridView;
		
		try {
			appJsonDatas = okHttp.parseDataWithGson(result);
			Log.i("MyappDatas", appJsonDatas.toString());
			for (int i = 0; i < appJsonDatas.length(); i++) {
				arrayListForEveryGridView = new ArrayList<HashMap<String, Object>>();
				recommendAccounts = appJsonDatas.getJSONObject(i).getJSONArray("recommendAccount");
				for (int j = 0; j < recommendAccounts.length(); j++) {
					JSONObject jsonObject = (JSONObject) recommendAccounts.get(j);
					hashMap = new HashMap<String, Object>();
					hashMap.put("account_name", "i="+i+",j="+j);
					arrayListForEveryGridView.add(hashMap);
				}
				mArrayList.add(arrayListForEveryGridView);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
