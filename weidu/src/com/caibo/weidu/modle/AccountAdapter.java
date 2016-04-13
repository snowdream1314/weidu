package com.caibo.weidu.modle;

import java.util.ArrayList;

import com.caibo.weidu.R;
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
	
	//ʹ��image-loader������ͼƬ
	DisplayImageOptions options; //DisplayImageOptions����������ͼƬ��ʾ����
	
	public AccountAdapter(Context context, int textViewResourceId, ArrayList<Account> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Account account = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView accountWxno = (TextView) view.findViewById(R.id.childCats_account_wx_no);
		TextView accountNotes = (TextView) view.findViewById(R.id.childCats_account_notes);
		ImageView accountImage = (ImageView) view.findViewById(R.id.childCats_account_img);
		ImageView accountScoreImage = (ImageView) view.findViewById(R.id.childCats_score_star);
		TextView accountName = (TextView) view.findViewById(R.id.childCats_account_name);
		
		//ͼƬ����
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.account_image)	// ����ͼƬ�����ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.account_image)		// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
				.showImageOnFail(R.drawable.account_image)		// ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.cacheInMemory(true)		 // �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisc(true)		// �������ص�ͼƬ�Ƿ񻺴���SD����
				.displayer(new RoundedBitmapDisplayer(60))	// ���ó�Բ��ͼƬ
				.build();	// �������ù���DisplayImageOption����  
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(account.getImageUrl(), accountImage, options);
		
		accountNotes.setText(account.getNotes());
		accountWxno.setText(account.getWxno());
		accountName.setText(account.getName());
		//΢�źŹ����������Ǽ�
		if (account.getWxno().length() > 8) {
			accountScoreImage.setVisibility(View.GONE);
		}
		
		switch (account.getScoreImageId()) {
		case 1:
			accountScoreImage.setImageResource(R.drawable.star_1);
			break;
		case 2:
			accountScoreImage.setImageResource(R.drawable.star_2);
			break;
		case 3:
			accountScoreImage.setImageResource(R.drawable.star_3);
			break;
		case 4:
			accountScoreImage.setImageResource(R.drawable.star_4);
			break;
		case 5:
			accountScoreImage.setImageResource(R.drawable.star_5);
			break;
		default:
			break;
		}
		return view;
		
	}
}
