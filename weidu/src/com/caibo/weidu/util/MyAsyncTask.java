package com.caibo.weidu.util;



import android.os.AsyncTask;
import android.util.Log;

public class MyAsyncTask extends AsyncTask<String, Integer, Object> {
	
	private Object appDatas;
	private String url;
	
	onDataFinishedListener onDataFinishedListener;
	
	public MyAsyncTask(String address) {
		this.url = address;
	}
	
	public void setOnDataFinishedListener(onDataFinishedListener onDataFinishedListener) {
		this.onDataFinishedListener = onDataFinishedListener;
	}
	
	//onPreExecute����������ִ�к�̨����ǰ��һЩUI����  
	@Override
	protected void onPreExecute() {
		
	}
	
	//doInBackground�����ڲ�ִ�к�̨����,�����ڴ˷������޸�UI
	@Override 
	protected Object doInBackground(String... address) {
		for (int i = 0; i < 3; i++) {
			
			try {
				if (address[0] == "string") {
					appDatas = okHttp.getAppData(url);
				}
				else if (address[0] == "bitmap") {
					appDatas = okHttp.getBitmap(url);
				}
				Log.i("MyappDatas", appDatas.toString());
				if (appDatas.toString().length() == 0) {
					continue;
				}
				return appDatas;
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return null;
		
	}
	
	 //onProgressUpdate�������ڸ��½�����Ϣ
	@Override
	protected void onProgressUpdate(Integer... progress) {
		
	}
	
	//onPostExecute����������ִ�����̨��������UI,��ʾ���
	@Override
	protected void onPostExecute(Object result) {
		
		if (result != null) {
			onDataFinishedListener.onDataSuccessfully(result);
		}
	}
}
