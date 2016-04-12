package com.caibo.weidu.util;


public class InitUrls {
	
	public String InitRegistUrl(String deviceId) {
		String registeUrl = "http://wx.xiyiyi.com/Mobile//UserAuth/registerUser?appcode=wxh&v=1.0&devicetype=android&deviceid=" + deviceId;
		return registeUrl;
	}
	
	public String InitAccountsUrl(String session, String deviceId, String ac_id, String pageNum) {
		String AccountsUrl = "http://wx.xiyiyi.com/Mobile//Account/accounts?appcode=wxh&v=1&devicetype=android&deviceid=" + deviceId + "&wxhsession=" + session + "&ac_id=" + ac_id + "&p=" + pageNum;
		return AccountsUrl;
	}
	
	public String InitAccountDetailUrl(String session, String deviceId, String a_id) {
		String AccountDetailUrl = "http://wx.xiyiyi.com/Mobile/Account/accountDetail?appcode=wxh&v=1&devicetype=android&format=clientdetailview_v1&deviceid=" + deviceId + "&wxhsession=" + session + "&a_id=" + a_id;
		return AccountDetailUrl;
	}
	
	public String InitAddFavoriteUrl(String session, String deviceId, String a_id) {
		String AddFavoriteUrl = "http://wx.xiyiyi.com/Mobile/AccountFavorite/addFavorite?appcode=wxh&v=1&devicetype=android&deviceid=" + deviceId + "&wxhsession=" + session + "&a_id=" + a_id;
		return AddFavoriteUrl;
	}
	
	public String InitRemoveFavoriteUrl(String session, String deviceId, String a_id) {
		String RemoveFavoriteUrl = "http://wx.xiyiyi.com/Mobile/AccountFavorite/removeFav?appcode=wxh&v=1&devicetype=android&deviceid=" + deviceId + "&wxhsession=" + session + "&a_id=" + a_id;
		return RemoveFavoriteUrl;
	}
	
	public String InitFavoriteListUrl(String session, String deviceId, String pageNum) {
		String FavoriteListUrl = "http://wx.xiyiyi.com/Mobile/AccountFavorite/favList?appcode=wxh&v=1&devicetype=android&deviceid=" + deviceId + "&wxhsession=" + session + "&p=" + pageNum;
		return FavoriteListUrl;
	}
}
