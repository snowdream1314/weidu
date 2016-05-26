package com.caibo.weidu.modle;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class myPagerAdapter extends FragmentPagerAdapter {
	
	private ArrayList<HashMap<String, Object>> arrayListForChildcats;
//	private ArrayList<Fragment> fragments;
//	private ArrayList<String> titles;
	private String session, deviceId;
	private ChildCatsFragment childCatsFragment;
	private Context mContext;
	
	public myPagerAdapter(FragmentManager fm, Context context, ArrayList<HashMap<String, Object>> arrayListForChildcats, String session, String deviceId) {
		super(fm);
		this.mContext = context;
//		this.titles = titles;
		this.arrayListForChildcats = arrayListForChildcats;
		this.session = session;
		this.deviceId = deviceId;
	}
	
	public myPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public int getCount() {
		if (arrayListForChildcats != null) {
			return arrayListForChildcats.size();
		}
		return 0;
	}
	
	@Override
	public Fragment getItem(int position) {
		//生成新的fragment
		childCatsFragment = new ChildCatsFragment(mContext, arrayListForChildcats.get(position).get("childCat_id").toString(), session, deviceId);
		return childCatsFragment;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return arrayListForChildcats.get(position).get("childCat_name").toString();
	}
	
	@Override 
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}
}
