package org.tjsse.courseshare.service;

import java.util.List;

import org.tjsse.courseshare.bean.Flash;
import org.tjsse.courseshare.util.Config;

public interface FlashService {

  public static final String ROOT_PATH = Config.ROOT_PATH;
  public static final String SWF_PATH = ROOT_PATH + "swf/";
  public static final String SWF_URL = "/course-share/flash/read/";

  public int importFlashes(String path);

  public List<Flash> findFlashes();

  public List<Flash> findFlashes(String[] contents);
  
  public byte[] readFlash(String flashNo);

}
