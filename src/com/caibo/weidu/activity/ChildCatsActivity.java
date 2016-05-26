package com.caibo.weidu.activity;

import java.util.ArrayList;
import java.util.HashMap;
import com.caibo.weidu.R;
import com.caibo.weidu.modle.PagerSlidingTabStrip;
import com.caibo.weidu.modle.myPagerAdapter;
import com.caibo.weidu.util.SaveDataInPref;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
//	private Fragment fragment;
	private PagerSlidingTabStrip tabs;
	
	private ArrayList<HashMap<String, Object>> arrayListForChildcats;
	private String session, deviceId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_cats);
		
		ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		
		SharedPreferences pref = getSharedPreferences("registerData", MODE_PRIVATE);
		SaveDataInPref saveDataInPref = new SaveDataInPref(pref);
		session = saveDataInPref.GetSession("userData", "");
		deviceId = saveDataInPref.GetData("imei", "");
		
		try {
			childCatsTabName = (TextView) findViewById(R.id.childCats_tab_name);
			childCatsTagBack = (ImageView) findViewById(R.id.childCats_tag_back);
			
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
			
//			pager.setOffscreenPageLimit(1);
			pager.setAdapter(new myPagerAdapter(getSupportFragmentManager(), ChildCatsActivity.this, arrayListForChildcats, session, deviceId));
			pager.setCurrentItem(0);
			tabs.setViewPager(pager);
			
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
