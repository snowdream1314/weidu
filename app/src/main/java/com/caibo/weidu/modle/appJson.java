package com.caibo.weidu.modle;

import java.util.List;

public class appJson {
	private List<appJsonData> appJsonDatas;
	private String message;
	private int code, timestamp;
	
	public String get_message() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int get_code() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public void setTimeStamp(int time) {
		this.timestamp = time;
	}
	
	public int get_timestamp() {
		return timestamp;
	}
	
	public List<appJsonData> get_appJsonData() {
		return appJsonDatas;
	}
	
	public void setListAppJsonData(List<appJsonData> appJsonDatas) {
		this.appJsonDatas = appJsonDatas;
	}
}
