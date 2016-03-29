package com.caibo.weidu.modle;

import java.util.ArrayList;
import java.util.List;

public class appJsonData {
	private String ac_id, ac_name, ac_seq, ac_is_parent, ac_parent_id, ac_need_geo, 
	ac_min_version, ac_has_more, ac_index_item_count;
	private childCat childCat;
	private recommendAccount recommendAccount;
	private List<recommendAccount> recommendAccounts = new ArrayList<recommendAccount>();
	private List<childCat> childCats = new ArrayList<childCat>();
	
	public String get_ac_id() {
		return ac_id;
	}
	
	public String get_ac_name() {
		return ac_name;
	}
	
	public String get_ac_seq() {
		return ac_seq;
	}
	
	public String get_ac_is_parent() {
		return ac_is_parent;
	}
	
	public String get_ac_parent_id() {
		return ac_parent_id;
	}
	
	public String get_ac_need_geo() {
		return ac_need_geo;
	}
	
	public String get_ac_min_version() {
		return ac_min_version;
	}
	
	public String get_ac_has_more() {
		return ac_has_more;
	}
	
	public String get_ac_index_item_count() {
		return ac_index_item_count;
	}
	
	public List<childCat> get_childCats() {
		return childCats;
	}
	
	public List<recommendAccount> get_recommendAccounts() {
		return recommendAccounts;
	}
	
	public class childCat {
		private String ac_id, ac_name, ac_seq, ac_is_parent, ac_parent_id, ac_need_geo, 
		ac_min_version, ac_has_more, ac_index_item_count;
		
		public String get_ac_id() {
			return ac_id;
		}
		
		public String get_ac_name() {
			return ac_name;
		}
		
		public String get_ac_seq() {
			return ac_seq;
		}
		
		public String get_ac_is_parent() {
			return ac_is_parent;
		}
		
		public String get_ac_parent_id() {
			return ac_parent_id;
		}
		
		public String get_ac_need_geo() {
			return ac_need_geo;
		}
		
		public String get_ac_min_version() {
			return ac_min_version;
		}
		
		public String get_ac_has_more() {
			return ac_has_more;
		}
		
		public String get_ac_index_item_count() {
			return ac_index_item_count;
		}
	}
	
	public class recommendAccount {
		private String ar_seq, ar_badge, ar_id, a_id, a_log, a_wx_no, a_name, a_is_valided;
		
//		public void set_ar_seq(String ar_seq) {
//			this.ar_seq = ar_seq;
//		}
		
		public String get_ar_seq() {
			return ar_seq;
		}
		
//		public void set_ar_badge(String ar_badge) {
//			this.ar_badge = ar_badge;
//		}
		
		public String get_ar_badge() {
			return ar_badge;
		}
		
//		public void set_ar_id(String ar_id) {
//			this.ar_id = ar_id;
//		}
		
		public String get_ar_id() {
			return ar_id;
		}
		
		public String get_a_id() {
			return a_id;
		}
		
		public String get_a_log() {
			return a_log;
		}
		
		public String get_a_wx_no() {
			return a_wx_no;
		}
		
		public String get_a_name() {
			return a_name;
		}
		
		public String get_a_is_valided() {
			return a_is_valided;
		}
	}
}
