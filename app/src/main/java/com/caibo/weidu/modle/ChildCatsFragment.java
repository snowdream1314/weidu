package com.caibo.weidu.modle;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.caibo.weidu.R;
import com.caibo.weidu.util.InitUrls;
import com.caibo.weidu.util.MyAsyncTask;
import com.caibo.weidu.util.onDataFinishedListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ChildCatsFragment extends Fragment {
	
	private ListView listView;
	private ArrayList<Account> accountList;
	private Context mContext;
	private String session, deviceId, childCatsId;
	private int accountScore, totalAccounts, totalPageCount;
	private int pageNum = 1;
	private int flag = 1;
	private String accountsUrl, loadMoreUrl;
	private InitUrls initUrls = new InitUrls();
	private String accountName, accountWxNo, accountLogoLink, accountDesc, accountValidReason, accountId;
	private LinearLayout footerLayout;
	private SwipeRefreshLayout refreshLayout;
	
	private boolean hasLoadedOnce = false;
	protected boolean isVisible;
	
	public ChildCatsFragment(Context context, String childCatsId, String session, String deviceId) {
		this.mContext = context;
		this.childCatsId = childCatsId;
		this.session = session;
		this.deviceId = deviceId;
	}
	
	//�ж��Ƿ�ɼ�
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
//			onVisible();
		} else {
			isVisible = false;
//			onInvisible();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.account_fragment, container, false);
		footerLayout = InitFooterLayout();
		listView = (ListView) fragmentView.findViewById(R.id.childCatsListView);
		refreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.childCats_SwipeRefreshLayout);
		
		accountsUrl = initUrls.InitAccountsUrl(session, deviceId, childCatsId, Integer.toString(pageNum));
		
		//����ˢ��
		refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
											android.R.color.holo_red_light,
											android.R.color.holo_green_light,
											android.R.color.holo_orange_light);
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				Log.i("favoriteListUrl", accountsUrl);
				excuteTask(accountsUrl);
				refreshLayout.setRefreshing(false);
			}
		});
		
		excuteTask(accountsUrl);
		
		return fragmentView;
	}
	
	private void excuteTask(String url) {
		try {
			MyAsyncTask mTask = new MyAsyncTask(accountsUrl);
			mTask.setOnDataFinishedListener(new onDataFinishedListener() {
				@Override
				public void onDataSuccessfully(Object data) {
					String account_data = data.toString();
					try {
						Log.i("account_data", account_data);
						accountList = new ArrayList<Account>();
						JSONObject jsonObject = new JSONObject(account_data);
						pageNum = jsonObject.getJSONObject("data").getInt("page");
						totalAccounts = jsonObject.getJSONObject("data").getInt("total_count");
						totalPageCount = totalAccounts/20 + 1;
						Log.i("page now", Integer.toString(pageNum));
						Log.i("totalAccounts", Integer.toString(totalAccounts));
						Log.i("totalPageCount", Integer.toString(totalPageCount));
						if (!jsonObject.getJSONObject("data").getString("total_count").equals("0")) {
//							Toast.makeText(mContext, "�÷�������ʱû�й��ںţ�", Toast.LENGTH_SHORT).show();
//							hasLoadedOnce = false;
//						} else {
							JSONArray jsonAccounts = jsonObject.getJSONObject("data").getJSONArray("accounts");
							for (int i = 0; i < jsonAccounts.length(); i++) {
								accountName = jsonAccounts.getJSONObject(i).getString("a_name");
								accountWxNo = jsonAccounts.getJSONObject(i).getString("a_wx_no");
								accountId = jsonAccounts.getJSONObject(i).getString("a_id");
								accountLogoLink = jsonAccounts.getJSONObject(i).getString("a_logo");
								accountDesc = jsonAccounts.getJSONObject(i).getString("a_desc");
								accountValidReason = jsonAccounts.getJSONObject(i).getString("a_valid_reason");
								accountScore = Integer.valueOf(jsonAccounts.getJSONObject(i).getString("a_rank"));
								Account account = new Account(accountName, accountWxNo, accountId, accountDesc, accountLogoLink, accountScore, accountValidReason);
								accountList.add(account);
							}
							Log.i("accounts size", Integer.toString(accountList.size()));
							final AccountAdapter adapter = new AccountAdapter(mContext, R.layout.childcats_account_listview, accountList);
							listView.setAdapter(adapter);
							listView.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
									Account account = accountList.get(arg2);
									Intent intent = new Intent(mContext, AccountDetailActivity.class);
									intent.putExtra("from_childCatsActivity", true);
									intent.putExtra("a_id", account.getAccountId());
									startActivity(intent);
								}
							});
							hasLoadedOnce = true;
							//�������ײ����ظ���
							final LinearLayout footerLayout = InitFooterLayout();
							listView.setOnScrollListener(new OnScrollListener() {
								@Override
								public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
									if (firstVisibleItem + visibleItemCount == totalItemCount) {
//										Log.i("onScroll", "onScroll");
									}
								}
								
								@Override
								public void onScrollStateChanged(AbsListView view, int scrollState) {
									switch (scrollState) {
									case OnScrollListener.SCROLL_STATE_IDLE:
										//�жϹ������ײ�
										if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
											Log.i("flag", Integer.toString(flag));
											
											if (pageNum < totalPageCount && totalAccounts > 20) {
												listView.addFooterView(footerLayout);
//												flag = 0;
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
//																	Toast.makeText(mContext, "û�и��������", Toast.LENGTH_SHORT).show();
//																	loadPageNum -= 1;
//																}
//																else {
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
																		accountList.add(account);
																		Log.i("accounts size", Integer.toString(accountList.size()));
																	}
																	adapter.notifyDataSetChanged();
																}
																listView.removeFooterView(footerLayout);
//																flag = 1;
																
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
//											else {
//												Toast.makeText(mContext, "û�и��������", Toast.LENGTH_SHORT).show();
//											}
//											listView.removeFooterView(footerLayout);
										}
										break;
									}
								}
							});
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
	
	private LinearLayout InitFooterLayout() {
		LayoutParams mLayoutParams = new LinearLayout.LayoutParams(  
	            LinearLayout.LayoutParams.WRAP_CONTENT,  
	            LinearLayout.LayoutParams.WRAP_CONTENT); 
		
		LayoutParams FFlayoutParams = new LinearLayout.LayoutParams(  
	            LinearLayout.LayoutParams.MATCH_PARENT,  
	            LinearLayout.LayoutParams.MATCH_PARENT);
		
		LinearLayout layout = new LinearLayout(mContext);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		ProgressBar progressBar = new ProgressBar(mContext);
		// ��������ʾλ��  
        progressBar.setPadding(0, 0, 15, 0); 
        // �ѽ��������뵽layout�� 
		layout.addView(progressBar, mLayoutParams);  
        // �ı�����  
        TextView textView = new TextView(mContext);  
        textView.setText("������...");  
        textView.setGravity(Gravity.CENTER_VERTICAL);  
        // ���ı����뵽layout��  
        layout.addView(textView, FFlayoutParams); 
        // ����layout���������򣬼����뷽ʽ��  
        layout.setGravity(Gravity.CENTER); 
        
        // ����ListView��ҳ��layout  
        LinearLayout loadingLayout = new LinearLayout(mContext);  
        loadingLayout.addView(layout, mLayoutParams);  
        loadingLayout.setGravity(Gravity.CENTER); 
        
        return loadingLayout;
		
	}
}
