package com.caibo.weidu.util;

import android.content.Context;

public class InitUrl {
	
	private int type;
	private String baseUrl;
	private final String appcode = "wxh";
	private final String devicetype = "android";
	private final String v = "1.0";
	private final int register = 0;
	private final int accounts = 1;
	private final int accountDetail = 2;
	
	public InitUrl(String baseUrl, Context context) {
		this.baseUrl = baseUrl;
	}
	
//	public String initUrl(String baseUrl) {
//	}
}
