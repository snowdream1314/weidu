package com.caibo.weidu.modle;

import com.caibo.weidu.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyGridView extends LinearLayout {
	
	private final static String TYPE_HORIZONTAL = "horizontal";
	private final static String TYPE_VERTICAL = "vertical";

	private final static int NUM_HORIZONTAL = 2;
	private final static int NUM_VERTICAL = 3;
	
	private String mType;
	
	//含有的列数
	private int mNumColumns = 0;
	
	private Context mContext;
	
	//控件中的数据信息
	private ItemListView mData;
//	private Account mData;
	
	public MyGridView(Context context) {
		super(context);
	}
	
	public MyGridView(Context context, String type) {
		this(context);
		this.mContext = context;
		this.mType = type;
		
		if (TYPE_HORIZONTAL.equals(type)) {
			mNumColumns = NUM_HORIZONTAL;
		}
		else if (TYPE_VERTICAL.equals(type)) {
			mNumColumns = NUM_VERTICAL;
		}
		
		this.setOrientation(VERTICAL);
	}
	
	//每一个MyGridView包含的布局
	public void addView() {
		addTitle();
		addGridView();
	}
	
	//布局标题
	public void addTitle() {
		TitleLayoutHolder titleHolder = new TitleLayoutHolder();
		View titleLayout = LayoutInflater.from(mContext).inflate(R.layout.account_category, null);
		titleHolder.layoutTitle = (TextView) titleLayout.findViewById(R.id.account_category);
		this.addView(titleLayout);
	}
	
	//布局GridView部分
	public void addGridView() {
		if (mData == null) {
			return;
		}
		
		//根据屏幕的宽和每行的个数来计算宽度
		int width = ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth();
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//		int rowSpacing = mContext.getResources().getDimensionPixelSize();
		int rowSpacing = 0;
		
		int size = mData.getList().size();
		int numRow = size / mNumColumns + (size % mNumColumns == 0 ? 0 : 1);
		
		//遍历行
		for (int i = 0; i < numRow; i++) {
			//每一行是横向的LiearLayout
			LinearLayout linearLayout = new LinearLayout(mContext);
			linearLayout.setOrientation(HORIZONTAL);
			
			linearLayout.setPadding(rowSpacing, 0, rowSpacing, 0);
			
			//遍历列
			for (int j = 0; j < mNumColumns && i * mNumColumns + j < size; j++) {
				View view = LayoutInflater.from(mContext).inflate(R.layout.modle_account, null);
				view.setPadding(rowSpacing, rowSpacing, rowSpacing, rowSpacing);
				
				ViewHolder holder = new ViewHolder();
				holder.imageView = (ImageView) view.findViewById(R.id.account_image);
				//计算出ImageView的高和宽
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
				int mImageHeight = 0;
				int mImageWidth = 0;
				
				if (mImageWidth == 0) {
					mImageWidth = width / mNumColumns;
				}
				//分横竖屏计算出Image的高
				if (TYPE_HORIZONTAL.equals(mType)) {
					mImageHeight = mImageWidth * 9 / 16;
				}
				else if (TYPE_VERTICAL.equals(mType)) {
					mImageHeight = mImageWidth * 4 / 3;
				}
				layoutParams.width = mImageWidth;
				layoutParams.height = mImageHeight;
				
				holder.account_category = (TextView) view.findViewById(R.id.account_category);
				holder.account_name = (TextView) view.findViewById(R.id.account_name);
				
				view.setTag(holder);
				if (i == mImageWidth -1) {
					linearLayout.addView(view, j);
				}
				else {
					linearLayout.addView(view, params);
				}
			}
			this.addView(linearLayout);
		}
	}
	
	//初始化数据，包括初始化布局
	public void setData(ItemListView data) {
		this.mData = data;
		addView();
		fillData(mData);
	}
	
	//将信息填充在MyGridView中
	public void fillData(ItemListView data) {
		
		if (data == null) {
			return;
		}
		
		this.mData = data;
		//得到含有的所有子项的信息
		int childCount = getChildCount();
		
		//填充标题
		TitleLayoutHolder titleLayoutHolder  = (TitleLayoutHolder) this.getChildAt(0).getTag();
		titleLayoutHolder.layoutTitle.setText(data.getTitle());
		//填充view信息，从第一个到最后一个（分行加载，填充view的每一行）
		for (int i = 1; i < childCount; i ++) {
			//得到每一行的信息，包含多少个Item
			ViewGroup linear = (ViewGroup) this.getChildAt(i);
			int itemCount =  linear.getChildCount();
			
			//每一行的起始位置
			int firstDataIndex = (i-1) * mNumColumns;
			
			//遍历每一行的信息，显示所有的item
			for (int j = 0; j < itemCount; j++) {
				//所有信息包含的个数
				int dataIndex = mData.getList().size();
				if (firstDataIndex >= dataIndex) { //如果当前的下标已经大于数据的长度，就退出
					break;
				}
				//得到本行的数据信息
				ItemListView.ItemGridView itemData = mData.getList().get(firstDataIndex);
				
				ViewHolder holder = (ViewHolder) linear.getChildAt(j).getTag();
				
				holder.overLap.setText(itemData.getOverlap());
				holder.title.setText(itemData.getTitle());
				holder.imageView.setBackgroundResource(R.mipmap.empty_photo);
				
				firstDataIndex ++;
			}
		}
	}
	
	public class TitleLayoutHolder {
		public TextView layoutTitle;
	}
	
	public class ViewHolder {
		public ImageView imageView;
		public TextView account_name;
		public TextView account_category;
	}
}
