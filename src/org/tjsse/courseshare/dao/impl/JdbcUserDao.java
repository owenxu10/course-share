package org.tjsse.courseshare.dao.impl;

import org.springframework.stereotype.Repository;
import org.tjsse.courseshare.bean.User;
import org.tjsse.courseshare.dao.UserDao;

@Repository
public class JdbcUserDao extends JdbcBaseDao implements UserDao {

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		int userid = user.getId();
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		String udatesql= "UPDATE `course-share`.`user` SET `username`='"+username+"',"
				+"`password`='"+password+"',"+"`email`='"+email+"'"
				+" WHERE `id`='"+userid+"';";
		
		System.out.println(udatesql);
		jdbcTemplate.execute(udatesql);
	}
    
}
