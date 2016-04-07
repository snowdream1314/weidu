package com.caibo.weidu.activity;

import com.caibo.weidu.R;
import com.caibo.weidu.util.MyAsyncTask;
import com.caibo.weidu.util.onDataFinishedListener;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends ActivityGroup {
	
	private TabHost tabHost;
	
	private TextView tab_name;
	private int tabTag = 0;
	private String imei, registUrl;
	private String session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final SharedPreferences.Editor editor = getSharedPreferences("registerData", MODE_PRIVATE).edit();
		
		this.tabHost = (TabHost) findViewById(R.id.mytabhost);
		tabHost.setup(this.getLocalActivityManager());
		
		tabHost.addTab(tabHost.newTabSpec("公众号").setIndicator(initView("公众号", R.drawable.account_selector)).setContent(new Intent(this, AccountActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("喜欢").setIndicator(initView("喜欢", R.drawable.like_selector)).setContent(new Intent(this, LikeActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("更多").setIndicator(initView("更多", R.drawable.more_selector)).setContent(new Intent(this, MoreActivity.class)));
		
		Intent intent = getIntent();
		tabTag = intent.getIntExtra("tabTag", 0);
		tabHost.setCurrentTab(tabTag);
		
		//注册
		TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
		imei = tm.getDeviceId();
		Log.i("imei", imei);
		editor.putString("imei", imei);
		editor.commit();
		
		try {
			registUrl = "http://wx.xiyiyi.com/Mobile//UserAuth/registerUser?appcode=wxh&v=1.0&devicetype=android&deviceid=" + imei;
			Log.i("registUrl", registUrl);
			MyAsyncTask mTask = new MyAsyncTask(registUrl);
			mTask.setOnDataFinishedListener(new onDataFinishedListener() {
				@Override
				public void onDataSuccessfully(Object data) {
					editor.putString("userData", data.toString());
					editor.commit();
					session = data.toString();
					Log.i("session", session);
				}
			});
			mTask.execute("string");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private View initView(String name, int drawableId) {
		View view = View.inflate(this, R.layout.tab_layout, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.image);
		TextView textView = (TextView) view.findViewById(R.id.title);
		imageView.setImageDrawable(getResources().getDrawable(drawableId));
		textView.setText(name);
		return view;
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
