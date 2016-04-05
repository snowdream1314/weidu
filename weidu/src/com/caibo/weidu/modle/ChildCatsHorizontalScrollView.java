package com.caibo.weidu.modle;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class ChildCatsHorizontalScrollView extends HorizontalScrollView {
	
	public ChildCatsHorizontalScrollView(Context context) {
		super(context);
	}
	
	public ChildCatsHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
	}
}
