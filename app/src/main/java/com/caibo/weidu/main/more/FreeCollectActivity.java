package com.caibo.weidu.main.more;

import com.caibo.weidu.R;
import com.caibo.weidu.main.MainActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

public class FreeCollectActivity extends Activity {
	
	private ImageView freecollectTagBack;
	private int tabTag = 2;
	
	private WebView webView;
	private String url;
	private ProgressDialog dialog;
	private Button submit;
	 private Handler mHandler = new Handler();
	
	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_free_collect);
		
		freecollectTagBack = (ImageView) findViewById(R.id.freecollect_tag_back);
		freecollectTagBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FreeCollectActivity.this, MainActivity.class);
				intent.putExtra("tabTag", tabTag);
				startActivity(intent);
				finish();
			}
		});
		
		webView  = (WebView) findViewById(R.id.freecollect_webview);
		webView.getSettings().setJavaScriptEnabled(true); 
		
		Intent intent  = getIntent();
		url = intent.getStringExtra("url");
		webView.loadUrl(url);
		webView.addJavascriptInterface(new MyJsInterface(), "submit");
		
		submit = (Button) findViewById(R.id.freecollect_submit);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				webView.loadUrl("javascript:submitForm()");
				Log.i("freecollect_submit", "submit");
			}
		});
	}
	
	final class MyJsInterface {
		MyJsInterface() {
		}
		
		public void clickOnAndroid() {
			mHandler.post(new Runnable() {
				public void run() {
					webView.loadUrl("javascript:submitForm()");
				}
			});
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.free_collect, menu);
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
