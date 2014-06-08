package org.tjsse.courseshare.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.tjsse.courseshare.bean.Image;
import org.tjsse.courseshare.util.Config;

public interface ImageService {

  public static final String ROOT_PATH = Config.ROOT_PATH;
  public static final String IMG_PATH = ROOT_PATH + "image/";
  public static final String IMG_URL = "/course-share/image/read/";
  public static final String IMG_SUFFIX = "jpg";

  public int importImages(String path);

  public List<Image> findImages();

  public List<Image> findImages(String[] contents);
  
  public byte[] readImage(String imageNo);
  
  public boolean uploadImage(String resourceName, String resourceknowledge, MultipartFile file);

}
