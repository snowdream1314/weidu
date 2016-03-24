package com.caibo.weidu.modle;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ItemListViewAdapter extends BaseAdapter {
	//原始数据
	private List<ItemListView> list;
	//加工以后的数据，知道是什么类型的
	private List<DataHolder> dataList;
	private Context mContext;
	private LayoutInflater mInflater;
	//item的种类， 可以根据type字段和数量来做
	private int mTypeCount = 0;
	
	public ItemListViewAdapter (List<ItemListView> list, Context context) {
		this.list = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		
		//初始化时计算出有多少类
		mTypeCount = countType();
	}
	
	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}
	
	@Override
	public Object getItem(int position) {
		return list == null ? null : list.get(position);
	}
	
	//根据list得到有多少种类型
	private int countType() {
		if(list == null) {
			return 0;
		}
		//默认的类型
		int typeNum = 0;
		//总共含有的项
		int size =list.size();
		dataList = new ArrayList<DataHolder>();
		
		boolean flag = false;
		for (int i = 0; i < size; i++) {
			flag = false;
			DataHolder dataHolder = new DataHolder();
			dataHolder.data = list.get(i);
			for (int j = 0; j < i; j++) {
				if (isSameType(dataHolder.data, dataList.get(j).data)) {
					dataHolder.type = dataList.get(j).type;
					flag = true;
					break;
				}
			}
			if (!flag) {
				dataHolder.type = typeNum++;
			}
			dataList.add(dataHolder);
		}
		return typeNum;
	}
	
	//判断src和dst是否是相同的类型，类型相同并且含有的子项数量相同才相同
	private boolean isSameType(ItemListView src, ItemListView dst) {
		if (src == null || dst == null ) {
			return false;
		}
		if (src.getType().equals(dst.getType())) {
			if (src.getList().size() == dst.getList().size()) {
				return true;
			}
		}
		return false;
	}
	
	//得到某一个position对应的类型
	@Override
	public int getItemViewType(int positon) {
		return dataList == null ? 0 :dataList.get(positon).type;
	}
	
	//计算出总体的类型
	@Override
	public int getViewTypeCount() {
		return mTypeCount > 0 ? mTypeCount : 1;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String type = list.get(position).getType();
		MyGridView myGridView = null;
		if (convertView == null) {
			//如果为null的时候则需要初始化布局以及填充数据
			myGridView = new MyGridView(mContext, type);
			myGridView.setData(list.get(position));
		}
		else {
			//如果不为null，只需要填充数据
			myGridView = (MyGridView) convertView;
			myGridView.fillData(dataList.get(position).data);
		}
		
		return myGridView;
	}
	
	//数据和数据类型
	public class DataHolder {
		ItemListView data;
		int type;
	}
}
