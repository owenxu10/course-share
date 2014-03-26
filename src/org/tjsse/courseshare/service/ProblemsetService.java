package org.tjsse.courseshare.service;

import java.util.List;

import org.tjsse.courseshare.bean.DSPicture;
import org.tjsse.courseshare.bean.Problem;
import org.tjsse.courseshare.util.Config;

public interface ProblemsetService {

  public boolean convertDoc2Html(String docPath, String htmlPath);

  public int splitProblem(String htmlPath);
  
  public void removeAll();

//  public List<Problem> findProblems();

//  public List<Problem> findProblems(String[] contents);

  public List<Problem> findProblems(String[] types, Integer[] diffs,
      String[] contents, String[] knows, int offset);

//  public List<Problem> findProblemsByTypes(String[] types);
  
  public DSPicture readPicture(int id);
  
  public byte[] makePaper(Integer[] pids);
}
