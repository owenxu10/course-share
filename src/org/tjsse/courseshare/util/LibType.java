package org.tjsse.courseshare.util;

public enum LibType {
  PROBLEMSET("题库", 1), SUBJECT("专题库", 2), FLASH("动画库", 3), IMAGE("资源库", 4);
  
  private String name;
  private int index;
  
  private LibType(String name, int index) {
    this.name = name;
    this.index = index;
  }
  
  public static final String getName(int index) {
    for (LibType lt : LibType.values())
      if(lt.getIndex() == index)
        return lt.getName();
    return null;
  }
  
  public final String getName() {
    return this.name;
  }
  
  public final int getIndex() {
    return this.index;
  }
  
  public final String isEqual(LibType lt, String result) {
    return this.equals(lt) ? result : "";
  }
}
