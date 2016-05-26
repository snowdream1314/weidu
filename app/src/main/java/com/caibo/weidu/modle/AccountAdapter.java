package com.caibo.weidu.modle;

import java.util.ArrayList;

import com.caibo.weidu.R;
import com.caibo.weidu.modle.GridViewAdapter.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountAdapter extends ArrayAdapter<Account> {
	
	private int resourceId;
	private ArrayList<Account> accounts;
	private Context mContext;
	
	//使用image-loader包加载图片
	DisplayImageOptions options; //DisplayImageOptions是用于设置图片显示的类
	
	public AccountAdapter(Context context, int textViewResourceId, ArrayList<Account> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
		this.accounts = objects;
		this.mContext = context;
		
		//图片加载
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(resourceId, null);
			holder.accountWxno = (TextView) convertView.findViewById(R.id.childCats_account_wx_no);
			holder.accountNotes = (TextView) convertView.findViewById(R.id.childCats_account_notes);
			holder.accountImage = (ImageView) convertView.findViewById(R.id.childCats_account_img);
			holder.accountScoreImage = (ImageView) convertView.findViewById(R.id.childCats_score_star);
			holder.accountName = (TextView) convertView.findViewById(R.id.childCats_account_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (accounts != null) {
			Account account = accounts.get(position);
			holder.accountNotes.setText(account.getNotes());
			//微信号过长则隐藏星级
			if (account.getWxno().length() > 10) {
				holder.accountWxno.setText(account.getWxno().subSequence(0, 6) + "...");
			} else {
				holder.accountWxno.setText(account.getWxno());
			}
			holder.accountName.setText(account.getName());
			
			switch (account.getScoreImageId()) {
			case 1:
				holder.accountScoreImage.setImageResource(R.drawable.star_1);
				break;
			case 2:
				holder.accountScoreImage.setImageResource(R.drawable.star_2);
				break;
			case 3:
				holder.accountScoreImage.setImageResource(R.drawable.star_3);
				break;
			case 4:
				holder.accountScoreImage.setImageResource(R.drawable.star_4);
				break;
			case 5:
				holder.accountScoreImage.setImageResource(R.drawable.star_5);
				break;
			default:
				break;
			}
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(account.getImageUrl(), holder.accountImage, options);
		}
		
		return convertView;
		
	}
	
	private class ViewHolder {
		TextView accountWxno;
		TextView accountNotes;
		ImageView accountImage;
		ImageView accountScoreImage;
		TextView accountName;
	}
}
