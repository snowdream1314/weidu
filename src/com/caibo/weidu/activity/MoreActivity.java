package com.caibo.weidu.activity;

import java.util.ArrayList;

import com.caibo.weidu.MoreWebContentActivity;
import com.caibo.weidu.R;
import com.caibo.weidu.modle.MoreItem;
import com.caibo.weidu.modle.MoreItemAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MoreActivity extends Activity {
	
	private ListView moreListView;
	private ArrayList<Message> moreItemList;
	private String itemName;
	private final int text = 0;
	private final int img_text = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_more);
		
		try {
			initItems();
			moreListView = (ListView) findViewById(R.id.more_listView);
			MoreItemAdapter adapter = new MoreItemAdapter(MoreActivity.this, moreItemList);
			moreListView.setAdapter(adapter);
			
			moreListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					if (moreItemList.get(arg2).what == img_text) {
						Message msg = moreItemList.get(arg2);
						itemName = ((MoreItem) (msg.obj)).getItemName();
						if (itemName.equals("免费收录")) {
							Intent intent = new Intent(MoreActivity.this, FreeCollectActivity.class);
							intent.putExtra("url", "http://wx.xiyiyi.com/Mobile/Account/submit");
							startActivity(intent);
						}
						else if (itemName.equals("关于我们")) {
							Intent intent = new Intent(MoreActivity.this, AboutUsActivity.class);
							intent.putExtra("url", "http://wx.xiyiyi.com/Mobile/About/aboutus");
							startActivity(intent);
						}
						else if (itemName.equals("商务合作")) {
							Intent intent = new Intent(MoreActivity.this, BusinessCooperationActivity.class);
							intent.putExtra("url", "http://wx.xiyiyi.com/Mobile/About/business");
							startActivity(intent);
						}
//						else if (itemName.equals("给我点爱")) {
//							Intent intent = new Intent(MoreActivity.this, DonationActivity.class);
//							startActivity(intent);
//						}
						else if (itemName.equals("意见反馈")) {
							Intent intent = new Intent(MoreActivity.this, FeedBackActivity.class);
							intent.putExtra("url", "http://wx.xiyiyi.com/Mobile/Feedback/index");
							startActivity(intent);
						}
					}
//					else {
//						if (moreItemList.get(arg2).obj.toString().equals("关于")) {
//							Intent intent = new Intent(MoreActivity.this, AboutActivity.class);
//							startActivity(intent);
//						}
//					}
				}
			});
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void initItems() {
			moreItemList = new ArrayList<Message>();
			
			Message tool = new Message();
			tool.obj = "工具";
			tool.what = text;
			moreItemList.add(tool);
			
			MoreItem free_collect = new MoreItem("免费收录", R.drawable.free_collect, R.drawable.tag_more);
			Message collect = new Message();
			collect.obj = free_collect;
			collect.what = img_text;
			moreItemList.add(collect);
			
			Message about = new Message();
			about.obj = "关于";
			about.what = text;
			moreItemList.add(about);
			
			MoreItem about_us = new MoreItem("关于我们", R.drawable.about_us, R.drawable.tag_more);
			Message us = new Message();
			us.obj = about_us;
			us.what = img_text;
			moreItemList.add(us);
			
			MoreItem business_cooperation = new MoreItem("商务合作", R.drawable.business_cooperation, R.drawable.tag_more);
			Message cooperation = new Message();
			cooperation.obj = business_cooperation;
			cooperation.what = img_text;
			moreItemList.add(cooperation);
			
			MoreItem donation = new MoreItem("给我点爱", R.drawable.donation, R.drawable.tag_more);
			Message contribute = new Message();
			contribute.obj = donation;
			contribute.what = img_text;
			moreItemList.add(contribute);
			
			MoreItem feed_back = new MoreItem("意见反馈", R.drawable.feed_back, R.drawable.tag_more);
			Message suggest = new Message();
			suggest.obj = feed_back;
			suggest.what = img_text;
			moreItemList.add(suggest);
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
