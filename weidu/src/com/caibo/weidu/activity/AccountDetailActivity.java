package com.caibo.weidu.activity;

import org.json.JSONObject;

import com.caibo.weidu.R;
import com.caibo.weidu.util.MyAsyncTask;
import com.caibo.weidu.util.onDataFinishedListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountDetailActivity extends Activity {
	
	private ImageView accountImg, like, subscribe, tagBack, scoreStar;
	private TextView tabName, accountName, accountWxNo, subscribeText, functionIntroduction, authenticationInformation, accountUrl;
	private boolean from_childCatsActivity = false;
	
	private String baseUrl, requestUrl, session;
	private String account_id, account_name, account_wx_no, account_desc, account_valid_reason, account_url, account_message;
	private  int account_score;
	private static String  account_logo_link;
	
	//ʹ��image-loader������ͼƬ
	DisplayImageOptions options; //DisplayImageOptions����������ͼƬ��ʾ����
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_detail);
		
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.account_image)	// ����ͼƬ�����ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.account_image)		// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
				.showImageOnFail(R.drawable.account_image)		// ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.cacheInMemory(true)		 // �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisc(true)		// �������ص�ͼƬ�Ƿ񻺴���SD����
				.displayer(new RoundedBitmapDisplayer(80))	// ���ó�Բ��ͼƬ
				.build();	// �������ù���DisplayImageOption����  
		
		accountImg = (ImageView) findViewById(R.id.account_img);
		scoreStar = (ImageView) findViewById(R.id.score_star);
		like = (ImageView) findViewById(R.id.like);
		subscribe = (ImageView) findViewById(R.id.subscribe);
		tagBack = (ImageView) findViewById(R.id.tag_back);
		tabName = (TextView) findViewById(R.id.tab_name);
		accountName = (TextView) findViewById(R.id.account_name);
		accountWxNo = (TextView) findViewById(R.id.account_wx_no);
		subscribeText = (TextView) findViewById(R.id.subscribe_text);
		functionIntroduction = (TextView) findViewById(R.id.function_introduction);
		authenticationInformation = (TextView) findViewById(R.id.authentication_information);
		accountUrl = (TextView) findViewById(R.id.account_url);
		
		Intent intent = getIntent();
		from_childCatsActivity = intent.getBooleanExtra("from_childCatsActivity", false);
		
		tagBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				Intent intent_main = new Intent(AccountDetailActivity.this, MainActivity.class);
				startActivity(intent_main);
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
		
		if (intent != null) {
			
			account_id = getIntent().getStringExtra("a_id");
//			Log.i("account_id", account_id);
			
			try {
				SharedPreferences pref = getSharedPreferences("registerData", MODE_PRIVATE);
				String userData = pref.getString("userData", "");
				JSONObject jsonObject = new JSONObject(userData);
				session = jsonObject.getJSONObject("data").getString("session");
//				Log.i("session", session);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				baseUrl = "http://wx.xiyiyi.com/Mobile/Account/accountDetail?appcode=wxh&v=1&devicetype=android&deviceid=864572010615121&format=clientdetailview_v1&wxhsession=" + session + "&a_id=" + account_id;
				MyAsyncTask mTask = new MyAsyncTask(baseUrl);
				mTask.setOnDataFinishedListener(new onDataFinishedListener() {
					@Override
					public void onDataSuccessfully(Object data) {
						String account_data = data.toString();
						try {
							Log.i("account_data", account_data);
							JSONObject jsonObject = new JSONObject(account_data);
							account_name = jsonObject.getJSONObject("data").getString("a_name");
							account_logo_link = jsonObject.getJSONObject("data").getString("a_logo");
							account_wx_no = jsonObject.getJSONObject("data").getString("a_wx_no");
							account_desc = jsonObject.getJSONObject("data").getString("a_desc");
							account_valid_reason = jsonObject.getJSONObject("data").getString("a_valid_reason");
							account_url = jsonObject.getJSONObject("data").getString("a_url");
							account_message = jsonObject.getString("message");
							account_score = Integer.valueOf(jsonObject.getJSONObject("data").getString("a_rank"));
							
							tabName.setText(account_name);
							accountName.setText(account_name);
							accountWxNo.setText(account_wx_no);
							if (account_desc.length() != 0) {
								functionIntroduction.setText(account_desc);
							} else {
								functionIntroduction.setText("�ù��ں����޽���");
							}
							if (account_valid_reason.length() != 0) {
								authenticationInformation.setText(account_valid_reason);
							} else {
								authenticationInformation.setText("�ù��ں�������֤��Ϣ");
							}
							
							accountUrl.setText(account_url);
							switch (account_score) {
							case 1:
								scoreStar.setImageResource(R.drawable.star_1);
								break;
							case 2:
								scoreStar.setImageResource(R.drawable.star_2);
								break;
							case 3:
								scoreStar.setImageResource(R.drawable.star_3);
								break;
							case 4:
								scoreStar.setImageResource(R.drawable.star_4);
								break;
							case 5:
								scoreStar.setImageResource(R.drawable.star_5);
								break;
							default:
								break;
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						Log.i("account_logo_link", account_logo_link);
						//ͼƬ����
						ImageLoader.getInstance().displayImage(account_logo_link, accountImg, options);
					}
				});
				mTask.execute("string");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
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
