package com.caibo.weidu.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.caibo.weidu.R;
import com.caibo.weidu.modle.Account;
import com.caibo.weidu.modle.AccountFragment;
import com.caibo.weidu.modle.PagerSlidingTabStrip;
import com.caibo.weidu.modle.myPagerAdapter;
import com.caibo.weidu.util.InitUrls;
import com.caibo.weidu.util.MyAsyncTask;
import com.caibo.weidu.util.SaveDataInPref;
import com.caibo.weidu.util.onDataFinishedListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ChildCatsActivity extends FragmentActivity {
	
	private TextView childCatsTabName;
	private ImageView childCatsTagBack;
	private Fragment fragment;
	private PagerSlidingTabStrip tabs;
	
	private ArrayList<HashMap<String, Object>> arrayListForChildcats;
	private ArrayList<Fragment> fragments;
	private ArrayList<String> titles;
	private ArrayList<Account> accounts;
	private String accountsUrl, childCatsId, session, deviceId;
	private JSONArray jsonAccounts; 
	private String accountName, accountWxNo, accountLogoLink, accountDesc, accountValidReason, accountId;
	private int accountScore, pageNum;
	private String title;
	private ImageView childCatsScoreStar;
	private TextView childCatsAccountWxNo;
	private InitUrls initUrls = new InitUrls();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_cats);
		
		childCatsScoreStar = (ImageView) findViewById(R.id.childCats_score_star);
		childCatsAccountWxNo = (TextView) findViewById(R.id.childCats_account_wx_no);
		
		SharedPreferences pref = getSharedPreferences("registerData", MODE_PRIVATE);
		final SaveDataInPref saveDataInPref = new SaveDataInPref(pref);
		session = saveDataInPref.GetSession("userData", "");
		deviceId = saveDataInPref.GetData("imei", "");
		
		try {
			childCatsTabName = (TextView) findViewById(R.id.childCats_tab_name);
			childCatsTagBack = (ImageView) findViewById(R.id.childCats_tag_back);
//			ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
			
			Intent intent = getIntent();
			childCatsTabName.setText(intent.getStringExtra("category_name"));
			arrayListForChildcats = (ArrayList<HashMap<String, Object>>) intent.getSerializableExtra("childCats");
			
			childCatsTagBack.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ChildCatsActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}
			});
			
//			initAccounts();
		
//			fragments = new ArrayList<Fragment>();
			titles = new ArrayList<String>();
			for (int i = 0; i < arrayListForChildcats.size(); i++) {
//				Log.i("title", arrayListForChildcats.get(i).get("childCat_name").toString());
				title = arrayListForChildcats.get(i).get("childCat_name").toString();
				titles.add(title);
				childCatsId = arrayListForChildcats.get(i).get("childCat_id").toString();
				initAccounts(title, childCatsId);
//				Log.i("accounts", accounts.toString());
//				fragment = new AccountFragment(accounts, ChildCatsActivity.this);
//				fragments.add(fragment);
			}
			
//			pager.setAdapter(new myPagerAdapter(getSupportFragmentManager(), fragments, titles));
//			pager.setCurrentItem(0);
//			tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//			tabs.setViewPager(pager);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initAccounts(String title, final String childCatsId) {
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		fragments = new ArrayList<Fragment>();
		pageNum = 1;
		accountsUrl = initUrls.InitAccountsUrl(session, deviceId, childCatsId, Integer.toString(pageNum));
		
		try {
			MyAsyncTask mTask = new MyAsyncTask(accountsUrl);
			mTask.setOnDataFinishedListener(new onDataFinishedListener() {
				@Override
				public void onDataSuccessfully(Object data) {
					ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
					String account_data = data.toString();
					try {
//						Log.i("account_data", account_data);
						accounts = new ArrayList<Account>();
						JSONObject jsonObject = new JSONObject(account_data);
						jsonAccounts = jsonObject.getJSONObject("data").getJSONArray("accounts");
						for (int i = 0; i < jsonAccounts.length(); i++) {
							accountName = jsonAccounts.getJSONObject(i).getString("a_name");
							accountWxNo = jsonAccounts.getJSONObject(i).getString("a_wx_no");
							accountId = jsonAccounts.getJSONObject(i).getString("a_id");
							accountLogoLink = jsonAccounts.getJSONObject(i).getString("a_logo");
							accountDesc = jsonAccounts.getJSONObject(i).getString("a_desc");
							accountValidReason = jsonAccounts.getJSONObject(i).getString("a_valid_reason");
							accountScore = Integer.valueOf(jsonAccounts.getJSONObject(i).getString("a_rank"));
							Account account = new Account(accountName, accountWxNo, accountId, accountDesc, accountLogoLink, accountScore, accountValidReason);
							accounts.add(account);
							
						}
//						Log.i("i", Integer.toString(accounts.size()));
//						Log.i("accounts", accounts.toString());
						fragment = new AccountFragment(accounts, ChildCatsActivity.this, session, deviceId, childCatsId, pageNum);
						fragments.add(fragment);
						pager.setAdapter(new myPagerAdapter(getSupportFragmentManager(), fragments, titles));
						pager.setCurrentItem(0);
						tabs.setViewPager(pager);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			mTask.execute("string");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.child_cats, menu);
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
