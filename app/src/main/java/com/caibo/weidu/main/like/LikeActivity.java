package com.caibo.weidu.main.like;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.caibo.weidu.R;
import com.caibo.weidu.SwipeMenuListView.SwipeMenu;
import com.caibo.weidu.SwipeMenuListView.SwipeMenuCreator;
import com.caibo.weidu.SwipeMenuListView.SwipeMenuItem;
import com.caibo.weidu.SwipeMenuListView.SwipeMenuListView;
import com.caibo.weidu.main.account.AccountDetailActivity;
import com.caibo.weidu.bean.Account;
import com.caibo.weidu.modle.AccountAdapter;
import com.caibo.weidu.util.InitUrls;
import com.caibo.weidu.util.MyAsyncTask;
import com.caibo.weidu.util.SaveDataInPref;
import com.caibo.weidu.util.onDataFinishedListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LikeActivity extends Activity {
	
	private String favoriteListUrl, session, deviceId, removeFavUrl, loadMoreUrl;
	private String accountName, accountWxNo, accountLogoLink, accountDesc, accountValidReason, accountId;
	private int accountScore, totalAccounts, totalPageCount;
	private int  pageNum = 1;
	private InitUrls initUrls = new InitUrls();
	private Account account;
	private ArrayList<Account> accounts;
	private LinearLayout likeNoneLayout;
	private SwipeMenuListView favListView;
	private TextView likeTabName;
	private int flag = 0;
	private int accountPosition;
	private float mDownX;
    private float mDownY;
	
	private SwipeRefreshLayout refreshLayout, likenoneRefresh;
	private AccountAdapter mAdapter;
	
	private static final int code = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_like);
		Log.i("create", "create");
		try {

		likeNoneLayout = (LinearLayout) findViewById(R.id.likenone_layout);
		favListView = (SwipeMenuListView) findViewById(R.id.like_listView);
		likeTabName = (TextView) findViewById(R.id.like_tabname);
		refreshLayout = (SwipeRefreshLayout) findViewById(R.id.like_SwipeRefreshLayout);
		likenoneRefresh = (SwipeRefreshLayout) findViewById(R.id.likenone_SwipeRefreshLayout);

		SharedPreferences pref = getSharedPreferences("registerData", MODE_PRIVATE);
		SaveDataInPref saveDataInPref = new SaveDataInPref(pref);
		session = saveDataInPref.GetSession("userData", "");
		deviceId = saveDataInPref.GetData("imei", "");
		favoriteListUrl = initUrls.InitFavoriteListUrl(session, deviceId, "1");

//		favListView.setVisibility(View.GONE);

		excuteTask(favoriteListUrl);

		//下拉刷新
		refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
											android.R.color.holo_red_light,
											android.R.color.holo_green_light,
											android.R.color.holo_orange_light);
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				Log.i("favoriteListUrl", favoriteListUrl);
				excuteTask(favoriteListUrl);
				refreshLayout.setRefreshing(false);
			}

		});

		likenoneRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
				android.R.color.holo_red_light,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light);
		likenoneRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

		@Override
		public void onRefresh() {
		// TODO Auto-generated method stub
		Log.i("favoriteListUrl", favoriteListUrl);
		excuteTask(favoriteListUrl);
		likenoneRefresh.setRefreshing(false);
		}
		});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void excuteTask(String url) {
		MyAsyncTask mTask = new MyAsyncTask(url);
		mTask.setOnDataFinishedListener(new onDataFinishedListener() {
			@Override
			public void onDataSuccessfully(Object data) {
//				Log.i("favoriteListUrl", data.toString());
				try {
					accounts = new ArrayList<Account>();
					JSONObject jsonObject = new JSONObject(data.toString());
					JSONArray jsonFavAccounts = jsonObject.getJSONObject("data").getJSONArray("favorite_accounts");
					pageNum = jsonObject.getJSONObject("data").getInt("page");
					totalAccounts = jsonObject.getJSONObject("data").getInt("total_count");
					totalPageCount = totalAccounts/20 + 1;
					Log.i("page now", Integer.toString(pageNum));
					Log.i("totalAccounts", Integer.toString(totalAccounts));
					Log.i("totalPageCount", Integer.toString(totalPageCount));
					if (jsonFavAccounts.length() != 0) {
						likenoneRefresh.setVisibility(View.GONE);
//						likeNoneLayout.setVisibility(View.GONE);
//						favListView.setVisibility(View.VISIBLE);
//						likeTabName.setText("收藏");
						for (int i = 0; i < jsonFavAccounts.length(); i++) {
							accountName = jsonFavAccounts.getJSONObject(i).getString("a_name");
							accountWxNo = jsonFavAccounts.getJSONObject(i).getString("a_wx_no");
							accountId = jsonFavAccounts.getJSONObject(i).getString("a_id");
							accountLogoLink = jsonFavAccounts.getJSONObject(i).getString("a_logo");
							accountDesc = jsonFavAccounts.getJSONObject(i).getString("a_desc");
							accountValidReason = jsonFavAccounts.getJSONObject(i).getString("a_valid_reason");
							accountScore = Integer.valueOf(jsonFavAccounts.getJSONObject(i).getString("a_rank"));
							Account account = new Account(accountName, accountWxNo, accountId, accountDesc, accountLogoLink, accountScore, accountValidReason);
							accounts.add(account);
						}
						final AccountAdapter adapter = new AccountAdapter(LikeActivity.this, R.layout.childcats_account_listview, accounts);
						mAdapter = adapter;
						
						favListView.setAdapter(adapter);
						favListView.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
								Account account = accounts.get(arg2);
								Intent intent = new Intent(LikeActivity.this, AccountDetailActivity.class);
								intent.putExtra("a_id", account.getAccountId());
								intent.putExtra("from_likeActivity", true);
								intent.putExtra("position", arg2);
								startActivityForResult(intent, 1);
							}
						});
						favListView.setOnTouchListener(new OnTouchListener() {
							@Override
						    public boolean onTouch(View arg0, MotionEvent ev) {
//						    	Log.i("onTouchEvent", "onTouchEvent");
						    	int action = ev.getAction();
						    	switch (action) {
						    	case MotionEvent.ACTION_DOWN:
						    		mDownX = ev.getX();
					                mDownY = ev.getY();
						    		break;
						    	case MotionEvent.ACTION_MOVE:
						    		Log.i("ACTION_MOVE", "ACTION_MOVE");
						    		float dy = Math.abs((ev.getY() - mDownY));
					                float dx = Math.abs((ev.getX() - mDownX));
					                if (Math.abs(dy) > dp2px(5) && Math.abs(dx) < dp2px(5)) {
					                	refreshLayout.setEnabled(true);
					                } else {
					                	refreshLayout.setEnabled(false);
					                }
						    		break;
						    	case MotionEvent.ACTION_UP:
						    		Log.i("ACTION_UP", "ACTION_UP");
						    		refreshLayout.setEnabled(true);
						    		break;
						    	case MotionEvent.ACTION_CANCEL:
						    		Log.i("ACTION_CANCEL", "ACTION_CANCEL");
						    		refreshLayout.setEnabled(true);
						    		break;
						    	}
						    	return false;
						    }
						});
						
						//滑动删除
						SwipeMenuCreator creator = new SwipeMenuCreator() {
							@Override
							public void create(SwipeMenu menu) {
								SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
								deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						                0x3F, 0x25)));
								deleteItem.setWidth(dp2px(120));
								deleteItem.setIcon(R.drawable.like_delete_icon);
								menu.addMenuItem(deleteItem);
							}
							
						};
						
						favListView.setMenuCreator(creator);
