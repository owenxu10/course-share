package org.tjsse.courseshare.bean;

public class Image {
  private Integer id;
  private String name;
  private String knowledge;
  private String url;
  private Integer userid;
  private String username;
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
	return userid;
  }

  public void setUserId(Integer userid) {
	this.userid = userid;
  }

/**
 * @return the username
 */
public String getUserName() {
	return username;
}

/**
 * @param username the username to set
 */
public void setUserName(String username) {
	this.username = username;
}

}
