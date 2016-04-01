package com.caibo.weidu.modle;

import java.util.ArrayList;
import java.util.HashMap;

import com.caibo.weidu.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

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
//	private String account_category;
	
	//ʹ��image-loader������ͼƬ
	DisplayImageOptions options; //DisplayImageOptions����������ͼƬ��ʾ����
	
	public GridViewAdapter(Context mConetxt, ArrayList<HashMap<String, Object>> mList, ArrayList<HashMap<String, Object>> mListImg, String account_category) {
		super();
		this.mContext = mConetxt;
		this.mList = mList;
//		this.mListImg = mListImg;
//		this.account_category = account_category;
		
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
			holder.account_name.setText(hashMap.get("account_name").toString());
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(hashMap.get("a_logo_link").toString(), holder.account_image, options);
			
//			holder.account_image.setImageDrawable(convertView.getResources().getDrawable(R.drawable.account_image));//ʹ��Ĭ��ͼƬ
			
			//���ط���һ��ȫ���������Ժ�һ���Լ���
//			HashMap<String, Object> hashMapImg = new HashMap<String, Object>();
//			if (holder.account_image != null) {
//				String account_name = hashMap.get("account_name").toString();
//				holder.account_name.setText(account_name);
//				for (int i = 0; i< this.mListImg.size(); i++) {
//					String a_name = this.mListImg.get(i).get("a_name").toString();
//					if (a_name == account_name) {
//						holder.account_image.setImageBitmap((Bitmap)this.mListImg.get(i).get("a_logo"));
//						break;
//					}
//				}
//				holder.account_image.setImageBitmap((Bitmap)hashMapImg.get("a_logo"));
//			}
			
		}
		
		return convertView;
		
	}
	
	public class ViewHolder {
		ImageView account_image;
		TextView account_name;
	}
}
