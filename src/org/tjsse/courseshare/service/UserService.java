package org.tjsse.courseshare.service;
import org.tjsse.courseshare.bean.User;

public interface UserService {
	
	 public User registerUser(String username, String password, String email);
	 
	 public int loginUser(String username, String password);
	 
	 public boolean checkUser(String username);
}
