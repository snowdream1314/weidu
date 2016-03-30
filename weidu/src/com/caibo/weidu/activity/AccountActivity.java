package com.caibo.weidu.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import com.caibo.weidu.R;
import com.caibo.weidu.modle.ListViewAdapter;
import com.caibo.weidu.util.MyAsyncTask;
import com.caibo.weidu.util.okHttp;
import com.caibo.weidu.util.onDataFinishedListener;

import android.app.Activity;
import android.graphics.Bitmap;
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
	private ArrayList<ArrayList<HashMap<String, Object>>> mArrayList, mArrayListForImage;
	private ArrayList<HashMap<String, Object>> categoryList;
	
	private String appDatas;
	private JSONArray appJsonDatas, recommendAccounts;
	private Bitmap bitmap;
//	private MyAsyncTaskForImage mTaskForImage;
	private HashMap<String, Object> accountHashMap,imageHashMap;
	private ArrayList<HashMap<String, Object>> arrayListForEveryGridView, arrayListImageForEveryGridView;
	
	private int i, j;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_account);
		
//		mListView = (ListView) findViewById(R.id.account_listView);
		String url = "http://wx.xiyiyi.com/Mobile/AccountCategory/cats"; 
//		mTask = new MyAsyncTask();
//		mTask.execute(url);
		init(url);
	}
	
	private void init(String url) {
		mListView = (ListView) findViewById(R.id.account_listView);
		mArrayList = new ArrayList<ArrayList<HashMap<String, Object>>>();
		mArrayListForImage = new ArrayList<ArrayList<HashMap<String, Object>>>();
		arrayListImageForEveryGridView = new ArrayList<HashMap<String, Object>>();
		categoryList = new ArrayList<HashMap<String, Object>>();
		accountHashMap = null;
		imageHashMap = null;
//		HashMap<String, Object> categoryHashMap = null;
//		ArrayList<HashMap<String, Object>> arrayListForEveryGridView;
		
		MyAsyncTask mTask = new MyAsyncTask(url);
		mTask.setOnDataFinishedListener(new onDataFinishedListener() {
			HashMap<String, Object> categoryHashMap = null;
			ArrayList<HashMap<String, Object>> arrayListForEveryGridView;
			ArrayList<HashMap<String, Object>> arrayListImgForEveryGridView;
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
//							MyAsyncTask mTaskForImage = new MyAsyncTask(recommendAccounts.getJSONObject(j).getString("a_logo"));
//							mTaskForImage.setOnDataFinishedListener(new onDataFinishedListener() {
//								@Override
//								public void onDataSuccessfully(Object bitmap) {
//									accountHashMap.put("account_img", bitmap);
//									Log.i("BitMap", bitmap.toString());
////									Log.i("accountHashMap", accountHashMap.toString());
////									Log.i("arrayListForEveryGridView", arrayListForEveryGridView.toString());
//								}
//							});
//							mTaskForImage.execute("bitmap");
							arrayListForEveryGridView.add(accountHashMap);
						}
						
						mArrayList.add(arrayListForEveryGridView);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
//				Log.i("mArrayList", mArrayList.toString());
//				Log.i("arrayListForEveryGridView", arrayListForEveryGridView.toString());
				try {
					mArrayListForImage = new ArrayList<ArrayList<HashMap<String, Object>>>();
//					imageHashMap = new HashMap<String, Object>();
					for (i = 0; i < mArrayList.size(); i++) {
						arrayListImageForEveryGridView = new ArrayList<HashMap<String, Object>>();
						for (j = 0; j < mArrayList.get(i).size(); j++) {
							String a_logo_link = mArrayList.get(i).get(j).get("a_logo_link").toString();
							final String a_name = mArrayList.get(i).get(j).get("account_name").toString();
//						Log.i("a_logo_link", a_logo_link);
//							imageHashMap = new HashMap<String, Object>();
							MyAsyncTask mTaskForImage = new MyAsyncTask(a_logo_link);
							mTaskForImage.setOnDataFinishedListener(new onDataFinishedListener() {
								@Override
								public void onDataSuccessfully(Object bitmap) {
									imageHashMap = new HashMap<String, Object>();
									imageHashMap.put("a_logo", bitmap);
									imageHashMap.put("a_name", a_name);
//									mArrayList.get(i).get(j).put("a_logo", bitmap);
									arrayListImageForEveryGridView.add(imageHashMap);
//									Log.i("arrayListImageForEveryGridView", arrayListImageForEveryGridView.toString());
//									mArrayListForImage.add(arrayListImageForEveryGridView);
									if (arrayListImageForEveryGridView.size() == 44) {
//										Log.i("arrayListImageForEveryGridView", arrayListImageForEveryGridView.toString());
//										Log.i("mArrayListForImage", mArrayListForImage.toString());
										mListViewAdapter = new ListViewAdapter(mArrayList, arrayListImageForEveryGridView, categoryList, AccountActivity.this);
										mListView.setAdapter(mListViewAdapter);
									}
//									Log.i("arrayListImageForEveryGridView", arrayListImageForEveryGridView.toString());
//									Log.i("count", String.valueOf(mArrayListForImage.size()));
//									Log.i("mTaskForImage-status", mTaskForImage.getStatus().toString());
								}
							});
							mTaskForImage.execute("bitmap");
						}
//						mArrayListForImage.add(arrayListImageForEveryGridView);
					}
//					Log.i("mArrayListForImage", mArrayListForImage.toString());
				} catch (Exception e) {
					Log.e("error", e.toString());
				}
//				Log.i("mArrayList", mArrayList.toString());
//				mListViewAdapter = new ListViewAdapter(mArrayList, categoryList, AccountActivity.this);
//				mListView.setAdapter(mListViewAdapter);
//				Log.i("arrayListImageForEveryGridView", arrayListImageForEveryGridView.toString());
//				Log.i("mArrayListForImage", mArrayListForImage.toString());
			}
		});
		mTask.execute("string");
