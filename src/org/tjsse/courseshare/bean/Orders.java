package org.tjsse.courseshare.bean;

public class Orders {
	private Integer order_id;
	private String order;
	private Integer userid;
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer orderid) {
		this.order_id = orderid;
	}
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
}
