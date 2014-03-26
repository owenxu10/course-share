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
import org.tjsse.courseshare.bean.Flash;
import org.tjsse.courseshare.dao.FlashDao;
import org.tjsse.courseshare.service.FlashService;

@Service
public class ImplFlashService implements FlashService {

  @Autowired
  private FlashDao flashDao;

  @Override
  public int importFlashes(String path) {
    int count = 0;
    try {
      FileReader reader = new FileReader(path);
      BufferedReader br = new BufferedReader(reader);
      String line = "";
      while ((line = br.readLine()) != null) {
        StringTokenizer st = new StringTokenizer(line, " ");
        Flash flash = new Flash();
        flash.setName(line);
        flash.setUrl(SWF_URL + st.nextToken());
        flash.setKnowledge(br.readLine());
        br.readLine();
        if (flashDao.save(flash) != null) {
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
  public List<Flash> findFlashes() {
    return flashDao.find();
  }

  @Override
  public List<Flash> findFlashes(String[] contents) {
    if (contents == null || contents.length == 0) {
      return findFlashes();
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
    return flashDao.find(condition.toString());

  }

  @Override
  public byte[] readFlash(String flashNo) {
    String path = String.format("%s%s.swf", SWF_PATH, flashNo);
    File flash = new File(path);
    if (!flash.exists()) {
      return null;
    }
    byte[] data = null;
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(path);
      data = new byte[(int) flash.length()];
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
