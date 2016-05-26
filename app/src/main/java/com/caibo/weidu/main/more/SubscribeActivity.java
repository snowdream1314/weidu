package com.caibo.weidu.main.more;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.caibo.weidu.R;

public class SubscribeActivity extends Activity {
	
	private ImageView openWx, contacts, addFriends, subscribe, subscribeSelectedImg, subscribeTagBack;
	private TextView openWxText, contactsText, addFriendsText, subscribeText, subscribeTip;
	private Button subscribeOpenWx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscribe);
		
		openWx = (ImageView) findViewById(R.id.open_wx_icon);
		contacts = (ImageView) findViewById(R.id.contacts_icon);
		addFriends = (ImageView) findViewById(R.id.add_friends_icon);
		subscribe = (ImageView) findViewById(R.id.subscribe_icon);
		subscribeSelectedImg = (ImageView) findViewById(R.id.subscribe_selectedImg);
		subscribeTagBack = (ImageView) findViewById(R.id.subscribe_tag_back);
		openWxText = (TextView) findViewById(R.id.openwx_text);
		contactsText = (TextView) findViewById(R.id.contacts_text);
		addFriendsText = (TextView) findViewById(R.id.addfriends_text);
		subscribeText = (TextView) findViewById(R.id.subscribe_text);
		subscribeTip = (TextView) findViewById(R.id.subscribe_tip);
		subscribeOpenWx = (Button) findViewById(R.id.subscribe_openwx);
		
		Intent intent = getIntent();
		if (intent.getStringExtra("account_wx_name") != null) {
			subscribeTip.setText("* ���ںţ�" + intent.getStringExtra("account_wx_name") + " �Ѿ����Ƶ�������");
		}
		else {
			subscribeTip.setVisibility(View.GONE);
		}
		subscribeTagBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SubscribeActivity.this.finish();
			}
		});
		
		//�����΢��
		subscribeOpenWx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
				intent.setAction(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setComponent(cmp);
				startActivityForResult(intent, 0);
			}
		});
		
		openWx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openWx.setImageResource(R.drawable.openwx_select);
				openWxText.setTextColor(Color.parseColor("#64de4c"));
				contacts.setImageResource(R.drawable.contacts_icon);
				contactsText.setTextColor(Color.parseColor("#515151"));
				addFriends.setImageResource(R.drawable.add_friends_icon);
				addFriendsText.setTextColor(Color.parseColor("#515151"));
				subscribe.setImageResource(R.drawable.subscribe_icon);
				subscribeText.setTextColor(Color.parseColor("#515151"));
				subscribeSelectedImg.setImageResource(R.drawable.open_wx);
			}
		});
		
		contacts.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				contacts.setImageResource(R.drawable.contacts_select);
				contactsText.setTextColor(Color.parseColor("#64de4c"));
				openWx.setImageResource(R.drawable.open_wx_icon);
				openWxText.setTextColor(Color.parseColor("#515151"));
				addFriends.setImageResource(R.drawable.add_friends_icon);
				addFriendsText.setTextColor(Color.parseColor("#515151"));
				subscribe.setImageResource(R.drawable.subscribe_icon);
				subscribeText.setTextColor(Color.parseColor("#515151"));
				subscribeSelectedImg.setImageResource(R.drawable.contacts);
			}
		});
		
		addFriends.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addFriends.setImageResource(R.drawable.add_friends_select);
				addFriendsText.setTextColor(Color.parseColor("#64de4c"));
				contacts.setImageResource(R.drawable.contacts_icon);
				contactsText.setTextColor(Color.parseColor("#515151"));
				openWx.setImageResource(R.drawable.open_wx_icon);
				openWxText.setTextColor(Color.parseColor("#515151"));
				subscribe.setImageResource(R.drawable.subscribe_icon);
				subscribeText.setTextColor(Color.parseColor("#515151"));
				subscribeSelectedImg.setImageResource(R.drawable.add_friends);
			}
		});
		
		subscribe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				subscribe.setImageResource(R.drawable.subscribe_icon_select);
				subscribeText.setTextColor(Color.parseColor("#64de4c"));
				addFriends.setImageResource(R.drawable.add_friends_icon);
				addFriendsText.setTextColor(Color.parseColor("#515151"));
				contacts.setImageResource(R.drawable.contacts_icon);
				contactsText.setTextColor(Color.parseColor("#515151"));
				openWx.setImageResource(R.drawable.open_wx_icon);
				openWxText.setTextColor(Color.parseColor("#515151"));
				subscribeSelectedImg.setImageResource(R.drawable.subscribe);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subscribe, menu);
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
