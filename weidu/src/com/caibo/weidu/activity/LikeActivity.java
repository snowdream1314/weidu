package com.caibo.weidu.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.caibo.weidu.R;
import com.caibo.weidu.modle.Account;
import com.caibo.weidu.modle.AccountAdapter;
import com.caibo.weidu.modle.SwipeMenu;
import com.caibo.weidu.modle.SwipeMenuCreator;
import com.caibo.weidu.modle.SwipeMenuItem;
import com.caibo.weidu.modle.SwipeMenuListView;
import com.caibo.weidu.modle.SwipeMenuListView.OnMenuItemClickListener;
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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

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
	private int flag = 1;
	
	private SwipeRefreshLayout refreshLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_like);
		
		likeNoneLayout = (LinearLayout) findViewById(R.id.likenone_layout);
		favListView = (SwipeMenuListView) findViewById(R.id.like_listView);
		likeTabName = (TextView) findViewById(R.id.like_tabname);
		refreshLayout = (SwipeRefreshLayout) findViewById(R.id.like_SwipeRefreshLayout);
		
		SharedPreferences pref = getSharedPreferences("registerData", MODE_PRIVATE);
		SaveDataInPref saveDataInPref = new SaveDataInPref(pref);
		session = saveDataInPref.GetSession("userData", "");
		deviceId = saveDataInPref.GetData("imei", "");
		favoriteListUrl = initUrls.InitFavoriteListUrl(session, deviceId, "1");
		
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
						likeNoneLayout.setVisibility(View.GONE);
						likeTabName.setText("收藏");
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
//						final AccountAdapterWithDelete adapter = new AccountAdapterWithDelete(LikeActivity.this, R.layout.like_account_listview, accounts, session, deviceId);
						final AccountAdapter adapter = new AccountAdapter(LikeActivity.this, R.layout.childcats_account_listview, accounts);
						
						favListView.setAdapter(adapter);
						favListView.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
								Account account = accounts.get(arg2);
								Intent intent = new Intent(LikeActivity.this, AccountDetailActivity.class);
								intent.putExtra("a_id", account.getAccountId());
								startActivity(intent);
							}
						});
						
						//滑动删除
						SwipeMenuCreator creator = new SwipeMenuCreator() {
							@Override
							public void create(SwipeMenu menu) {
								SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
								deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						                0x3F, 0x25)));
								deleteItem.setIcon(R.drawable.like_delete_icon);
								deleteItem.setWidth(235);
								menu.addMenuItem(deleteItem);
							}
						};
						favListView.setMenuCreator(creator);
						favListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
						favListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
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
									Log.i("onScroll", "onScroll");
								}
							}
							
							@Override
							public void onScrollStateChanged(AbsListView view, int scrollState) {
								switch (scrollState) {
								case OnScrollListener.SCROLL_STATE_IDLE:
									//判断滚动到底部
									if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
										Log.i("flag", Integer.toString(flag));
										
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
															if (jsonObject.getJSONObject("data").isNull("favorite_accounts")) {
																Toast.makeText(LikeActivity.this, "没有更多的啦！", Toast.LENGTH_SHORT).show();
//																loadPageNum -= 1;
															}
															else {
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
//															flag = 1;
															
														} catch (Exception e) {
															e.printStackTrace();
														}
														
													}
												});
												mTask.execute("string");
												
											} catch (Exception e) {
												e.printStackTrace();
											}
											
										} else {
											Toast.makeText(LikeActivity.this, "没有更多的啦！", Toast.LENGTH_SHORT).show();
										}
//										favListView.removeFooterView(footerLayout);
									}
									break;
								}
							}
						});
						
						Log.i("account_num", Integer.toString(accounts.size()));
					} else {
						likeNoneLayout.setVisibility(View.VISIBLE);
						likeTabName.setText("喜欢");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mTask.execute("string");
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
	
//	@Override
//	public void onStart() {
//		excuteTask(favoriteListUrl);
//	}
	
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
}
