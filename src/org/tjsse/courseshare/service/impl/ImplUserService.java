package org.tjsse.courseshare.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tjsse.courseshare.bean.Orders;
import org.tjsse.courseshare.bean.User;
import org.tjsse.courseshare.dao.UserDao;
import org.tjsse.courseshare.service.UserService;


@Service
public class ImplUserService implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public User registerUser(String username, String password, String email) {
		 System.out.println(username+"/"+password+"/"+email);
		 User user = new User();
		 User result;
		 user.setUsername(username);
		 user.setPassword(password);
		 user.setEmail(email);
		 
		 result=userDao.save(user);
		 
			 return result;
		 
		
	}

	
	public int loginUser(String username, String password){
		
		String condition="username='"+username+"' and password='"+password+"'";
		
		List<User> result = userDao.find(condition);
		
		
		if(result.size()==0)
			return 0;
		else return result.get(0).getId();
		
		
	}
	
	public boolean checkUser(String username){
		
		String condition="username='"+username+"'";
		
		List<User> result = userDao.find(condition);
		
		if(result.size()==0)
			return true;
		else return false;
	}
	
	public boolean checkModifyUser(String username){
		
		String condition="username='"+username+"'";
		
		List<User> result = userDao.find(condition);
		
		if(result.size()==1)
			return true;
		else return false;
	}
	
	public List<User> getUser(int userid){
		 StringBuffer condition = new StringBuffer();
	      condition
	          .append(String
	              .format(
	                  "(id = '%d')",
	                  userid));
	    return userDao.find(condition.toString());
	}
	
	public User modifyUser(int userid,String username, String password, String email){
		  
		User user = new User();
		user.setId(userid);
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		
		userDao.update(user);
		
		String condition="id="+userid;
		
		List<User> result = userDao.find(condition);
		
		
		 return result.get(0);
	 }
	
}
