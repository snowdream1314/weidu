package com.caibo.weidu.modle;

import java.util.ArrayList;
import java.util.List;


public class appJsonData {
	private String ac_id, ac_name, ac_seq, ac_is_parent, ac_parent_id, ac_need_geo, 
	ac_min_version, ac_has_more, ac_index_item_count;
	private childCat childCat;
	private recommendAccount recommendAccount;
	private List<recommendAccount> recommendAccounts;
	private List<childCat> childCats ;
	
	public void set_ac_id(String ac_id) {
		this.ac_id = ac_id;
	}
	
	public String get_ac_id() {
		return ac_id;
	}
	
	public void set_ac_name(String ac_name) {
		this.ac_name = ac_name;
	}
	
	public String get_ac_name() {
		return ac_name;
	}
	
	public void set_ac_seq(String ac_seq) {
		this.ac_seq = ac_seq;
	}
	
	public String get_ac_seq() {
		return ac_seq;
	}
	
	public void set_ac_is_parent(String ac_is_parent) {
		this.ac_is_parent = ac_is_parent;
	}
	
	public String get_ac_is_parent() {
		return ac_is_parent;
	}
	
	public void set_ac_parent_id(String ac_parent_id) {
		this.ac_parent_id = ac_parent_id;
	}
	
	public String get_ac_parent_id() {
		return ac_parent_id;
	}
	
	public void set_ac_need_geo(String ac_need_geo) {
		this.ac_need_geo = ac_need_geo;
	}
	
	public String get_ac_need_geo() {
		return ac_need_geo;
	}
	
	public void set_ac_min_version(String ac_min_version) {
		this.ac_min_version = ac_min_version;
	}
	
	public String get_ac_min_version() {
		return ac_min_version;
	}
	
	public void set_ac_has_more(String ac_has_more) {
		this.ac_has_more = ac_has_more;
	}
	
	public String get_ac_has_more() {
		return ac_has_more;
	}
	
	public void set_ac_index_item_count(String ac_index_item_count) {
		this.ac_index_item_count = ac_index_item_count;
	}
	
	public String get_ac_index_item_count() {
		return ac_index_item_count;
	}
	
	public void setListChildCat(List<childCat> childCats) {
		this.childCats = childCats;
	}
	
	public List<childCat> get_childCats() {
		return childCats;
	}
	
	public void setListrecommendAccount(List<recommendAccount> recommendAccounts) {
		this.recommendAccounts = recommendAccounts;
	}
	
	public List<recommendAccount> get_recommendAccounts() {
		return recommendAccounts;
	}
	
	public class childCat {
		private String ac_id, ac_name, ac_seq, ac_is_parent, ac_parent_id, ac_need_geo, 
		ac_min_version, ac_has_more, ac_index_item_count;
		
		public void set_ac_id(String ac_id) {
			this.ac_id = ac_id;
		}
		
		public String get_ac_id() {
			return ac_id;
		}
		
		public void set_ac_name(String ac_name) {
			this.ac_name = ac_name;
		}
		
		public String get_ac_name() {
			return ac_name;
		}
		
		public void set_ac_seq(String ac_seq) {
			this.ac_seq = ac_seq;
		}
		
		public String get_ac_seq() {
			return ac_seq;
		}
		
		public void set_ac_is_parent(String ac_is_parent) {
			this.ac_is_parent = ac_is_parent;
		}
		
		public String get_ac_is_parent() {
			return ac_is_parent;
		}
		
		public void set_ac_parent_id(String ac_parent_id) {
			this.ac_parent_id = ac_parent_id;
		}
		
		public String get_ac_parent_id() {
			return ac_parent_id;
		}
		
		public void set_ac_need_geo(String ac_need_geo) {
			this.ac_need_geo = ac_need_geo;
		}
		
		public String get_ac_need_geo() {
			return ac_need_geo;
		}
		
		public void set_ac_min_version(String ac_min_version) {
			this.ac_min_version = ac_min_version;
		}
		
		public String get_ac_min_version() {
			return ac_min_version;
		}
		
		public void set_ac_has_more(String ac_has_more) {
			this.ac_has_more = ac_has_more;
		}
		
		public String get_ac_has_more() {
			return ac_has_more;
		}
		
		public void set_ac_index_item_count(String ac_index_item_count) {
			this.ac_index_item_count = ac_index_item_count;
		}
		
		public String get_ac_index_item_count() {
			return ac_index_item_count;
		}
	}
	
	public class recommendAccount {
		private String ar_seq, ar_badge, ar_id, a_id, a_log, a_wx_no, a_name, a_is_valided;
		
		public void set_ar_seq(String ar_seq) {
			this.ar_seq = ar_seq;
		}
		
		public String get_ar_seq() {
			return ar_seq;
		}
		
		public void set_ar_badge(String ar_badge) {
			this.ar_badge = ar_badge;
		}
		
		public String get_ar_badge() {
			return ar_badge;
		}
		
		public void set_ar_id(String ar_id) {
			this.ar_id = ar_id;
		}
		
		public String get_ar_id() {
			return ar_id;
		}
		
		public void set_a_id(String a_id) {
			this.a_id = a_id;
		}
		
		public String get_a_id() {
			return a_id;
		}
		
		public void set_a_log(String a_log) {
			this.a_log = a_log;
		}
		
		public String get_a_log() {
			return a_log;
		}
		
		public void set_a_wx_no(String a_wx_no) {
			this.a_wx_no = a_wx_no;
		}
		
		public String get_a_wx_no() {
			return a_wx_no;
		}
		
		public void set_a_name(String a_name) {
			this.a_name = a_name;
		}
		
		public String get_a_name() {
			return a_name;
		}
		
		public void set_a_is_valided(String a_is_valided) {
			this.a_is_valided = a_is_valided;
		}
		
		public String get_a_is_valided() {
			return a_is_valided;
		}
	}
}
