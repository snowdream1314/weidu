package com.caibo.weidu.modle;

import java.util.ArrayList;

import com.caibo.weidu.R;
import com.caibo.weidu.activity.AccountDetailActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class AccountFragment extends Fragment {
	
	private ListView listView;
	private ArrayList<Account> accountList;
	private Context mContext;
	private ImageView accountImage;
	
	public AccountFragment(ArrayList<Account> list, Context context) {
		this.accountList = list;
		this.mContext = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.account_fragment, container, false);
		View v = inflater.inflate(R.layout.childcats_account_listview, container, false);
		listView = (ListView) fragmentView.findViewById(R.id.childCatsListView);
		accountImage = (ImageView) v.findViewById(R.id.childCats_account_img);
		
		AccountAdapter adapter = new AccountAdapter(mContext, R.layout.childcats_account_listview, accountList);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Account account = accountList.get(arg2);
				Intent intent = new Intent(mContext, AccountDetailActivity.class);
				intent.putExtra("from_childCatsActivity", true);
				intent.putExtra("account_name", account.getName());
				intent.putExtra("a_wx_no", account.getWxno());
				accountImage.setDrawingCacheEnabled(true);
				intent.putExtra("account_img", accountImage.getDrawingCache());
				startActivity(intent);
			}
		});
		return fragmentView;
	}
}
