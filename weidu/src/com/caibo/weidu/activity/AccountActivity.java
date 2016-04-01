package com.caibo.weidu.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import com.caibo.weidu.AccountDetailActivity;
import com.caibo.weidu.R;
import com.caibo.weidu.modle.ListViewAdapter;
import com.caibo.weidu.modle.NoScrollGridView;
import com.caibo.weidu.util.MyAsyncTask;
import com.caibo.weidu.util.okHttp;
import com.caibo.weidu.util.onDataFinishedListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

public class AccountActivity extends Activity {
	
	private ListView mListView;
	private NoScrollGridView gridView;
	private ListViewAdapter mListViewAdapter;
	private ArrayList<ArrayList<HashMap<String, Object>>> mArrayList, mArrayListForImage;
	private ArrayList<HashMap<String, Object>> categoryList;
	
	private JSONArray appJsonDatas, recommendAccounts;
//	private MyAsyncTaskForImage mTaskForImage;
	private HashMap<String, Object> accountHashMap,imageHashMap;
	private ArrayList<HashMap<String, Object>> arrayListImageForEveryGridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_account);
		
		String url = "http://wx.xiyiyi.com/Mobile/AccountCategory/cats"; 
		init(url);
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(AccountActivity.this).build();
		ImageLoader.getInstance().init(config);
		
		View convertView = LayoutInflater.from(this).inflate(R.layout.account_listview, null, false);
		gridView = (NoScrollGridView) convertView.findViewById(R.id.account_gridView);
//		gridView = (GridView) findViewById(R.id.account_listView).findViewById(R.id.account_gridView);
		
		try {
			gridView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					Intent intent = new Intent(AccountActivity.this, AccountDetailActivity.class);
					startActivity(intent);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("gridView", e.toString());
		}
	}
	
	private void init(String url) {
		mListView = (ListView) findViewById(R.id.account_listView);
		mArrayList = new ArrayList<ArrayList<HashMap<String, Object>>>();
		arrayListImageForEveryGridView = new ArrayList<HashMap<String, Object>>();
		categoryList = new ArrayList<HashMap<String, Object>>();
		accountHashMap = null;
		imageHashMap = null;
		
		MyAsyncTask mTask = new MyAsyncTask(url);
		mTask.setOnDataFinishedListener(new onDataFinishedListener() {
			HashMap<String, Object> categoryHashMap = null;
			ArrayList<HashMap<String, Object>> arrayListForEveryGridView;
			@Override
			public void onDataSuccessfully(Object data) {
				try {
					appJsonDatas = okHttp.parseDataWithGson(data.toString());
					for (int i = 0; i < appJsonDatas.length(); i++) {
						arrayListForEveryGridView = new ArrayList<HashMap<String, Object>>();
						
						//分类
						categoryHashMap = new HashMap<String, Object>();
						categoryHashMap.put("category_name", appJsonDatas.getJSONObject(i).getString("ac_name"));
						categoryList.add(categoryHashMap);
						
						//分类下的公众号
						recommendAccounts = appJsonDatas.getJSONObject(i).getJSONArray("recommendAccount");
						for (int j = 0; j < recommendAccounts.length(); j++) {
							accountHashMap = new HashMap<String, Object>();
							accountHashMap.put("account_name", recommendAccounts.getJSONObject(j).getString("a_name"));
							accountHashMap.put("a_logo_link", recommendAccounts.getJSONObject(j).getString("a_logo"));
							arrayListForEveryGridView.add(accountHashMap);
						}
						mArrayList.add(arrayListForEveryGridView);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				mListViewAdapter = new ListViewAdapter(mArrayList, arrayListImageForEveryGridView, categoryList, AccountActivity.this);
				mListView.setAdapter(mListViewAdapter);
			}
		});
		mTask.execute("string");
	}
	
	//屏幕旋转
//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig);
//		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//			setContentView(R.layout.activity_account);
//		}
//	}
	
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
