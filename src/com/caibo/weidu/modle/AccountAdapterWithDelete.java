package com.caibo.weidu.modle;

import java.util.ArrayList;

import com.caibo.weidu.R;
import com.caibo.weidu.util.InitUrls;
import com.caibo.weidu.util.MyAsyncTask;
import com.caibo.weidu.util.onDataFinishedListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountAdapterWithDelete extends ArrayAdapter<Account> {
	
	private int resourceId;
	private float downX, upX, downY, upY;
	private View viewForDelete;
	private ArrayList<Account> objects;
	private String removeFavUrl, session, deviceId, account_id;
	private InitUrls initUrls = new InitUrls();
	private GestureDetectorCompat detector;
	private Context mContext;
	private MyGestureListener mGestureListener;
//	private Animation animation;
//	private SharedPreferences pref;
//	private ImageView delete;
	
	//ʹ��image-loader������ͼƬ
	DisplayImageOptions options; //DisplayImageOptions����������ͼƬ��ʾ����
	
	public AccountAdapterWithDelete(Context context, int textViewResourceId, ArrayList<Account> objects, String session, String deviceId) {
		super(context, textViewResourceId, objects);
		this.mContext = context;
		this.objects = objects;
		this.session = session;
		this.deviceId = deviceId;
		resourceId = textViewResourceId;
//		animation = AnimationUtils.loadAnimation(context, R.anim.abc_fade_in);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Account account = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView accountWxno = (TextView) view.findViewById(R.id.like_account_wx_no);
		TextView accountNotes = (TextView) view.findViewById(R.id.like_account_notes);
		ImageView accountImage = (ImageView) view.findViewById(R.id.like_account_img);
		ImageView accountScoreImage = (ImageView) view.findViewById(R.id.like_score_star);
		TextView accountName = (TextView) view.findViewById(R.id.like_account_name);
		
		final ImageView accountDelete = (ImageView) view.findViewById(R.id.like_delete);
		accountDelete.setVisibility(View.GONE);
//		mGestureListener = new MyGestureListener(accountDelete);
		detector = new GestureDetectorCompat(mContext, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onDown(MotionEvent event) {
				return false;
			}
			
			@Override
			public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
				Log.i("onFling", "onFling");
				if (accountDelete != null) {
					if (Math.abs(event1.getX() - event2.getX()) > 35) {
						accountDelete.setVisibility(View.VISIBLE);
						return true;
					}
					return false;
				}
				return false;
			}
		});
		
//		view.setLongClickable(true);
		//Ϊÿ��item����setOnTouchListener�¼�
		view.setOnTouchListener(new OnTouchListener() {
			
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("onTouch", "onTouch");
				return detector.onTouchEvent(event);
				
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:	//��ָ����
//					downX = event.getX();		//��ȡ��ָX����
//					downY = event.getY();
//					if (accountDelete != null) {
//						accountDelete.setVisibility(View.GONE);//������ʾ������ɾ����ť
//					}
//					break;
//				case MotionEvent.ACTION_UP:		//��ָ�뿪
//					upX = event.getX();
//					upY = event.getY();
//					break;
////				case MotionEvent.ACTION_MOVE:
////					return true;
//				}
//				
//				if (accountDelete != null) {
//					if (Math.abs(downX - upX) > 100 ) {	//2���������ֵ����35������Ϊ�����һ���
//						accountDelete.setVisibility(View.VISIBLE);
////						delete = accountDelete;
//						viewForDelete = v;
//						return true;	//��ֹ�¼�
//					}
//					return false;	//�ͷ��¼���ʹonItemClick����ִ��
//				}
//				return false;
			}
		});
		
		accountDelete.setOnClickListener(new OnClickListener() {	//Ϊɾ����ť���¼�
			@Override
			public void onClick(View v) {
				if (accountDelete != null) {
					accountDelete.setVisibility(View.GONE);  	//�����ť������
					deleteItem(viewForDelete, position);		//ɾ������
				}
			}
			
		});
		
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
	
	public void deleteItem(View view, final int position) {
		objects.remove(position);
		notifyDataSetChanged();
		
		//ȡ���ղ�
		account_id = objects.get(position).getAccountId();
		removeFavUrl = initUrls.InitRemoveFavoriteUrl(session, deviceId, account_id);
		MyAsyncTask mTaskRev = new MyAsyncTask(removeFavUrl);
		mTaskRev.setOnDataFinishedListener(new onDataFinishedListener() {
			@Override
			public void onDataSuccessfully(Object data) {
				Log.i("remove_data", data.toString());
			}
		});
		mTaskRev.execute("string");
	}
	
}

