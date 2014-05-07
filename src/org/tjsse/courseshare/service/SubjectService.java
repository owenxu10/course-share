package org.tjsse.courseshare.service;

import java.util.List;

import org.tjsse.courseshare.bean.Orders;
import org.tjsse.courseshare.bean.Subject;
import org.tjsse.courseshare.bean.Theme;

public interface SubjectService {

  public int importSubject(String path);
  
  public List<Subject> findSubjects();
  
  public List<Subject> findSubjects(String[] contents);
  
  public List<Subject> findSubjects(int themeid);
  
  public List<Theme> getTheme();
  
  public List<Orders> getOrder(int themeid, int userid);
  
  public void setOrder(int userid,int themeid,String order);
}
