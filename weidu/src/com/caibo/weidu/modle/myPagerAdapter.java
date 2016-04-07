package com.caibo.weidu.modle;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class myPagerAdapter extends FragmentPagerAdapter {
	
	private ArrayList<Fragment> fragments;
	private ArrayList<String> titles;
	
	public myPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> titles) {
		super(fm);
		this.fragments = fragments;
		this.titles = titles;
	}
	
	public myPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public int getCount() {
		if (titles != null) {
			return titles.size();
		}
		return 0;
	}
	
	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position).toString();
	}
	
	@Override 
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}
}
