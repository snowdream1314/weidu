package com.caibo.weidu.modle;

public class MoreItem {
	private String itemName;
	private int imageId;
	private int imageTagId;
	
	public MoreItem(String itemName, int imageId, int imageTagId) {
		this.imageId = imageId;
		this.imageTagId = imageTagId;
		this.itemName = itemName;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public int getImageId() {
		return imageId;
	}
	
	public int getImageTagId() {
		return imageTagId;
	}
}
