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
	
	//���е�����
	private int mNumColumns = 0;
	
	private Context mContext;
	
	//�ؼ��е�������Ϣ
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
	
	//ÿһ��MyGridView�����Ĳ���
	public void addView() {
		addTitle();
		addGridView();
	}
	
	//���ֱ���
	public void addTitle() {
		TitleLayoutHolder titleHolder = new TitleLayoutHolder();
		View titleLayout = LayoutInflater.from(mContext).inflate(R.layout.account_category, null);
		titleHolder.layoutTitle = (TextView) titleLayout.findViewById(R.id.account_category);
		this.addView(titleLayout);
	}
	
	//����GridView����
	public void addGridView() {
		if (mData == null) {
			return;
		}
		
		//������Ļ�Ŀ��ÿ�еĸ�����������
		int width = ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth();
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//		int rowSpacing = mContext.getResources().getDimensionPixelSize();
		int rowSpacing = 0;
		
		int size = mData.getList().size();
		int numRow = size / mNumColumns + (size % mNumColumns == 0 ? 0 : 1);
		
		//������
		for (int i = 0; i < numRow; i++) {
			//ÿһ���Ǻ����LiearLayout
			LinearLayout linearLayout = new LinearLayout(mContext);
			linearLayout.setOrientation(HORIZONTAL);
			
			linearLayout.setPadding(rowSpacing, 0, rowSpacing, 0);
			
			//������
			for (int j = 0; j < mNumColumns && i * mNumColumns + j < size; j++) {
				View view = LayoutInflater.from(mContext).inflate(R.layout.modle_account, null);
				view.setPadding(rowSpacing, rowSpacing, rowSpacing, rowSpacing);
				
				ViewHolder holder = new ViewHolder();
				holder.imageView = (ImageView) view.findViewById(R.id.account_image);
				//�����ImageView�ĸߺͿ�
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
				int mImageHeight = 0;
				int mImageWidth = 0;
				
				if (mImageWidth == 0) {
					mImageWidth = width / mNumColumns;
				}
				//�ֺ����������Image�ĸ�
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
	
	//��ʼ�����ݣ�������ʼ������
	public void setData(ItemListView data) {
		this.mData = data;
		addView();
		fillData(mData);
	}
	
	//����Ϣ�����MyGridView��
	public void fillData(ItemListView data) {
		
		if (data == null) {
			return;
		}
		
		this.mData = data;
		//�õ����е������������Ϣ
		int childCount = getChildCount();
		
		//������
		TitleLayoutHolder titleLayoutHolder  = (TitleLayoutHolder) this.getChildAt(0).getTag();
		titleLayoutHolder.layoutTitle.setText(data.getTitle());
		//���view��Ϣ���ӵ�һ�������һ�������м��أ����view��ÿһ�У�
		for (int i = 1; i < childCount; i ++) {
			//�õ�ÿһ�е���Ϣ���������ٸ�Item
			ViewGroup linear = (ViewGroup) this.getChildAt(i);
			int itemCount =  linear.getChildCount();
			
			//ÿһ�е���ʼλ��
			int firstDataIndex = (i-1) * mNumColumns;
			
			//����ÿһ�е���Ϣ����ʾ���е�item
			for (int j = 0; j < itemCount; j++) {
				//������Ϣ�����ĸ���
				int dataIndex = mData.getList().size();
				if (firstDataIndex >= dataIndex) { //�����ǰ���±��Ѿ��������ݵĳ��ȣ����˳�
					break;
				}
				//�õ����е�������Ϣ
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
