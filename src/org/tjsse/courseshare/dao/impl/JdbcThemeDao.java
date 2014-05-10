package org.tjsse.courseshare.dao.impl;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.stereotype.Repository;
import org.tjsse.courseshare.dao.ThemeDao;

@Repository
public class JdbcThemeDao extends JdbcBaseDao implements ThemeDao {

	 @Override
	  public void delete(int id) {
	    String sql = String.format("DELETE FROM  %s WHERE theme_id=%d;", table, id);
	    System.out.println(sql);
	    jdbcTemplate.execute(sql);
	  }
	 
	 protected Object setBeanId(Object bean, Integer id) {
		    try {
		      Method m = bean.getClass().getMethod("settheme_id", Integer.class);
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
}
