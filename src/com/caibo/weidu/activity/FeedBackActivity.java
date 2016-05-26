package com.caibo.weidu.activity;

import com.caibo.weidu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

public class FeedBackActivity extends Activity {
	
	private ImageView feedbackTagBack;
	private int tabTag = 2;
	private WebView webView;
	private String url;
	private Button submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_back);
		
		feedbackTagBack = (ImageView) findViewById(R.id.feedback_tag_back);
		feedbackTagBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FeedBackActivity.this, MainActivity.class);
				intent.putExtra("tabTag", tabTag);
				startActivity(intent);
				finish();
			}
		});
		
		webView  = (WebView) findViewById(R.id.feedback_webview);
		webView.getSettings().setJavaScriptEnabled(true);  
		
		Intent intent  = getIntent();
		url = intent.getStringExtra("url");
		
		submit = (Button) findViewById(R.id.feedback_submit);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						webView.loadUrl("javascript:submitForm();");
						Log.i("feedback_submit", "submit");
					}
				});
			}
		});
		webView.loadUrl(url);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feed_back, menu);
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
