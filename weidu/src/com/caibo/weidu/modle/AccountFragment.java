package com.caibo.weidu.modle;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.caibo.weidu.R;
import com.caibo.weidu.activity.AccountDetailActivity;
import com.caibo.weidu.util.InitUrls;
import com.caibo.weidu.util.MyAsyncTask;
import com.caibo.weidu.util.onDataFinishedListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

public class AccountFragment extends Fragment {
	
	private ListView listView;
	private ArrayList<Account> accountList;
	private Context mContext;
	private String session, deviceId, childCatsId;
	private int pageNum, accountScore;
	private int flag = 1;
	private String accountsUrl;
	private InitUrls initUrls = new InitUrls();
	private String accountName, accountWxNo, accountLogoLink, accountDesc, accountValidReason, accountId;
	private LinearLayout footerLayout;
	
	public AccountFragment(ArrayList<Account> list, Context context, String session, String deviceId, String childCatsId, int pageNum) {
		this.accountList = list;
		this.mContext = context;
		this.session = session;
		this.deviceId = deviceId;
		this.childCatsId = childCatsId;
		this.pageNum = pageNum;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.account_fragment, container, false);
		footerLayout = InitFooterLayout();
//		LinearLayout listViewFooter = (LinearLayout) inflater.inflate(R.layout.listview_footer, container, false);
		listView = (ListView) fragmentView.findViewById(R.id.childCatsListView);
		
//		listView.addFooterView(InitFooterLayout());
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
		
		listView.setOnScrollListener(new OnScrollListener() {
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
					Log.i("flag", Integer.toString(flag));
					//判断滚动到底部
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
//						flag = 1;
						if (flag == 1) {
							listView.addFooterView(footerLayout);
							flag = 0;
							pageNum += 1;
							accountsUrl = initUrls.InitAccountsUrl(session, deviceId, childCatsId, Integer.toString(pageNum));
							Log.i("pageNum", Integer.toString(pageNum));
							try {
								MyAsyncTask mTask = new MyAsyncTask(accountsUrl);
								mTask.setOnDataFinishedListener(new onDataFinishedListener() {
									@Override
									public void onDataSuccessfully(Object data) {
										try {
											Log.i("data", data.toString());
											JSONObject jsonObject = new JSONObject(data.toString());
											if (jsonObject.getJSONObject("data").isNull("accounts")) {
												Toast.makeText(mContext, "没有更多的啦！", Toast.LENGTH_SHORT).show();
												pageNum -= 1;
											}
											else {
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
													Log.i("account", account.toString());
												}
												adapter.notifyDataSetChanged();
											}
											listView.removeFooterView(footerLayout);
											flag = 1;
											
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
		
		
		return fragmentView;
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
		// 进度条显示位置  
        progressBar.setPadding(0, 0, 15, 0); 
        // 把进度条加入到layout中 
		layout.addView(progressBar, mLayoutParams);  
        // 文本内容  
        TextView textView = new TextView(mContext);  
        textView.setText("加载中...");  
        textView.setGravity(Gravity.CENTER_VERTICAL);  
        // 把文本加入到layout中  
        layout.addView(textView, FFlayoutParams); 
        // 设置layout的重力方向，即对齐方式是  
        layout.setGravity(Gravity.CENTER); 
        
        // 设置ListView的页脚layout  
        LinearLayout loadingLayout = new LinearLayout(mContext);  
        loadingLayout.addView(layout, mLayoutParams);  
        loadingLayout.setGravity(Gravity.CENTER); 
        
        return loadingLayout;
		
	}
}
