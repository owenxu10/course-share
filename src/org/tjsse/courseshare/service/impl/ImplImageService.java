package org.tjsse.courseshare.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tjsse.courseshare.bean.Image;
import org.tjsse.courseshare.dao.ImageDao;
import org.tjsse.courseshare.service.ImageService;

@Service
public class ImplImageService implements ImageService {

  @Autowired
  private ImageDao imageDao;

  @Override
  public int importImages(String path) {
    int count = 0;
    try {
      FileReader reader = new FileReader(path);
      BufferedReader br = new BufferedReader(reader);
      String line = "";
      while ((line = br.readLine()) != null) {
        StringTokenizer st = new StringTokenizer(line, " ");
        Image image = new Image();
        image.setName(line);
        image.setUrl(IMG_URL + st.nextToken());
        image.setKnowledge(br.readLine());
        br.readLine();
        if (imageDao.save(image) != null) {
          count++;
        }
      }
      br.close();
      reader.close();
    } catch (IOException e) {
      return count;
    }
    return count;
  }

  @Override
  public List<Image> findImages() {
    return imageDao.find();
  }

  @Override
  public List<Image> findImages(String[] contents) {
    if (contents == null || contents.length == 0) {
      return findImages();
    }
    StringBuffer condition = new StringBuffer();
    for (int i = 0; i < contents.length; i++) {
      if (contents[i] == null || contents[i].isEmpty())
        continue;
      if (condition.length() != 0) {
        condition.append(" AND ");
      }
      condition.append(String.format(
          "(name LIKE '%%%s%%' OR knowledge LIKE '%%%s%%')", contents[i],
          contents[i]));
    }
    return imageDao.find(condition.toString());

  }

  @Override
  public byte[] readImage(String imageNo) {
    String path = String.format("%s%s.%s", IMG_PATH, imageNo, IMG_SUFFIX);
    File image = new File(path);
    if (!image.exists()) {
      return null;
    }
    byte[] data = null;
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(path);
      data = new byte[(int) image.length()];
      fis.read(data);
    } catch (IOException e) {
    } finally {
      try {
        if (fis != null)
          fis.close();
      } catch (IOException e) {
        return null;
      }
    }
    return data;
  }

}
