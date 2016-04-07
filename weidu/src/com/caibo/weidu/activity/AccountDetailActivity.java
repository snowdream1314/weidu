package com.caibo.weidu.activity;

import com.caibo.weidu.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountDetailActivity extends Activity {
	
	private ImageView accountImg, like, subscribe, tagBack;
	private TextView tabName, accountName, accountWxNo, subscribeText;
	private boolean from_childCatsActivity = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_detail);
		
		accountImg = (ImageView) findViewById(R.id.account_img);
		like = (ImageView) findViewById(R.id.like);
		subscribe = (ImageView) findViewById(R.id.subscribe);
		tagBack = (ImageView) findViewById(R.id.tag_back);
		tabName = (TextView) findViewById(R.id.tab_name);
		accountName = (TextView) findViewById(R.id.account_name);
		accountWxNo = (TextView) findViewById(R.id.account_wx_no);
		subscribeText = (TextView) findViewById(R.id.subscribe_text);
		Intent intent = getIntent();
		from_childCatsActivity = intent.getBooleanExtra("from_childCatsActivity", false);
		
		tagBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
//				if (from_childCatsActivity) {
//					Intent intent_childcats = new Intent(AccountDetailActivity.this, ChildCatsActivity.class);
//					startActivity(intent_childcats);
//				}
//				else {
					Intent intent_main = new Intent(AccountDetailActivity.this, MainActivity.class);
					startActivity(intent_main);
//				}
			}
		});
		
		like.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				like.setImageResource(R.drawable.like_select);
			}
		});
		
		subscribe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				subscribe.setImageResource(R.drawable.subscribe_select);
				subscribeText.setTextColor(Color.parseColor("#00bc44"));
			}
		});
		
//		if (intent != null && from_childCatsActivity) {
//			tabName.setText(getIntent().getStringExtra("account_name"));
//			accountName.setText(getIntent().getStringExtra("account_name"));
//			accountWxNo.setText(getIntent().getStringExtra("a_wx_no"));
//		}
		if (intent != null && intent.getParcelableExtra("account_img") != null) {
//			if (from_childCatsActivity)
			Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("account_img");
			Matrix matrix = new Matrix();//·Å´óÍ¼Æ¬
			matrix.postScale(1.2f, 1.2f);
			Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			accountImg.setImageBitmap(bm);
			
			tabName.setText(getIntent().getStringExtra("account_name"));
			accountName.setText(getIntent().getStringExtra("account_name"));
			accountWxNo.setText(getIntent().getStringExtra("a_wx_no"));
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_detail, menu);
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
