package com.caibo.weidu.util;



import android.os.AsyncTask;

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
	
	//onPreExecute方法用于在执行后台任务前做一些UI操作  
	@Override
	protected void onPreExecute() {
		
	}
	
	//doInBackground方法内部执行后台任务,不可在此方法内修改UI
	@Override 
	protected Object doInBackground(String... address) {
		try {
			if (address[0] == "string") {
				appDatas = okHttp.getAppData(url);
			}
			else if (address[0] == "bitmap") {
				appDatas = okHttp.getBitmap(url);
			}
			return appDatas;
//			Log.i("MyappDatas", appDatas);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 //onProgressUpdate方法用于更新进度信息
	@Override
	protected void onProgressUpdate(Integer... progress) {
		
	}
	
	//onPostExecute方法用于在执行完后台任务后更新UI,显示结果
	@Override
	protected void onPostExecute(Object result) {
		
		if (result != null) {
			onDataFinishedListener.onDataSuccessfully(result);
		}
	}
}
