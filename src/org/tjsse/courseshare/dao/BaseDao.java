package org.tjsse.courseshare.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao {
  
  public <E extends Object> E read(Integer id);
  
  public <E extends Object> E read(Integer id, String[] fields);

  public <E extends Object> List<E> read(Integer[] ids);

  // Select all data from table. (CAUTIOUS to use)
  public <E extends Object> List<E> find();
  
  // Select all data under given condition from table.
  public <E extends Object> List<E> find(String condition);

  // Select some data under given condition from table.
  // Number of results is up to $LIMIT.
  // IDs of results starts from $offset.
  public <E extends Object> List<E> find(String condition, int offset);

  // Select all data of given fields under given condition from table.
  public <E extends Object> List<E> find(String condition, String[] fields);
    
  // Select some data of given fields under given condition from table. 
  // Number of results is up to $LIMIT.
  // IDs of results starts from $offset.
  public <E extends Object> List<E> find(String condition, String[] fields, int offset);
  
  public List<Map<String, Object>> query(String sql);
  
  public <E extends Object> E save(E bean);
  
  public void clear();

}
