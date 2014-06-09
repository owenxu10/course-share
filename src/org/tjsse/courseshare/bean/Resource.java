package org.tjsse.courseshare.bean;

public class Resource {
	 private Integer id;
	  private String name;
	  private String knowledge;
	  private String url;
	  private String address;
	  private Integer user_id;
	  private String user_name;
	  
	  public Integer getId() {
	    return id;
	  }

	  public void setId(Integer id) {
	    this.id = id;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public String getKnowledge() {
	    return knowledge;
	  }

	  public void setKnowledge(String knowledge) {
	    this.knowledge = knowledge;
	  }

	  public String getUrl() {
	    return url;
	  }

	  public void setUrl(String url) {
	    this.url = url;
	  }
	  
	  public Integer getUserId() {
		return user_id;
	  }

	  public void setUserId(Integer userid) {
		this.user_id = userid;
	  }

	/**
	 * @return the username
	 */
	public String getUserName() {
		return user_name;
	}

	/**
	 * @param username the username to set
	 */
	public void setUserName(String username) {
		this.user_name = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
