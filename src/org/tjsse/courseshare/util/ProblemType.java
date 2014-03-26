package org.tjsse.courseshare.util;

public enum ProblemType {
  CONCEPT("概念题", 1), 
  BLANKFILL("填空题", 2), 
  CHOICE("选择题", 3), 
  QUESTION("问答题", 4), 
  INTEGRATE("综合题", 5);
  
  private String name;
  private int index;
  
  private ProblemType(String name, int index) {
    this.name = name;
    this.index = index;
  }
  
  public final String getName() {
    return this.name;
  }
  
  public final int getIndex() {
    return this.index;
  }
  
  public static final String name2Type(String name) {
    for (ProblemType pt : ProblemType.values())
      if(pt.name.equals(name))
        return pt.toString();
    return null;
  }
}
