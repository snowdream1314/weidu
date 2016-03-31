package com.caibo.weidu.activity;

import java.util.ArrayList;
import java.util.List;

import com.caibo.weidu.R;
import com.caibo.weidu.modle.MoreItem;
import com.caibo.weidu.modle.MoreItemAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

public class MoreActivity extends Activity {
	
	private ListView moreListView;
	private ArrayList<Message> moreItemList;
//	private HashMap<String, Object> map;
//	private ArrayList<HashMap<String, Object>> mapList;
	private final int text = 0;
	private final int img_text = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_more);
		
		initItems();
		Log.i("initItems", "ok");
		moreListView = (ListView) findViewById(R.id.more_listView);
		
		MoreItemAdapter adapter = new MoreItemAdapter(MoreActivity.this, moreItemList);
		Log.i("MoreItemAdapter", "ok");
//		MoreItemAdapter adapter = new MoreItemAdapter(MoreActivity.this, mapList);
		
		try {
			moreListView.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void initItems() {
		try {
			moreItemList = new ArrayList<Message>();
			
			Message tool = new Message();
			tool.obj = "����";
			tool.what = text;
			moreItemList.add(tool);
			
			MoreItem free_collect = new MoreItem("�����¼", R.drawable.free_collect, R.drawable.tag_more);
			Message collect = new Message();
			collect.obj = free_collect;
			collect.what = img_text;
			moreItemList.add(collect);
			
			Message about = new Message();
			about.obj = "����";
			about.what = text;
			moreItemList.add(about);
			
			MoreItem about_us = new MoreItem("��������", R.drawable.about_us, R.drawable.tag_more);
			Message us = new Message();
			us.obj = about_us;
			us.what = img_text;
			moreItemList.add(us);
			
			MoreItem business_cooperation = new MoreItem("�������", R.drawable.business_cooperation, R.drawable.tag_more);
			Message cooperation = new Message();
			cooperation.obj = business_cooperation;
			cooperation.what = img_text;
			moreItemList.add(cooperation);
			
			MoreItem donation = new MoreItem("���ҵ㰮", R.drawable.donation, R.drawable.tag_more);
			Message contribute = new Message();
			contribute.obj = donation;
			contribute.what = img_text;
			moreItemList.add(contribute);
			
			MoreItem feed_back = new MoreItem("�������", R.drawable.feed_back, R.drawable.tag_more);
			Message suggest = new Message();
			suggest.obj = feed_back;
			suggest.what = img_text;
			moreItemList.add(suggest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.more, menu);
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
