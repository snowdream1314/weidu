package com.caibo.weidu.util;

import org.json.JSONObject;

import android.content.SharedPreferences;

public class SaveDataInPref {
	
//	private String Key, Data;
	private SharedPreferences pref;
	
	public SaveDataInPref(SharedPreferences pref) {
//		this.Data = Data;
//		this.Key = Key;
		this.pref = pref;
	}
	
	public void SaveData(String Key, String Data) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(Key, Data);
		editor.commit();
	}
	
	public String GetData(String Key, String defaultdata) {
		return pref.getString(Key, defaultdata);
	}
	
	public String GetSession(String Key, String defaultdata) {
		String Data = pref.getString(Key, defaultdata);
		try {
			JSONObject jsonObject = new JSONObject(Data);
			return jsonObject.getJSONObject("data").getString("session");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
