package com.example.toutiao;

import com.example.toutiao.dao.LoginTicketDAO;
import com.example.toutiao.dao.NewsDAO;
import com.example.toutiao.dao.UserDAO;
import com.example.toutiao.model.LoginTicket;
import com.example.toutiao.model.News;
import com.example.toutiao.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {
	private final static Logger log= LoggerFactory.getLogger(InitDatabaseTests.class);
	@Autowired
	UserDAO userDAO;

	@Autowired
	NewsDAO newsDAO;

	@Autowired
	LoginTicketDAO loginTicketDAO;

	@Test
	public void contextLoads() {
		Random random=new Random();
	     for(int i=0;i<11;i++)
		 {
			 User user=new User();
			 user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
			 user.setName(String.format("USER[%d]",i));
			 user.setPassword("");
			 user.setSalt("");
			 user.setPassword("newpassword");
			 userDAO.updatePassword(user);
			 userDAO.addUser(user);
			 News news=new News();
			 news.setCommentCount(i);
			 Date date=new Date();
			 date.setTime(date.getTime()+1000*3600*5*i);
			 news.setCreatedDate(date);
			 news.setImage(String.format("http://images.nowcoder.com/head/%dm.png",random.nextInt(1000)));
			 news.setLikeCount(i+1);
			 news.setUserId(i+1);
			 news.setTitle(String.format("TiTle{%d}",i));
			 news.setLink(String.format("http://www.nowcoder.com/%d.html",i));
			 newsDAO.addNews(news);
			 LoginTicket ticket=new LoginTicket();
			 ticket.setStatus(0);
			 ticket.setUserId(i+1);
			 ticket.setExpired(date);
			 ticket.setTicket(String.format("TICKET%d",i+1));
			 loginTicketDAO.addTicket(ticket);

			 loginTicketDAO.updateStatus(ticket.getTicket(),2);

		 }

		 Assert.assertEquals("newpassword",userDAO.selectById(1).getPassword());
		 userDAO.delteById(1);
		 Assert.assertNull(userDAO.selectById(1));
		 Assert.assertEquals(1,loginTicketDAO.selectByTicket("TICKET1").getUserId());
		 Assert.assertEquals(2,loginTicketDAO.selectByTicket("TICKET1").getStatus());



	}

}
