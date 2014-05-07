package org.tjsse.courseshare.bean;

public class Orders {
	private Integer order_id;
	private String orderlist;
	private Integer userid;
	private Integer theme_id;
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer orderid) {
		this.order_id = orderid;
	}
	
	public String getorderlist() {
		return orderlist;
	}
	public void setorderlist(String order) {
		this.orderlist = order;
	}
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getTheme_id() {
		return theme_id;
	}
	public void setTheme_id(Integer theme_id) {
		this.theme_id = theme_id;
	}
}
