package com.caibo.weidu.activity;

import org.json.JSONObject;

import com.caibo.weidu.R;
import com.caibo.weidu.util.InitUrls;
import com.caibo.weidu.util.MyAsyncTask;
import com.caibo.weidu.util.SaveDataInPref;
import com.caibo.weidu.util.onDataFinishedListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AccountDetailActivity extends Activity {
	
	private ImageView accountImg, like, subscribe, tagBack, scoreStar;
	private TextView tabName, accountName, accountWxNo, subscribeText, functionIntroduction, authenticationInformation, accountUrl, progressbarText;
	private boolean from_childCatsActivity = false;
	private boolean from_likeActivity = false;
	
	private String accountDetailUrl, addFavoriteUrl, removeFavUrl, session, deviceId;
	private String account_id, account_name, account_wx_no, account_desc, account_valid_reason, account_url, account_message;
	private  int account_score, a_favorited;
	private static String  account_logo_link;
	
	private InitUrls initUrls = new InitUrls();
	
	private LinearLayout subscribeLayout, accountUrlLayout, likeLayout, progressbarLayout;
	
	private int flag;
	private int position;
	private static final int resultCode = 2;
	
	//使用image-loader包加载图片
	DisplayImageOptions options; //DisplayImageOptions是用于设置图片显示的类
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_detail);
		
		SharedPreferences pref = getSharedPreferences("registerData", MODE_PRIVATE);
		final SaveDataInPref saveDataInPref = new SaveDataInPref(pref);
		session = saveDataInPref.GetSession("userData", "");
		deviceId = saveDataInPref.GetData("imei", "");
		
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.account_image)	// 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.account_image)		// 设置图片Uri为空或是错误的时候显示的图片  
				.showImageOnFail(R.drawable.account_image)		// 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true)		 // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)		// 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(80))	// 设置成圆角图片
				.build();	// 创建配置过得DisplayImageOption对象  
		
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
		progressbarText = (TextView) findViewById(R.id.progressbar_text);
		
		likeLayout = (LinearLayout) findViewById(R.id.like_layout);
		subscribeLayout = (LinearLayout) findViewById(R.id.subscribe_layout);
		accountUrlLayout = (LinearLayout) findViewById(R.id.accountdetail_accounturl_layout);
		progressbarLayout = (LinearLayout) findViewById(R.id.accountdetail_progressbar_layout);
		
		progressbarLayout.setVisibility(View.GONE);
		
		subscribe.setTag(R.drawable.subscribe_normal);
		
		Intent intent = getIntent();
		from_childCatsActivity = intent.getBooleanExtra("from_childCatsActivity", false);
		from_likeActivity = intent.getBooleanExtra("from_likeActivity", false);
		account_id = getIntent().getStringExtra("a_id");
		position = getIntent().getIntExtra("position", 0);
		
		tagBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (from_likeActivity) {
					Intent intent = new Intent(AccountDetailActivity.this, LikeActivity.class);
					intent.putExtra("flag", flag);
					intent.putExtra("position", position);
					setResult(resultCode, intent);
				}
				AccountDetailActivity.this.finish();
			}
		});
		
		likeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Integer integer = (Integer) like.getTag();
				integer = integer == null ? 0 : integer;
				switch(integer) {
				case R.drawable.like_normal:
					progressbarLayout.setVisibility(View.VISIBLE);
					progressbarText.setText("收藏中...");
					addFavoriteUrl = initUrls.InitAddFavoriteUrl(session, deviceId, account_id);
					MyAsyncTask mTask = new MyAsyncTask(addFavoriteUrl);
					mTask.setOnDataFinishedListener(new onDataFinishedListener() {
						@Override
						public void onDataSuccessfully(Object data) {
							progressbarLayout.setVisibility(View.GONE);
							Log.i("data", data.toString());
							try {
								JSONObject jsonObject = new JSONObject(data.toString());
								Toast.makeText(AccountDetailActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
								if (jsonObject.getInt("code") == 1) {
									like.setImageResource(R.drawable.like_select);
									like.setTag(R.drawable.like_select);
									flag = 1;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					mTask.execute("string");
					break;
				case R.drawable.like_select:
					progressbarLayout.setVisibility(View.VISIBLE);
					progressbarText.setText("取消收藏中...");
					removeFavUrl = initUrls.InitRemoveFavoriteUrl(session, deviceId, account_id);
					MyAsyncTask mTaskRev = new MyAsyncTask(removeFavUrl);
					mTaskRev.setOnDataFinishedListener(new onDataFinishedListener() {
						@Override
						public void onDataSuccessfully(Object data) {
							progressbarLayout.setVisibility(View.GONE);
							Log.i("remove_data", data.toString());
							try {
								JSONObject jsonObject = new JSONObject(data.toString());
								Toast.makeText(AccountDetailActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
								if (jsonObject.getInt("code") == 1) {
									like.setImageResource(R.drawable.like_normal);
									like.setTag(R.drawable.like_normal);
									flag = 0;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					mTaskRev.execute("string");
					break;
				default:
					break;
				}
			}
		});
		
		subscribeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//复制到系统剪贴板
				ClipboardManager cm = (ClipboardManager) getSystemService(AccountDetailActivity.CLIPBOARD_SERVICE);
				cm.setText(accountWxNo.getText());
				Toast.makeText(AccountDetailActivity.this, accountWxNo.getText() + "已经复制到系统剪贴板", Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(AccountDetailActivity.this, SubscribeActivity.class);
				intent.putExtra("account_wx_name", accountWxNo.getText());
				startActivity(intent);
			}
		});
		
		if (intent != null) {
			
//			account_id = getIntent().getStringExtra("a_id");
//			Log.i("account_id", account_id);
			
			try {
				accountDetailUrl = initUrls.InitAccountDetailUrl(session, deviceId, account_id);
				MyAsyncTask mTask = new MyAsyncTask(accountDetailUrl);
				mTask.setOnDataFinishedListener(new onDataFinishedListener() {
					@Override
					public void onDataSuccessfully(Object data) {
						String account_data = data.toString();

						try {
							Log.i("account_data", account_data);
							JSONObject jsonObject = new JSONObject(account_data);
							String message = jsonObject.getString("message");
							Log.i("message", message);
							if (jsonObject.getInt("code") == 0) {
								Toast.makeText(AccountDetailActivity.this, message, Toast.LENGTH_SHORT).show();
								finish();
							} else {
								account_name = jsonObject.getJSONObject("data").getString("a_name");
								account_logo_link = jsonObject.getJSONObject("data").getString("a_logo");
								account_wx_no = jsonObject.getJSONObject("data").getString("a_wx_no");
								account_desc = jsonObject.getJSONObject("data").getString("a_desc");
								account_valid_reason = jsonObject.getJSONObject("data").getString("a_valid_reason");
								account_url = jsonObject.getJSONObject("data").getString("a_url");
								account_message = jsonObject.getString("message");
								account_score = Integer.valueOf(jsonObject.getJSONObject("data").getString("a_rank"));
								a_favorited = jsonObject.getJSONObject("data").getInt("a_favorited");
								tabName.setText(account_name);
								accountName.setText(account_name);
								accountWxNo.setText(account_wx_no);
								if (a_favorited == 1) {
									like.setImageResource(R.drawable.like_select);
									like.setTag(R.drawable.like_select);
									flag = 1;
								} else {
									like.setImageResource(R.drawable.like_normal);
									like.setTag(R.drawable.like_normal);
									flag = 0;
								}
								if (account_desc.length() != 0) {
									functionIntroduction.setText(account_desc);
								} else {
									functionIntroduction.setText("该公众号暂无介绍");
								}
								if (account_valid_reason.length() != 0) {
									authenticationInformation.setText(account_valid_reason);
								} else {
									authenticationInformation.setText("该公众号暂无认证信息");
								}
								if (account_url.length() == 0) {
									accountUrlLayout.setVisibility(View.GONE);
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
								
								Log.i("account_logo_link", account_logo_link);
								//图片加载
								ImageLoader.getInstance().displayImage(account_logo_link, accountImg, options);
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
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
		if (from_likeActivity) {
			Intent intent = new Intent(AccountDetailActivity.this, LikeActivity.class);
			intent.putExtra("flag", flag);
			intent.putExtra("position", position);
			setResult(resultCode, intent);
		}
		AccountDetailActivity.this.finish();
//		finish();
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
