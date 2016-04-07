package com.caibo.weidu.modle;

import java.util.ArrayList;

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
	
	public AccountAdapter(Context context, int textViewResourceId, ArrayList<Account> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Account account = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView accountWxno = (TextView) view.findViewById(R.id.childCats_account_wx_no);
		TextView accountNotes = (TextView) view.findViewById(R.id.childCats_account_notes);
		ImageView accountImage = (ImageView) view.findViewById(R.id.childCats_account_img);
		ImageView accountScoreImage = (ImageView) view.findViewById(R.id.childCats_score_star);
		TextView accountName = (TextView) view.findViewById(R.id.childCats_account_name);
		
		accountImage.setImageResource(account.getImageId());
		accountScoreImage.setImageResource(account.getScoreImageId());
		accountNotes.setText(account.getNotes());
		accountWxno.setText(account.getWxno());
		accountName.setText(account.getName());
		return view;
		
	}
}
