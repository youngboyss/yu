package com.example.toutiao.service;

import com.example.toutiao.dao.LoginTicketDAO;
import com.example.toutiao.dao.UserDAO;
import com.example.toutiao.model.LoginTicket;
import com.example.toutiao.model.User;
import com.example.toutiao.util.TouTiaoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public Map<String,Object> register(String username, String password)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        if(StringUtils.isBlank(username)){
            map.put("msgname","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)) {
            map.put("msgpwd", "密码不能为空");
            return map;
        }
        User user=userDAO.selectByName(username);

        if(user!=null) {
            map.put("msgname", "用户名已存在");
            return map;
        }
        user=new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(TouTiaoUtil.MD5(password+user.getSalt()));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dm.png",new Random().nextInt(1000)));
        userDAO.addUser(user);
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
         //登陆
        return map;
    }

    public Map<String,Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("msgname", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msgpwd", "密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);

        if (user == null) {
            map.put("msgname", "用户名不存在");
            return map;
        }

        if (!TouTiaoUtil.MD5(password + user.getSalt()).equals(user.getPassword()))
        {
            if (user == null) {
                map.put("msgpwd", "密码不正确");
                return map;
            }
        }
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        //ticket
        return map;
    }

    public User getUser(int id)
    {
        return userDAO.selectById(id);
    }


    private String addLoginTicket(int userId)
    {
        LoginTicket ticket=new LoginTicket();
        ticket.setUserId(userId);
        Date date=new Date();
        date.setTime(date.getTime()+1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();

    }
    public void logout(String ticket)
    {
           loginTicketDAO.updateStatus(ticket,1);

    }
}
