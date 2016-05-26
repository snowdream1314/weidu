package com.caibo.weidu.modle;

import java.util.ArrayList;

import com.caibo.weidu.R;
import com.caibo.weidu.bean.MoreItem;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreItemAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private ArrayList<Message> moreItemList;
	
	private static final int text = 0;
	private static final int img_text = 1;
	
	public MoreItemAdapter(Context conetxt, ArrayList<Message> moreItemList) {
		this.moreItemList = moreItemList;
//		this.mapList = mapList;
		mInflater = (LayoutInflater) conetxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return moreItemList.size();
	}
	
	@Override
	public Object getItem(int position) {
		return moreItemList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getItemViewType(int position) {
		Message msg = moreItemList.get(position);
		int type = msg.what;
		return type;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Message msg = moreItemList.get(position);
		int type = getItemViewType(position);
		ViewHolderText holderText = null;
		ViewHolderImageText holderImageText = null;
		
		if (convertView == null) {
//			Log.i("convertView", "null");
			switch (type) {
			case text:
				holderText = new ViewHolderText();
				convertView = mInflater.inflate(R.layout.more_item_text, null);
				holderText.text = (TextView) convertView.findViewById(R.id.name_itemMore);
				holderText.text.setText(msg.obj.toString());
				convertView.setTag(holderText);
				break;
			case img_text:
				holderImageText = new ViewHolderImageText();
				convertView = mInflater.inflate(R.layout.more_item, null);
				holderImageText.image = (ImageView) convertView.findViewById(R.id.image_itemMore);
				holderImageText.imageTag = (ImageView) convertView.findViewById(R.id.tag_itemMore);
				holderImageText.text = (TextView) convertView.findViewById(R.id.name_itemMore);
				holderImageText.image.setImageResource(((MoreItem)msg.obj).getImageId());
				holderImageText.imageTag.setImageResource(((MoreItem)msg.obj).getImageTagId());
				holderImageText.text.setText(((MoreItem)msg.obj).getItemName());
				convertView.setTag(holderImageText);
				break;
			default:
				break;	
			}
		} 
		else {
//			Log.i("convertView", "not null");
			switch (type) {
			case text:
				holderText = (ViewHolderText) convertView.getTag();
				holderText.text.setText(msg.obj.toString());
				break;
			case img_text:
				holderImageText = (ViewHolderImageText) convertView.getTag();
				holderImageText.image.setImageResource(((MoreItem)msg.obj).getImageId());
				holderImageText.imageTag.setImageResource(((MoreItem)msg.obj).getImageTagId());
				holderImageText.text.setText(((MoreItem)msg.obj).getItemName());
				break;
			default:
				break;
			}
		}
		return convertView;
	}
	
	private class ViewHolderText {
		private TextView text;
	}
	
	private class ViewHolderImageText {
		private TextView text;
		private ImageView image;
		private ImageView imageTag;
	}
}
