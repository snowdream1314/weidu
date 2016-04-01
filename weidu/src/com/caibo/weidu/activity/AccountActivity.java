package com.caibo.weidu.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import com.caibo.weidu.R;
import com.caibo.weidu.modle.ListViewAdapter;
import com.caibo.weidu.util.MyAsyncTask;
import com.caibo.weidu.util.okHttp;
import com.caibo.weidu.util.onDataFinishedListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

public class AccountActivity extends Activity {
	
	private ListView mListView;
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
		
//		mListView = (ListView) findViewById(R.id.account_listView);
		String url = "http://wx.xiyiyi.com/Mobile/AccountCategory/cats"; 
		init(url);
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(AccountActivity.this).build();
		ImageLoader.getInstance().init(config);
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
//				Log.i("mArrayList", mArrayList.toString());
				
				//下载完所有图片后一次性加载，等待时间过长
//				try {
//					mArrayListForImage = new ArrayList<ArrayList<HashMap<String, Object>>>();
//					for (int i = 0; i < mArrayList.size(); i++) {
//						arrayListImageForEveryGridView = new ArrayList<HashMap<String, Object>>();
//						for (int j = 0; j < mArrayList.get(i).size(); j++) {
//							String a_logo_link = mArrayList.get(i).get(j).get("a_logo_link").toString();
//							final String a_name = mArrayList.get(i).get(j).get("account_name").toString();
//							MyAsyncTask mTaskForImage = new MyAsyncTask(a_logo_link);
//							mTaskForImage.setOnDataFinishedListener(new onDataFinishedListener() {
//								@Override
//								public void onDataSuccessfully(Object bitmap) {
//									imageHashMap = new HashMap<String, Object>();
//									imageHashMap.put("a_logo", bitmap);
//									imageHashMap.put("a_name", a_name);
//									arrayListImageForEveryGridView.add(imageHashMap);
//									if (arrayListImageForEveryGridView.size() == 44) {
//										mListViewAdapter = new ListViewAdapter(mArrayList, arrayListImageForEveryGridView, categoryList, AccountActivity.this);
//										mListView.setAdapter(mListViewAdapter);
//									}
//								}
//							});
//							mTaskForImage.execute("bitmap");
//						}
//					}
//				} catch (Exception e) {
//					Log.e("error", e.toString());
//				}
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
