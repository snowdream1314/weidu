package com.caibo.weidu.modle;

import java.util.ArrayList;
import java.util.List;

public class ItemListView {
	
	private Account account;
	private List<Account> accountList = new ArrayList<Account>();
	
	public ItemListView(Account account) {
		
	}
	
	public List getList() {
		return accountList;
	}
	
//	public String getTitle() {
//		return ;
//	}
}
