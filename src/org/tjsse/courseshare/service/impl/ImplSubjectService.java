package org.tjsse.courseshare.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tjsse.courseshare.bean.Orders;
import org.tjsse.courseshare.bean.Subject;
import org.tjsse.courseshare.bean.Theme;
import org.tjsse.courseshare.dao.OrdersDao;
import org.tjsse.courseshare.dao.SubjectDao;
import org.tjsse.courseshare.dao.ThemeDao;
import org.tjsse.courseshare.service.SubjectService;
import org.tjsse.courseshare.util.Config;

@Service
public class ImplSubjectService implements SubjectService {

  @Autowired
  private SubjectDao subjectDao;
  @Autowired
  private OrdersDao orderDao;
  @Autowired
  private ThemeDao themeDao;
  
  @Override
  public int importSubject(String path) {
    int count = 0;
    try {
      FileReader reader = new FileReader(path);
      BufferedReader br = new BufferedReader(reader);
      String line = br.readLine();
      while ((line = br.readLine()) != null) {
        StringTokenizer st = new StringTokenizer(line, ",");
        Subject subject = new Subject();
        if (!st.hasMoreTokens())
          continue;
        subject.setTitle(st.nextToken());
        if (!st.hasMoreTokens())
          continue;
        subject.setDescription(st.nextToken());
        if (!st.hasMoreTokens())
          continue;
        subject.setUrl(st.nextToken());
        if (subjectDao.save(subject) != null)
          count++;
      }
      br.close();
      reader.close();
    } catch (IOException e) {
      return count;
    }
    return count;
  }

  
  @Override
  public List<Subject> findSubjects() {
    return subjectDao.find();
  }

  public List<Subject> findSubjects(String[] contents) {
    if (contents == null || contents.length == 0) {
      return findSubjects();
    }
    StringBuffer condition = new StringBuffer();
    for (int i = 0; i < contents.length; i++) {
      if (contents[i] == null || contents[i].isEmpty())
        continue;
      if (condition.length() != 0) {
        condition.append(" AND ");
      }
      condition
          .append(String
              .format(
                  "(name LIKE '%%%s%%' OR description LIKE '%%%s%%' OR subject_name LIKE '%%%s%%')",
                  contents[i], contents[i], contents[i]));
    }
    return subjectDao.find(condition.toString());
  }

  public static void main(String[] args) {
    ImplSubjectService iss = new ImplSubjectService();
    iss.importSubject(Config.ROOT_PATH + "subject1.txt");
  }

	@Override
	public List<Subject> findSubjects(int themeid) {
		    StringBuffer condition = new StringBuffer();
		      condition
		          .append(String
		              .format(
		                  "(themeid = '%d')",
		                  themeid));
		    return subjectDao.find(condition.toString());
	}
	
	@Override
	public List<Orders> getOrder(int themeid, int userid) {
		    StringBuffer condition = new StringBuffer();
		      condition
		          .append(String
		              .format(
		                  "(theme_id ='%d' AND userid = '%d')",
		                  themeid, userid));
		    return orderDao.find(condition.toString());
	}

	@Override
	public List<Theme> getTheme() {
		return themeDao.find();
	}


	  @Override
	  public void setOrder(int userid,int themeid,String orderlist){
		  int findsize;
		  Orders orders=new Orders();
		  orders.setorderlist(orderlist);
		  orders.setUserid(userid);
		  orders.setTheme_id(themeid);
		  
		  
		  //find if there exists
		  List<Orders> ordersfind = orderDao.find("userid="+userid+" and theme_id="+themeid);
		  findsize = ordersfind.size();
		  if(findsize!=0){
		  //update
			  orders = ordersfind.get(0);
			  orders.setorderlist(orderlist);
			  orders.setUserid(userid);
			  orders.setTheme_id(themeid);
			  orderDao.update(orders);
		  }else
		  //insert
			  orderDao.save(orders); 
		 }
	  
	  @Override
	  public int importSubject(String title,int theme_id,String description,String url){
		  Subject subject=new Subject();
		  subject.setDescription(description);
		  subject.setThemeid(theme_id);
		  subject.setTitle(title);
		  subject.setUrl(url);
		  
		  subjectDao.save(subject);
		  
		  List<Subject> subjects = subjectDao.find("description='"+description+"' and themeid='"+theme_id+"' and url='"+url+"' and title='"+title+"';");
		  return subjects.get(0).getSubject_id();
		  }
	  
	  @Override
	  public void importOrder(int subject_id, int theme_id,int user_id){
		  Orders orders=new Orders();
		  List<Orders> ordersfind = orderDao.find("userid="+user_id+" and theme_id="+theme_id);
		  int findsize = ordersfind.size();
		  if(findsize!=0){
		  //insert this to the end
			  orders = ordersfind.get(0);
			  String orderlist = orders.getorderlist();
			  orders.setorderlist(orderlist+subject_id+"/");
			  orderDao.update(orders);
		  }else{
		  //first
			  orders.setorderlist(subject_id+"/");
			  orders.setUserid(user_id);
			  orders.setTheme_id(theme_id);
			  orderDao.save(orders); 
		  }
	  }
	  
	  @Override
	  public void deleteTheme(int themeid){
		  themeDao.delete(themeid);
	  }
	  
	  @Override
	  public void addTheme(String name){
		  Theme theme = new Theme();
		  theme.setName(name);
		  themeDao.save(theme);
	  }
}
