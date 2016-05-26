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
	
	//ʹ��image-loader������ͼƬ
	DisplayImageOptions options; //DisplayImageOptions����������ͼƬ��ʾ����
	
	public AccountAdapter(Context context, int textViewResourceId, ArrayList<Account> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
		this.accounts = objects;
		this.mContext = context;
		
		//ͼƬ����
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
			//΢�źŹ����������Ǽ�
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
