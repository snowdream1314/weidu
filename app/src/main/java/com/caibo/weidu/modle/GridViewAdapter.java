package com.caibo.weidu.modle;

import java.util.ArrayList;
import java.util.HashMap;

import com.caibo.weidu.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<HashMap<String, Object>> mList;
	private int selectedPosition = -1;
	
	//ʹ��image-loader������ͼƬ
	DisplayImageOptions options; //DisplayImageOptions����������ͼƬ��ʾ����
	
	public GridViewAdapter(Context conetxt, ArrayList<HashMap<String, Object>> mList, String account_category) {
		super();
		this.mContext = conetxt;
		this.mList = mList;
		
		// ʹ��DisplayImageOptions.Builder()����DisplayImageOptions
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.account_image)	// ����ͼƬ�����ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.account_image)		// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
				.showImageOnFail(R.drawable.account_image)		// ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.cacheInMemory(true)		 // �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisc(true)		// �������ص�ͼƬ�Ƿ񻺴���SD����
				.displayer(new RoundedBitmapDisplayer(60))	// ���ó�Բ��ͼƬ
				.build();	// �������ù���DisplayImageOption����  
		
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
	
	public void clearSelection(int position) {
		selectedPosition = position;
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
			final HashMap<String, Object> hashMap = this.mList.get(position);
			holder.account_name.setText(hashMap.get("account_name").toString());
			//ͼƬ����
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(hashMap.get("a_logo_link").toString(), holder.account_image, options);
			
			//��Ӧ����¼�
			holder.account_image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, AccountDetailActivity.class);
					intent.putExtra("a_id", hashMap.get("a_id").toString());
					mContext.startActivity(intent);
				}
			});
			
//			if (selectedPosition == position) {
////				holder.account_name.setTextColor(Color.parseColor("#000000"));
//				Intent intent = new Intent(this.mContext, AccountDetailActivity.class);
//				holder.account_image.setDrawingCacheEnabled(true);
////				intent.putExtra("account_name", holder.account_name.getText());
////				intent.putExtra("account_img", holder.account_image.getDrawingCache());
////				intent.putExtra("a_wx_no", hashMap.get("a_wx_no").toString());
//				intent.putExtra("a_id", hashMap.get("a_id").toString());
//				mContext.startActivity(intent);
//			}
		}
		
		return convertView;
		
	}
	
	public class ViewHolder {
		ImageView account_image;
		TextView account_name;
	}
}
