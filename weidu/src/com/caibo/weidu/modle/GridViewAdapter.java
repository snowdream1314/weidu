package com.caibo.weidu.modle;

import java.util.ArrayList;
import java.util.HashMap;

import com.caibo.weidu.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<HashMap<String, Object>> mList;
	private String account_category;
	
	public GridViewAdapter(Context mConetxt, ArrayList<HashMap<String, Object>> mList, String account_category) {
		super();
		this.mContext = mConetxt;
		this.mList = mList;
		this.account_category = account_category;
	}
	
	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		}
		else {
			return this.mList.size();
		}
	}
	
	@Override
	public Object getItem(int position) {
		if (mList == null) {
			return null;
		}
		else {
			return this.mList.get(position);
		}
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.modle_account, null, false);
			holder.account_image = (ImageView) convertView.findViewById(R.id.account_image);
			holder.account_name = (TextView) convertView.findViewById(R.id.account_name);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (this.mList != null) {
			HashMap<String, Object> hashMap = this.mList.get(position);
			if (holder.account_image != null) {
				holder.account_image.setImageDrawable(convertView.getResources().getDrawable(R.drawable.account_image));
				holder.account_name.setText(hashMap.get("account_name").toString());
			}
			
		}
		
		return convertView;
		
	}
	
	public class ViewHolder {
		ImageView account_image;
		TextView account_name;
	}
}
