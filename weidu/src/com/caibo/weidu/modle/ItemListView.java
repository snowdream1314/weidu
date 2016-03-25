package com.caibo.weidu.modle;

import java.util.ArrayList;
import java.util.List;

public class ItemListView {
	
	private String Title;
	private ItemGridView itemGridView;
	
	public ItemListView(String title, ItemGridView itemGridView) {
		this.Title = title;
		this.itemGridView = itemGridView;
	}
	
//	public List getList() {
//		return accountList;
//	}
	
	public String getTitle() {
		return Title;
	}
}
