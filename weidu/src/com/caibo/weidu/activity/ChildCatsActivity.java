package com.caibo.weidu.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.caibo.weidu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ChildCatsActivity extends Activity {
	
	private TextView childCatsTabName;
	private RadioGroup radioGroup;
	private LayoutInflater mInflater;
	private int indecatorWidth;
	private LinearLayout hsvTab;
	
	private ArrayList<HashMap<String, Object>> arrayListForChildcats;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_child_cats);
		
		childCatsTabName = (TextView) findViewById(R.id.childCats_tab_name);
//		hsvTab = (LinearLayout) findViewById(R.id.hsv_tab);
		
		
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
//		LayoutInflater mInflater = LayoutInflater.from(this);
		mInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE); 
		
		Intent intent = getIntent();
		childCatsTabName.setText(intent.getStringExtra("category_name"));
		arrayListForChildcats = (ArrayList<HashMap<String, Object>>) intent.getSerializableExtra("childCats");
		
//		try {
//			for (int i = 0; i < arrayListForChildcats.size(); i++) {
//				View view = mInflater.inflate(R.layout.radio_group, hsvTab, false);
//				TextView text = (TextView) view.findViewById(R.id.radioGroup_title);
//				text.setText(arrayListForChildcats.get(i).get("childCat_name").toString());
//				hsvTab.addView(view);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		DisplayMetrics dm = new DisplayMetrics(); 
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		indecatorWidth = dm.widthPixels / 4;
		
		try {
			
			radioGroup.removeAllViews();
			for (int i = 0; i < arrayListForChildcats.size(); i++) {
				RadioButton rb = (RadioButton) mInflater.inflate(R.layout.radio_group, null);
				rb.setId(i);
				rb.setText(arrayListForChildcats.get(i).get("childCat_name").toString());
				rb.setLayoutParams(new LayoutParams(indecatorWidth, LayoutParams.MATCH_PARENT));
				radioGroup.addView(rb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.child_cats, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
