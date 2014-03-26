package org.tjsse.courseshare.service;

import java.util.List;

import org.tjsse.courseshare.bean.Subject;

public interface SubjectService {

  public int importSubject(String path);
  
  public List<Subject> findSubjects();
  
  public List<Subject> findSubjects(String[] contents);
}
