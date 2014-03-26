package org.tjsse.courseshare.util;

import java.io.File;

public final class Config {
  
  public static String SERVER_URL = "http://localhost:8080";
  public static String ROOT_URI = "/course-share";
  public static String ROOT_PATH = null;
  static {
    String os = System.getProperty("os.name").toLowerCase();
    if (os.startsWith("windows")) {
      ROOT_PATH = "D:/course-share";
    } else {
      ROOT_PATH = "/var/tmp/course-share";
    }
    mkdir(ROOT_PATH);
  }

  public final static String PROBLEM_PATH = ROOT_PATH + "/problems";
  public final static String PROBLEM_RES_PATH = PROBLEM_PATH + "/resources";
  public final static String TMP_PATH = ROOT_PATH + "/tmp";
  static {
    mkdir(PROBLEM_PATH);
    mkdir(PROBLEM_RES_PATH);
    mkdir(TMP_PATH);
  }

  private static void mkdir(String path) {
    File dir = new File(path);
    if (!dir.exists() || !dir.isDirectory()) {
      dir.mkdir();
    }
  }

}
