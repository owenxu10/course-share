package org.tjsse.courseshare.bean;

public class Resource {
  
  public static final String DOC = "document";
  public static final String SWF = "flash";
  public static final String ZIP = "zipfile";
  public static final String URL = "url";
  public static final String JPG = "picture";
  
  private Integer id;
  private String name;
  private String description;
  private String mediaType;
  private String fileFormat;
  private Double fileSize;
  private Integer isSubject;
  private String url;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getMediaType() {
    return mediaType;
  }

  public void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }

  public String getFileFormat() {
    return fileFormat;
  }

  public void setFileFormat(String fileFormat) {
    this.fileFormat = fileFormat;
  }

  public Double getFileSize() {
    return fileSize;
  }

  public void setFileSize(Double fileSize) {
    this.fileSize = fileSize;
  }



  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
