package com.caibo.weidu.modle;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ItemListViewAdapter extends BaseAdapter {
	//ԭʼ����
	private List<ItemListView> list;
	//�ӹ��Ժ�����ݣ�֪����ʲô���͵�
	private List<DataHolder> dataList;
	private Context mContext;
	private LayoutInflater mInflater;
	//item�����࣬ ���Ը���type�ֶκ���������
	private int mTypeCount = 0;
	
	public ItemListViewAdapter (List<ItemListView> list, Context context) {
		this.list = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		
		//��ʼ��ʱ������ж�����
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
	
	//����list�õ��ж���������
	private int countType() {
		if(list == null) {
			return 0;
		}
		//Ĭ�ϵ�����
		int typeNum = 0;
		//�ܹ����е���
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
	
	//�ж�src��dst�Ƿ�����ͬ�����ͣ�������ͬ���Һ��е�����������ͬ����ͬ
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
	
	//�õ�ĳһ��position��Ӧ������
	@Override
	public int getItemViewType(int positon) {
		return dataList == null ? 0 :dataList.get(positon).type;
	}
	
	//��������������
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
			//���Ϊnull��ʱ������Ҫ��ʼ�������Լ��������
			myGridView = new MyGridView(mContext, type);
			myGridView.setData(list.get(position));
		}
		else {
			//�����Ϊnull��ֻ��Ҫ�������
			myGridView = (MyGridView) convertView;
			myGridView.fillData(dataList.get(position).data);
		}
		
		return myGridView;
	}
	
	//���ݺ���������
	public class DataHolder {
		ItemListView data;
		int type;
	}
}
