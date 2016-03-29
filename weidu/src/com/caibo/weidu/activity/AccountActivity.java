package com.caibo.weidu.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.caibo.weidu.R;
import com.caibo.weidu.modle.ListViewAdapter;
import com.caibo.weidu.util.MyAsyncTask;
import com.caibo.weidu.util.okHttp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

public class AccountActivity extends Activity {
	
	private ListView mListView;
	private ListViewAdapter mListViewAdapter;
	private ArrayList<ArrayList<HashMap<String, Object>>> mArrayList;
	private ArrayList<HashMap<String, Object>> categoryList;
	
	private String appDatas;
	private JSONArray appJsonDatas, recommendAccounts;
	
	private MyAsyncTask mTask, mTaskForImage;
//	private List<appJsonData> appJsonDatas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_account);
		
		mListView = (ListView) findViewById(R.id.account_listView);
		String url = "http://wx.xiyiyi.com/Mobile/AccountCategory/cats"; 
		mTask = new MyAsyncTask();
		mTask.execute(url);
//		init();
	}
	
	private void init() {
		mListView = (ListView) findViewById(R.id.account_listView);
		String url = "http://wx.xiyiyi.com/Mobile/AccountCategory/cats"; 
		mTask = new MyAsyncTask();
		mTask.execute(url);
//		mTaskForImage = new MyAsyncTask();
//		mTaskForImage.execute(params)
		mListViewAdapter = new ListViewAdapter(mArrayList, categoryList, AccountActivity.this);
		mListView.setAdapter(mListViewAdapter);
	}
	
	private class MyAsyncTask extends AsyncTask<String, Integer, String> {
		
		//onPreExecute����������ִ�к�̨����ǰ��һЩUI����  
		
		//doInBackground�����ڲ�ִ�к�̨����,�����ڴ˷������޸�UI
		@Override 
		protected String doInBackground(String... address) {
//			Log.i("MyAsyncTask", "doInBackground(Params... params) called");  
			try {
				appDatas = okHttp.getAppData(address[0]);
//				Log.i("MyappDatas", appDatas);
				return appDatas;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		 //onProgressUpdate�������ڸ��½�����Ϣ
		
		//onPostExecute����������ִ�����̨��������UI,��ʾ���
		@Override
		protected void onPostExecute(String result) {
			mArrayList = new ArrayList<ArrayList<HashMap<String, Object>>>();
			categoryList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> accountHashMap = null;
			HashMap<String, Object> categoryHashMap = null;
			ArrayList<HashMap<String, Object>> arrayListForEveryGridView;
			
			try {
				appJsonDatas = okHttp.parseDataWithGson(result);
				for (int i = 0; i < appJsonDatas.length(); i++) {
					arrayListForEveryGridView = new ArrayList<HashMap<String, Object>>();
					
					//����
					categoryHashMap = new HashMap<String, Object>();
					categoryHashMap.put("category_name", appJsonDatas.getJSONObject(i).getString("ac_name"));
					categoryList.add(categoryHashMap);
					
					//�����µĹ��ں�
					recommendAccounts = appJsonDatas.getJSONObject(i).getJSONArray("recommendAccount");
					for (int j = 0; j < recommendAccounts.length(); j++) {
						accountHashMap = new HashMap<String, Object>();
						accountHashMap.put("account_name", recommendAccounts.getJSONObject(j).getString("a_name"));
						arrayListForEveryGridView.add(accountHashMap);
					}
					mArrayList.add(arrayListForEveryGridView);
//					Log.i("mArrayList", mArrayList.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			mListViewAdapter = new ListViewAdapter(mArrayList, categoryList, AccountActivity.this);
			mListView.setAdapter(mListViewAdapter);
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
