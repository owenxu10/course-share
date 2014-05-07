package org.tjsse.courseshare.bean;

public class Subject {

  private Integer subject_id;
  private String description;
  private String title;
  private String url;
  private Integer themeid;
  
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

/**
 * @return the title
 */
public String getTitle() {
	return title;
}

/**
 * @param title the title to set
 */
public void setTitle(String title) {
	this.title = title;
}

/**
 * @return the subjectid
 */
public Integer getSubject_id() {
	return subject_id;
}

/**
 * @param subjectid the subjectid to set
 */
public void setSubject_id(Integer subject_id) {
	this.subject_id = subject_id;
}

/**
 * @return the themeid
 */
public Integer getThemeid() {
	return themeid;
}

/**
 * @param themeid the themeid to set
 */
public void setThemeid(Integer themeid) {
	this.themeid = themeid;
}


}
