package com.caibo.weidu.activity;

import com.caibo.weidu.R;
import com.caibo.weidu.R.drawable;
import com.caibo.weidu.R.id;
import com.caibo.weidu.R.layout;
import com.caibo.weidu.R.menu;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class MainActivity extends ActivityGroup {
	
	private TabHost tabHost;
	
	private TextView tab_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tab_name = (TextView) findViewById(R.id.tab_name);
		this.tabHost = (TabHost) findViewById(R.id.mytabhost);
		tabHost.setup(this.getLocalActivityManager());
		
		tabHost.addTab(tabHost.newTabSpec("公众号").setIndicator(initView("公众号", R.drawable.account_selector)).setContent(new Intent(this, AccountActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("喜欢").setIndicator(initView("喜欢", R.drawable.like_selector)).setContent(new Intent(this, LikeActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("更多").setIndicator(initView("更多", R.drawable.more_selector)).setContent(new Intent(this, MoreActivity.class)));
		tabHost.setCurrentTab(0);
		
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
