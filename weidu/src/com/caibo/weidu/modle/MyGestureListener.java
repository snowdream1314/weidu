package com.caibo.weidu.modle;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
	private ImageView accountDelete;
	
	public MyGestureListener(ImageView accountDelete) {
		this.accountDelete = accountDelete;
	}
	
//	@Override
//	public boolean onDown(MotionEvent event) {
//		return true;
//	}
	
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
}
