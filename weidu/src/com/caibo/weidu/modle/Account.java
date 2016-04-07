package com.caibo.weidu.modle;

public class Account {
	
	private String accountName, a_wx_no, notes;
	private String accountCategory;
	private int imageId, scoreImageId;
	
	public Account( String accountName, String a_wx_no, String notes, int imageId, int scoreImageId) {
//		this.accountCategory = accountCategory;
		this.accountName = accountName;
		this.a_wx_no = a_wx_no;
		this.notes = notes;
		this.imageId = imageId;
		this.scoreImageId = scoreImageId;
		
		
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
	
	public String getNotes() {
		return notes;
	}
	
	public int getImageId() {
		return imageId;
	}
	
	public int getScoreImageId() {
		return scoreImageId;
	}

}
