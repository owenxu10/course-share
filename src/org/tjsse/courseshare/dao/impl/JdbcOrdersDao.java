package org.tjsse.courseshare.dao.impl;

import org.springframework.stereotype.Repository;
import org.tjsse.courseshare.bean.Orders;
import org.tjsse.courseshare.dao.OrdersDao;

@Repository
public class JdbcOrdersDao extends JdbcBaseDao implements OrdersDao {

	@Override
	public void update(Orders orders) {
		String orderlist = orders.getorderlist();
		int order_id = orders.getOrder_id();
		int user_id = orders.getUserid();
		int theme_id = orders.getTheme_id();
		String udatesql= "UPDATE `course-share`.`orders` SET `orderlist`='"+orderlist
				+"' WHERE `order_id`='"+order_id
				+"' and`userid`='"+user_id
				+"' and`theme_id`='"+theme_id+"';";
		
		jdbcTemplate.execute(udatesql);
		
		
	}

}
