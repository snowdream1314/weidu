package com.caibo.weidu.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.caibo.weidu.R;
import com.caibo.weidu.main.account.ChildCatsActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	private ArrayList<ArrayList<HashMap<String, Object>>> mList, mListForChildCats;
	private ArrayList<HashMap<String, Object>> categoryList;
	private Context mContext;
	
	public ListViewAdapter(ArrayList<ArrayList<HashMap<String, Object>>> mList, ArrayList<ArrayList<HashMap<String, Object>>> mListForChildCats, ArrayList<HashMap<String, Object>> categoryList, Context context) {
		super();
		this.mList = mList;
		this.mListForChildCats = mListForChildCats;
		this.categoryList = categoryList;
		this.mContext = context;
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
			holder.category = (LinearLayout) convertView.findViewById(R.id.category);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (this.mList != null) {
			
			if (holder.account_category != null) {
				final HashMap<String, Object> hashMap = this.categoryList.get(position);
				holder.account_category.setText(hashMap.get("category_name").toString());
				
				if (holder.account_category.getText().equals("�����Ƽ�")) {
					holder.category.setClickable(false);//ʹ���ܵ��
					holder.wave_line.setVisibility(View.VISIBLE);
					holder.tab_image.setVisibility(ImageView.GONE);
					holder.tab_line.setVisibility(View.GONE);
					convertView.setBackgroundColor(Color.parseColor("#ffffff"));
//					convertView.findViewById(R.id.listview_item).setBackgroundColor(Color.parseColor("#ffffff"));
				}
				else {
					holder.wave_line.setVisibility(View.GONE);
					holder.tab_image.setVisibility(ImageView.VISIBLE);
					holder.tab_line.setVisibility(View.VISIBLE);
					convertView.setBackgroundColor(Color.parseColor("#f5f5f5"));
					
					final ArrayList<HashMap<String, Object>> arrayListForChildcats = this.mListForChildCats.get(position);
					//���õ���¼�
					holder.category.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(mContext, ChildCatsActivity.class);
							intent.putExtra("category_name", hashMap.get("category_name").toString());
							intent.putExtra("childCats", (Serializable) arrayListForChildcats);
							mContext.startActivity(intent);
						}
					});
				}
			}
			if (holder.gridView != null) {
				ArrayList<HashMap<String, Object>> arrayListForEveryGridView = this.mList.get(position);
				final GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext, arrayListForEveryGridView, holder.account_category.getText().toString());
				holder.gridView.setAdapter(gridViewAdapter);
				
				//���õ���¼�
//				try {
//					holder.gridView.setOnItemClickListener(new OnItemClickListener() {
//						public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//							gridViewAdapter.clearSelection(position);
//							gridViewAdapter.notifyDataSetChanged();
//						}
//					});
//				} catch (Exception e) {
//					e.printStackTrace();
//					Log.e("gridView", e.toString());
//				}
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
		LinearLayout category;
	}
	
	
	
}
