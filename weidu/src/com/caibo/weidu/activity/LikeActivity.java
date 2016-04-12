package com.caibo.weidu.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.caibo.weidu.R;
import com.caibo.weidu.modle.Account;
import com.caibo.weidu.modle.AccountAdapter;
import com.caibo.weidu.util.InitUrls;
import com.caibo.weidu.util.MyAsyncTask;
import com.caibo.weidu.util.SaveDataInPref;
import com.caibo.weidu.util.onDataFinishedListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LikeActivity extends Activity {
	
	private String favoriteListUrl, session, deviceId, pageNum;
	private String accountName, accountWxNo, accountLogoLink, accountDesc, accountValidReason, accountId;
	private int accountScore;
	private InitUrls initUrls = new InitUrls();
	private Account account;
	private ArrayList<Account> accounts;
	private LinearLayout likeNoneLayout;
	private ListView favListView;
	private TextView likeTabName;
	
	private SwipeRefreshLayout refreshLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_like);
		
		likeNoneLayout = (LinearLayout) findViewById(R.id.likenone_layout);
		favListView = (ListView) findViewById(R.id.like_listView);
		likeTabName = (TextView) findViewById(R.id.like_tabname);
		refreshLayout = (SwipeRefreshLayout) findViewById(R.id.like_SwipeRefreshLayout);
		
		SharedPreferences pref = getSharedPreferences("registerData", MODE_PRIVATE);
		SaveDataInPref saveDataInPref = new SaveDataInPref(pref);
		session = saveDataInPref.GetSession("userData", "");
		deviceId = saveDataInPref.GetData("imei", "");
		favoriteListUrl = initUrls.InitFavoriteListUrl(session, deviceId, "1");
		
		excuteTask(favoriteListUrl);
		
		//����ˢ��
		refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
											android.R.color.holo_red_light,
											android.R.color.holo_green_light,
											android.R.color.holo_orange_light);
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				excuteTask(favoriteListUrl);
				refreshLayout.setRefreshing(false);
			}
		});
	}
	
	private void excuteTask(String url) {
		MyAsyncTask mTask = new MyAsyncTask(url);
		mTask.setOnDataFinishedListener(new onDataFinishedListener() {
			@Override
			public void onDataSuccessfully(Object data) {
				Log.i("favoriteListUrl", data.toString());
				try {
					accounts = new ArrayList<Account>();
					JSONObject jsonObject = new JSONObject(data.toString());
					JSONArray jsonFavAccounts = jsonObject.getJSONObject("data").getJSONArray("favorite_accounts");
					if (jsonFavAccounts.length() != 0) {
						for (int i = 0; i < jsonFavAccounts.length(); i++) {
							accountName = jsonFavAccounts.getJSONObject(i).getString("a_name");
							accountWxNo = jsonFavAccounts.getJSONObject(i).getString("a_wx_no");
							accountId = jsonFavAccounts.getJSONObject(i).getString("a_id");
							accountLogoLink = jsonFavAccounts.getJSONObject(i).getString("a_logo");
							accountDesc = jsonFavAccounts.getJSONObject(i).getString("a_desc");
							accountValidReason = jsonFavAccounts.getJSONObject(i).getString("a_valid_reason");
							accountScore = Integer.valueOf(jsonFavAccounts.getJSONObject(i).getString("a_rank"));
							Account account = new Account(accountName, accountWxNo, accountId, accountDesc, accountLogoLink, accountScore, accountValidReason);
							accounts.add(account);
						}
						likeNoneLayout.setVisibility(View.GONE);
						likeTabName.setText("�ղ�");
						AccountAdapter adapter = new AccountAdapter(LikeActivity.this, R.layout.childcats_account_listview, accounts);
						favListView.setAdapter(adapter);
						favListView.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
								Account account = accounts.get(arg2);
								Intent intent = new Intent(LikeActivity.this, AccountDetailActivity.class);
								intent.putExtra("a_id", account.getAccountId());
								startActivity(intent);
							}
						});
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mTask.execute("string");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.like, menu);
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
