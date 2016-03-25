package com.caibo.weidu.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.caibo.weidu.R;
import com.caibo.weidu.modle.ListViewAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

public class AccountActivity extends Activity {
	
	private ListView mListView;
	private ListViewAdapter mListViewAdapter;
	private ArrayList<ArrayList<HashMap<String, Object>>> mArrayList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_account);
		init();
//		
//		Account recommend = new Account("热门推荐", "account_1", R.drawable.account_image);
//		Account emotion = new Account("情感天地", "account_2", R.drawable.account_image);
//		Account message = new Account("资讯阅读", "account_3", R.drawable.account_image);
//		accountList.add(recommend);
//		accountList.add(emotion);
//		accountList.add(message);
//		AccountAdapter adapter = new AccountAdapter(AccountActivity.this, R.layout.modle_account, accountList);
//		ListView listView = (ListView) findViewById(R.id.account_listView);
//		listView.setAdapter(adapter);
	}
	
	private void init() {
		mListView = (ListView) findViewById(R.id.account_listView);
		initData();
		mListViewAdapter = new ListViewAdapter(mArrayList, AccountActivity.this);
		mListView.setAdapter(mListViewAdapter);
	}
	
	private void initData() {
		mArrayList = new ArrayList<ArrayList<HashMap<String, Object>>>();
		HashMap<String, Object> hashMap = null;
		ArrayList<HashMap<String, Object>> arrayListForEveryGridView;
		
		for (int i = 0; i < 3; i++) {
			arrayListForEveryGridView = new ArrayList<HashMap<String, Object>>();
			if (i == 0) {
				for (int j = 0; j < 8; j++) {
					hashMap = new HashMap<String, Object>();
					hashMap.put("account_name", "i="+i+",j="+j);
					arrayListForEveryGridView.add(hashMap);
				}
			}
			else {
				for (int j = 0; j < 4; j++) {
					hashMap = new HashMap<String, Object>();
					hashMap.put("account_name", "i="+i+",j="+j);
					arrayListForEveryGridView.add(hashMap);
				}
			}
			mArrayList.add(arrayListForEveryGridView);
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
