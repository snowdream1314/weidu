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
	
	//使用image-loader包加载图片
	DisplayImageOptions options; //DisplayImageOptions是用于设置图片显示的类
	
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
		//为每个item设置setOnTouchListener事件
		view.setOnTouchListener(new OnTouchListener() {
			
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("onTouch", "onTouch");
				return detector.onTouchEvent(event);
				
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:	//手指按下
//					downX = event.getX();		//获取手指X坐标
//					downY = event.getY();
//					if (accountDelete != null) {
//						accountDelete.setVisibility(View.GONE);//隐藏显示出来的删除按钮
//					}
//					break;
//				case MotionEvent.ACTION_UP:		//手指离开
//					upX = event.getX();
//					upY = event.getY();
//					break;
////				case MotionEvent.ACTION_MOVE:
////					return true;
//				}
//				
//				if (accountDelete != null) {
//					if (Math.abs(downX - upX) > 100 ) {	//2次坐标绝对值大于35，则认为是左右滑动
//						accountDelete.setVisibility(View.VISIBLE);
////						delete = accountDelete;
//						viewForDelete = v;
//						return true;	//终止事件
//					}
//					return false;	//释放事件，使onItemClick可以执行
//				}
//				return false;
			}
		});
		
		accountDelete.setOnClickListener(new OnClickListener() {	//为删除按钮绑定事件
			@Override
			public void onClick(View v) {
				if (accountDelete != null) {
					accountDelete.setVisibility(View.GONE);  	//点击按钮后隐藏
					deleteItem(viewForDelete, position);		//删除数据
				}
			}
			
		});
		
		//图片加载
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.account_image)	// 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.account_image)		// 设置图片Uri为空或是错误的时候显示的图片  
				.showImageOnFail(R.drawable.account_image)		// 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true)		 // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)		// 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(60))	// 设置成圆角图片
				.build();	// 创建配置过得DisplayImageOption对象  
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(account.getImageUrl(), accountImage, options);
		
		accountNotes.setText(account.getNotes());
		accountWxno.setText(account.getWxno());
		accountName.setText(account.getName());
		//微信号过长则隐藏星级
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
		
		//取消收藏
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

