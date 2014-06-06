package org.tjsse.courseshare.service;
import java.util.List;

import org.tjsse.courseshare.bean.User;

public interface UserService {
	
	 public User registerUser(String username, String password, String email);
	 
	 public int loginUser(String username, String password);
	 
	 public boolean checkUser(String username);
	 
	 public boolean checkModifyUser(String username);
	 
	 public  List<User> getUser(int userid);
	 
	 public User modifyUser(int userid,String username, String password, String email);
}
