package com.caibo.weidu.modle;

public class Account {
	
	private String accountName, a_wx_no, notes, accountId;
	private String accountCategory;
	private int imageId, scoreImageId;
	private String imageUrl;
	private String validReason;
	
	public Account( String accountName, String a_wx_no, String accountId, String notes, String imageUrl, int scoreImageId, String validReason) {
//		this.accountCategory = accountCategory;
		this.accountName = accountName;
		this.a_wx_no = a_wx_no;
		this.accountId = accountId;
		this.notes = notes;
//		this.imageId = imageId;
		this.imageUrl = imageUrl;
		this.scoreImageId = scoreImageId;
		this.validReason = validReason;
		
	}
	
	public String getCategory() {
		return accountCategory;
	}
	
	public String getName() {
		return accountName;
	}
	
	public String getWxno() {
		return a_wx_no;
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
//	public int getImageId() {
//		return imageId;
//	}
	
	public int getScoreImageId() {
		return scoreImageId;
	}
	
	public String getValidReason() {
		return validReason;
	}

}