//						favListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
						favListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
							@Override
							public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
								//取消收藏
								accountId = accounts.get(position).getAccountId();
								removeFavUrl = initUrls.InitRemoveFavoriteUrl(session, deviceId, accountId);
								MyAsyncTask mTaskRev = new MyAsyncTask(removeFavUrl);
								mTaskRev.setOnDataFinishedListener(new onDataFinishedListener() {
									@Override
									public void onDataSuccessfully(Object data) {
										Log.i("remove_data", data.toString());
										accounts.remove(position);
										adapter.notifyDataSetChanged();
										if (accounts.size() == 0) {
//											likeNoneLayout.setVisibility(View.VISIBLE);
											likenoneRefresh.setVisibility(View.VISIBLE);
										}
									}
								});
								mTaskRev.execute("string");
								return false;
							}
						});
						
						//滚动到底部加载更多
						final LinearLayout footerLayout = InitFooterLayout();
						favListView.setOnScrollListener(new OnScrollListener() {
							@Override
							public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
								if (firstVisibleItem + visibleItemCount == totalItemCount) {
//									Log.i("onScroll", "onScroll");
								}
							}
							
							@Override
							public void onScrollStateChanged(AbsListView view, int scrollState) {
								switch (scrollState) {
								case OnScrollListener.SCROLL_STATE_IDLE:
									//判断滚动到底部
									if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
										
										if (pageNum < totalPageCount && totalAccounts > 20) {
											favListView.addFooterView(footerLayout);
//											flag = 0;
											pageNum += 1;
											loadMoreUrl = initUrls.InitFavoriteListUrl(session, deviceId, Integer.toString(pageNum));
											Log.i("loadMoreUrl", loadMoreUrl);
											try {
												MyAsyncTask mTask = new MyAsyncTask(loadMoreUrl);
												mTask.setOnDataFinishedListener(new onDataFinishedListener() {
													@Override
													public void onDataSuccessfully(Object data) {
														try {
															Log.i("data", data.toString());
															JSONObject jsonObject = new JSONObject(data.toString());
															totalAccounts = jsonObject.getJSONObject("data").getInt("total_count");
															totalPageCount = totalAccounts/20 + 1;
															if (!jsonObject.getJSONObject("data").isNull("favorite_accounts")) {
																JSONArray jsonAccounts = jsonObject.getJSONObject("data").getJSONArray("favorite_accounts");
																for (int i = 0; i < jsonAccounts.length(); i++) {
																	accountName = jsonAccounts.getJSONObject(i).getString("a_name");
																	accountWxNo = jsonAccounts.getJSONObject(i).getString("a_wx_no");
																	accountId = jsonAccounts.getJSONObject(i).getString("a_id");
																	accountLogoLink = jsonAccounts.getJSONObject(i).getString("a_logo");
																	accountDesc = jsonAccounts.getJSONObject(i).getString("a_desc");
																	accountValidReason = jsonAccounts.getJSONObject(i).getString("a_valid_reason");
																	accountScore = Integer.valueOf(jsonAccounts.getJSONObject(i).getString("a_rank"));
																	Account account = new Account(accountName, accountWxNo, accountId, accountDesc, accountLogoLink, accountScore, accountValidReason);
																	accounts.add(account);
																	Log.i("accounts size", Integer.toString(accounts.size()));
																}
																adapter.notifyDataSetChanged();
															}
															favListView.removeFooterView(footerLayout);

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
									break;
								}
							}
						});
						
						Log.i("account_num", Integer.toString(accounts.size()));
					} 
					else {
						likenoneRefresh.setVisibility(View.VISIBLE);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mTask.execute("string");
	}
	
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	
	private LinearLayout InitFooterLayout() {
		LayoutParams mLayoutParams = new LinearLayout.LayoutParams(  
	            LinearLayout.LayoutParams.WRAP_CONTENT,  
	            LinearLayout.LayoutParams.WRAP_CONTENT); 
		
		LayoutParams FFlayoutParams = new LinearLayout.LayoutParams(  
	            LinearLayout.LayoutParams.MATCH_PARENT,  
	            LinearLayout.LayoutParams.MATCH_PARENT);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		ProgressBar progressBar = new ProgressBar(this);
		// 进度条显示位置
		progressBar.setPadding(0, 0, 15, 0);
		// 把进度条加入到layout中
		layout.addView(progressBar, mLayoutParams);
		// 文本内容
		TextView textView = new TextView(this);
		textView.setText("加载中...");
		textView.setGravity(Gravity.CENTER_VERTICAL);
		// 把文本加入到layout中
		layout.addView(textView, FFlayoutParams);
		// 设置layout的重力方向，即对齐方式是
		layout.setGravity(Gravity.CENTER);

		// 设置ListView的页脚layout
        LinearLayout loadingLayout = new LinearLayout(this);  
        loadingLayout.addView(layout, mLayoutParams);  
        loadingLayout.setGravity(Gravity.CENTER); 
        
        return loadingLayout;
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.like, menu);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("onActivityResult", "success");
		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == code) {
			try {
				flag = data.getExtras().getInt("flag");
				Log.i("flag", Integer.toString(flag));
				Log.i("position", Integer.toString(data.getExtras().getInt("position")));
				if (flag == 0) {
					accountPosition = data.getExtras().getInt("position");
					accounts.remove(accountPosition);
					mAdapter.notifyDataSetChanged();
					if (accounts.size() == 0) {
						likenoneRefresh.setVisibility(View.VISIBLE);
//						likeNoneLayout.setVisibility(View.VISIBLE);
//						favListView.setVisibility(View.GONE);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
