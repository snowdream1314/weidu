package com.caibo.weidu.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.caibo.weidu.R;
import com.caibo.weidu.modle.Account;
import com.caibo.weidu.modle.AccountFragment;
import com.caibo.weidu.modle.PagerSlidingTabStrip;
import com.caibo.weidu.modle.myPagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_cats);
		
		try {
			childCatsTabName = (TextView) findViewById(R.id.childCats_tab_name);
			childCatsTagBack = (ImageView) findViewById(R.id.childCats_tag_back);
			ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
			
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
			
			initAccounts();
		
			fragments = new ArrayList<Fragment>();
			titles = new ArrayList<String>();
			for (int i = 0; i < arrayListForChildcats.size(); i++) {
//				Log.i("title", arrayListForChildcats.get(i).get("childCat_name").toString());
				titles.add(arrayListForChildcats.get(i).get("childCat_name").toString());
				fragment = new AccountFragment(accounts, ChildCatsActivity.this);
				fragments.add(fragment);
			}
			
			pager.setAdapter(new myPagerAdapter(getSupportFragmentManager(), fragments, titles));
			pager.setCurrentItem(0);
			tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
			tabs.setViewPager(pager);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initAccounts() {
		accounts = new ArrayList<Account>();
//		Account apple = new Account("苹果", "apple", "这是一个苹果", R.drawable.account_default, R.drawable.three_stars);
//		accounts.add(apple);
//		
//		Account bn = new Account("香蕉", "apple", "这是一个香蕉", R.drawable.wx_icon, R.drawable.three_stars);
//		accounts.add(bn);
//		
//		Account bn2 = new Account("菠萝", "apple", "这是一个菠萝", R.drawable.account_default, R.drawable.three_stars);
//		accounts.add(bn2);
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
