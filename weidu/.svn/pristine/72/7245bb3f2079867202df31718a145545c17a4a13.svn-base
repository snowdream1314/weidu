package com.caibo.weidu.modle;

import java.util.List;

import com.caibo.weidu.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountAdapter extends ArrayAdapter<Account> {
	
	private int resourceId;
	
	public AccountAdapter(Context context, int textViewResourceId, List<Account> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Account account = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView accountCategory = (TextView) view.findViewById(R.id.account_category);
		ImageView accountImage = (ImageView) view.findViewById(R.id.account_image);
		TextView accountName = (TextView) view.findViewById(R.id.account_name);
		accountImage.setImageResource(account.getImageId());
		accountName.setText(account.getName());
		accountCategory.setText(account.getCategory());
		return view;
		
	}
}
