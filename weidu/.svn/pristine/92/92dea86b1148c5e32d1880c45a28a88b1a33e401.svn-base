package com.caibo.weidu.modle;

import java.util.ArrayList;
import java.util.HashMap;

import com.caibo.weidu.R;
import com.caibo.weidu.activity.AccountDetailActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<HashMap<String, Object>> mList;
	private int selectedPosition = -10;
	
	//使用image-loader包加载图片
	DisplayImageOptions options; //DisplayImageOptions是用于设置图片显示的类
	
	public GridViewAdapter(Context mConetxt, ArrayList<HashMap<String, Object>> mList, String account_category) {
		super();
		this.mContext = mConetxt;
		this.mList = mList;
		
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.account_image)	// 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.account_image)		// 设置图片Uri为空或是错误的时候显示的图片  
				.showImageOnFail(R.drawable.account_image)		// 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true)		 // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)		// 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(60))	// 设置成圆角图片
				.build();	// 创建配置过得DisplayImageOption对象  
		
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
			HashMap<String, Object> hashMap = this.mList.get(position);
			holder.account_name.setText(hashMap.get("account_name").toString());
			//图片加载
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(hashMap.get("a_logo_link").toString(), holder.account_image, options);
			
			//响应点击事件
			if (selectedPosition == position) {
//				holder.account_name.setTextColor(Color.parseColor("#000000"));
				Intent intent = new Intent(this.mContext, AccountDetailActivity.class);
				holder.account_image.setDrawingCacheEnabled(true);
				intent.putExtra("account_name", holder.account_name.getText());
				intent.putExtra("account_img", holder.account_image.getDrawingCache());
				intent.putExtra("a_wx_no", hashMap.get("a_wx_no").toString());
				intent.putExtra("a_id", hashMap.get("a_id").toString());
				this.mContext.startActivity(intent);
			}
		}
		
		return convertView;
		
	}
	
	public class ViewHolder {
		ImageView account_image;
		TextView account_name;
	}
}
