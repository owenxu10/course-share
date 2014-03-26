package org.tjsse.courseshare.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.tjsse.courseshare.dao.BaseDao;

@Repository
@SuppressWarnings("unchecked")
public class JdbcBaseDao implements BaseDao {

  private static final String SPLITER = "_";
  private static final String BEAN_PACKAGE = "org.tjsse.courseshare.bean";
  private static final String DAO_PREFIX = "Jdbc";
  private static final String DAO_SUFFIX = "Dao";
  private static final int LIMIT = 20;

  protected String bean;
  protected String table;
  protected JdbcTemplate jdbcTemplate;

  public JdbcBaseDao() {
    if (bean == null || "".equals(bean)) {
      bean = getClass().getSimpleName().replace(DAO_PREFIX, "")
          .replace(DAO_SUFFIX, "");
    }
    if (table == null || "".equals(table)) {
      table = mapBean2Table(bean);
    }
  }

  @Autowired
  protected void setJdbcTemplate(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  protected String mapBean2Table(String beanName) {
    if (beanName == null || beanName.equals("")) {
      return "";
    }
    String tableName = "";
    for (int i = 0; i < beanName.length(); i++) {
      if (Character.isUpperCase(beanName.charAt(i)) && i != 0) {
        tableName += SPLITER;
      }
      tableName += beanName.charAt(i);
    }
    return tableName.toLowerCase();
  }

  protected String mapTable2Bean(String tableName) {
    if (tableName == null || tableName.equals("")) {
      return "";
    }
    String[] names = tableName.toLowerCase().split(SPLITER);
    String beanName = "";
    for (String name : names) {
      beanName += Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
    return beanName;
  }

  protected String makeAttrs(String[] fields) {
    String attrs = "";
    if (fields == null || fields.length == 0) {
      attrs = "*";
    } else {
      for (String f : fields) {
        attrs += mapBean2Table(f) + ",";
      }
      attrs = attrs.substring(0, attrs.length() - 1);
    }
    return attrs.toUpperCase();
  }

  protected <E extends Object> RowMapper<E> getMapper() {
    return new RowMapper<E>() {
      @Override
      public E mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
          Class<E> c = (Class<E>) Class.forName(BEAN_PACKAGE + "." + bean);
          E bean = c.newInstance();
          Method[] methods = c.getMethods();
          for (Method m : methods) {
            if (!m.getName().startsWith("set")) {
              continue;
            }
            String attrName = mapBean2Table(m.getName().substring(3));
            String type = m.getParameterTypes()[0].getName();
            type = type.substring(type.lastIndexOf(".") + 1);
            Object value = null;
            switch (type) {
            case "String":
              value = rs.getString(attrName);
              break;
            case "Integer":
              value = rs.getInt(attrName);
              break;
            case "Date":
              value = new Date(rs.getTimestamp(attrName).getTime());
              break;
            case "Double":
              value = rs.getDouble(attrName);
              break;
            default:
              break;
            }
            m.invoke(bean, value);
          }
          return bean;
        } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (InstantiationException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IllegalArgumentException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        return null;
      }
    };
  }
  
  protected Object setBeanId(Object bean, Integer id) {
    try {
      Method m = bean.getClass().getMethod("setId", Integer.class);
      m.invoke(bean, id);
      return bean;
    } catch (NoSuchMethodException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  
  /***** Public Interfaces *****/

  @Override
  public <E extends Object> E read(Integer id) {
    if (id == null)
      return null;
    String sql = String.format("SELECT * FROM %s WHERE id='%s';", table, id);
    return (E) jdbcTemplate.queryForObject(sql, getMapper());
  }

  @Override
  public <E extends Object> E read(Integer id, String[] fields) {
    if (fields == null || fields.length == 0)
      return read(id);
    String sql = String.format("SELECT %s FROM %s WHERE id='%s';",
        makeAttrs(fields), table, id);
    return (E) jdbcTemplate.queryForObject(sql, getMapper());
  }
  
  @Override
  public <E extends Object> List<E> read(Integer[] ids) {
    if (ids == null || ids.length <= 0) {
      return new ArrayList<E>();
    }
    StringBuffer sql = new StringBuffer();
    sql.append(String.format("SELECT * FROM %s WHERE", table));
    for(int i=0;i<ids.length;i++){
      if(i > 0){
        sql.append(" OR ");
      }
      sql.append(String.format("(id='%d')", ids[i]));
    }
    System.out.println(sql);
    return (List<E>) jdbcTemplate.query(sql.toString(), getMapper());
  }
  
  @Override
  public List<Map<String, Object>> query(String sql) {
    System.out.println(sql);
    return jdbcTemplate.queryForList(sql);
  }
  
  @Override
  public void clear() {
    String sql = String.format("TRUNCATE TABLE %s;", table);
    System.out.println(sql);
    jdbcTemplate.execute(sql);
  }

  @Override
  public <E extends Object> E save(E bean) {
    StringBuffer attrs = new StringBuffer();
    StringBuffer values = new StringBuffer();
    Method[] methods = bean.getClass().getMethods();
    for (Method m : methods) {
      if (!m.getName().startsWith("get") || m.getName().equals("getClass")) {
        continue;
      }
      String attr = mapBean2Table(m.getName().substring(3));
      Object value = null;
      try {
        value = m.invoke(bean);
      } catch (Exception e) {
        System.out.println(attr + " cannot be saved.");
        continue;
      }
      if (value == null) {
        continue;
      }
      attrs.append(attr + ",");
      values.append(String.format("\"%s\",", value.toString().replaceAll("\"", "'")));
    }
    if (attrs.length() == 0 || values.length() == 0) {
      return null;
    }
    attrs.deleteCharAt(attrs.length() - 1);
    values.deleteCharAt(values.length() - 1);
    final String sql = String.format("INSERT INTO %s(%s) VALUES (%s);",
        this.table, attrs, values);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    int result = 0;
    try {
      result = this.jdbcTemplate.update(new PreparedStatementCreator() {
        @Override
        public PreparedStatement createPreparedStatement(Connection connection)
            throws SQLException {
          PreparedStatement ps = connection.prepareStatement(sql,
              new String[] { "id" });
          return ps;
        }
      }, keyHolder);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    if (result == 1) {
      return (E) setBeanId(bean, keyHolder.getKey().intValue());
    }
    return null;
  }

  @Override
  public <E extends Object> List<E> find() {
    String sql = String.format("SELECT * FROM %s;", table);
    System.out.println(sql);
    return (List<E>) jdbcTemplate.query(sql, getMapper());
  }

  @Override
  public <E extends Object> List<E> find(String condition) {
    if (condition == null || condition.isEmpty())
      return find();
    String sql = String.format("SELECT * FROM %s WHERE %s;", table, condition);
    System.out.println(sql);
    return (List<E>) jdbcTemplate.query(sql, getMapper());
  }
  
  @Override
  public <E extends Object> List<E> find(String condition, int offset) {
    String sql = String.format("SELECT * FROM %s WHERE id>=%d", table, offset);
    if (condition != null && !condition.isEmpty()) {
      sql += String.format(" AND (%s)", condition);
    }
    sql += String.format(" LIMIT %d;", LIMIT);
    System.out.println(sql);
    return (List<E>) jdbcTemplate.query(sql, getMapper());
  }

  @Override
  public <E extends Object> List<E> find(String condition, String[] fields) {
    if (fields == null || fields.length == 0)
      return find(condition);
    String sql = String.format("SELECT %s FROM %s WHERE %s;",
        makeAttrs(fields), table, condition);
    System.out.println(sql);
    return (List<E>) jdbcTemplate.query(sql, getMapper());
  }

  @Override
  public <E extends Object> List<E> find(String condition, String[] fields, int offset) {
    // TODO Auto-generated method stub
    return null;
  }
}