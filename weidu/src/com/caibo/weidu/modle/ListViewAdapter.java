package com.caibo.weidu.modle;

import java.util.ArrayList;
import java.util.HashMap;

import com.caibo.weidu.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	private ArrayList<ArrayList<HashMap<String, Object>>> mList;
	private ArrayList<HashMap<String, Object>> categoryList;
	private Context mContext;
	
	public ListViewAdapter(ArrayList<ArrayList<HashMap<String, Object>>> mList, ArrayList<HashMap<String, Object>> categoryList, Context mContext) {
		super();
		this.mList = mList;
		this.categoryList = categoryList;
		this.mContext = mContext;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.account_listview, null, false);
			holder.account_category = (TextView) convertView.findViewById(R.id.account_category);
			holder.tab_image = (ImageView) convertView.findViewById(R.id.tab_image);
			holder.tab_line = (View) convertView.findViewById(R.id.tab_line);
			holder.wave_line = (View) convertView.findViewById(R.id.wave_line);
			holder.gridView = (GridView) convertView.findViewById(R.id.account_gridView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (this.mList != null) {
			
			if (holder.account_category != null) {
				HashMap<String, Object> hashMap = this.categoryList.get(position);
				holder.account_category.setText(hashMap.get("category_name").toString());
				
				if (holder.account_category.getText().equals("»»√≈Õ∆ºˆ")) {
					holder.wave_line.setVisibility(View.VISIBLE);
					holder.tab_image.setVisibility(ImageView.GONE);
					holder.tab_line.setVisibility(View.GONE);
//					convertView.findViewById(R.id.listview_item).setBackgroundColor(Color.parseColor("#ffffff"));
				}
				else {
					holder.wave_line.setVisibility(View.GONE);
					convertView.setBackgroundColor(Color.parseColor("#f5f5f5"));
				}
			}
			if (holder.gridView != null) {
				ArrayList<HashMap<String, Object>> arrayListForEveryGridView = this.mList.get(position);
				GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext, arrayListForEveryGridView, holder.account_category.getText().toString());
				holder.gridView.setAdapter(gridViewAdapter);
			}
		}
		return convertView;
	}
	
	private class ViewHolder {
		TextView account_category;
		GridView gridView;
		ImageView tab_image;
		View tab_line;
		View wave_line;
	}
	
	
	
}
