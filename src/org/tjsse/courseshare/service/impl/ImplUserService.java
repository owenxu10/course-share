package org.tjsse.courseshare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

}