//		Log.i("mArrayList", mArrayList.toString());
//		Log.i("mTask", mTask.getStatus().toString());
//		if (mTask.getStatus() == AsyncTask.Status.FINISHED) {
//		Log.i("accountHashMap", accountHashMap.toString());
//		Log.i("arrayListForEveryGridView", arrayListForEveryGridView.toString());
//		}
		
//		mTaskForImage = new MyAsyncTask();
//		mTaskForImage.execute(params)
//		mListViewAdapter = new ListViewAdapter(mArrayList, categoryList, AccountActivity.this);
//		mListView.setAdapter(mListViewAdapter);
//		Log.i("mListView.setAdapter", "done");
	}
	
	
//	private class MyAsyncTask extends AsyncTask<String, Integer, String> {
//		
//		//onPreExecute方法用于在执行后台任务前做一些UI操作  
//		
//		//doInBackground方法内部执行后台任务,不可在此方法内修改UI
//		@Override 
//		protected String doInBackground(String... address) {
////			Log.i("MyAsyncTask", "doInBackground(Params... params) called");  
//			try {
//				appDatas = okHttp.getAppData(address[0]);
////				Log.i("MyappDatas", appDatas);
//				return appDatas;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		}
//		
//		 //onProgressUpdate方法用于更新进度信息
//		
//		//onPostExecute方法用于在执行完后台任务后更新UI,显示结果
//		@Override
//		protected void onPostExecute(String result) {
//			mArrayList = new ArrayList<ArrayList<HashMap<String, Object>>>();
//			categoryList = new ArrayList<HashMap<String, Object>>();
//			accountHashMap = null;
//			HashMap<String, Object> categoryHashMap = null;
////			ArrayList<HashMap<String, Object>> arrayListForEveryGridView;
//			
//			try {
//				appJsonDatas = okHttp.parseDataWithGson(result);
//				for (int i = 0; i < appJsonDatas.length(); i++) {
//					arrayListForEveryGridView = new ArrayList<HashMap<String, Object>>();
//					
//					//分类
//					categoryHashMap = new HashMap<String, Object>();
//					categoryHashMap.put("category_name", appJsonDatas.getJSONObject(i).getString("ac_name"));
//					categoryList.add(categoryHashMap);
//					
//					//分类下的公众号
//					recommendAccounts = appJsonDatas.getJSONObject(i).getJSONArray("recommendAccount");
//					for (int j = 0; j < recommendAccounts.length(); j++) {
//						accountHashMap = new HashMap<String, Object>();
//						accountHashMap.put("account_name", recommendAccounts.getJSONObject(j).getString("a_name"));
//						
//						mTaskForImage = new MyAsyncTaskForImage(recommendAccounts.getJSONObject(j).getString("a_logo"));
//						mTaskForImage.setOnDataFinishedListener(new onDataFinishedListener() {
//							@Override
//							public void onDataSuccessfully(Bitmap bitmap) {
//								accountHashMap.put("account_img", bitmap);
//								Log.i("BitMap", bitmap.toString());
//								arrayListForEveryGridView.add(accountHashMap);
////								Log.i("accountHashMap", accountHashMap.toString());
////								Log.i("arrayListForEveryGridView", arrayListForEveryGridView.toString());
//							}
//						});
//						mTaskForImage.execute();
//						
//					}
//					mArrayList.add(arrayListForEveryGridView);
//					Log.i("mArrayList", mArrayList.toString());
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
////			mTaskForImage.execute();
//			mListViewAdapter = new ListViewAdapter(mArrayList, categoryList, AccountActivity.this);
//			mListView.setAdapter(mListViewAdapter);
//		}
//	}
	
	
//	private class MyAsyncTaskForImage extends AsyncTask<String, Integer, Bitmap> {
//		String url;
//		onDataFinishedListener onDataFinishedListener;
//		
//		public MyAsyncTaskForImage(String address) {
//			this.url = address;
//		}
//		
//		public void setOnDataFinishedListener(onDataFinishedListener onDataFinishedListener) {
//			this.onDataFinishedListener = onDataFinishedListener;
//		}
//		//onPreExecute方法用于在执行后台任务前做一些UI操作  
//		
//		//doInBackground方法内部执行后台任务,不可在此方法内修改UI
//		@Override 
//		protected Bitmap doInBackground(String... address) {
////			Log.i("MyAsyncTask", "doInBackground(Params... params) called");  
//			try {
//				bitmap = okHttp.getBitmap(url);
////				Log.i("bitmap", bitmap.toString());
//				return bitmap;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		}
//		
//		 //onProgressUpdate方法用于更新进度信息
//		
//		//onPostExecute方法用于在执行完后台任务后更新UI,显示结果
//		@Override
//		protected void onPostExecute(Bitmap bitmap) {
//			if (bitmap != null) {
////				mArrayListForImg = new ArrayList<ArrayList<HashMap<String, Object>>>();
////				HashMap<String, Object> imageHashMap = new HashMap<String, Object>();
////				ArrayList<HashMap<String, Object>> arrayListForEveryGridView = new ArrayList<HashMap<String, Object>>();
////				imageHashMap.put("account_img", bitmap);
////				arrayListForEveryGridView.add(imageHashMap);
////				mArrayListForImg.add(arrayListForEveryGridView);
////				mListViewAdapter = new ListViewAdapter(mArrayList, mArrayListForImg, categoryList, AccountActivity.this);
////				mListView.setAdapter(mListViewAdapter);
//				onDataFinishedListener.onDataSuccessfully(bitmap);
//			}
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
