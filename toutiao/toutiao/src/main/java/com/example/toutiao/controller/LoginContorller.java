package com.example.toutiao.controller;


import com.example.toutiao.service.UserService;
import com.example.toutiao.util.TouTiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginContorller {

    private final static Logger logger=LoggerFactory.getLogger(LoginContorller.class);

    @Autowired
    UserService userService;
    @RequestMapping(path={"/reg/"},method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String reg(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam(value="rember",defaultValue="0") int rember, HttpServletResponse response)
    {
           try
           {
              Map<String,Object> map=userService.register(username,password);
              if(map.containsKey("ticket"))
               {
                  Cookie cookie =new Cookie("ticket",map.get("ticket").toString());
                  if(rember>0)
                  {
                      cookie.setMaxAge(5*24*3600);
                  }
                   cookie.setPath("/");
                  response.addCookie(cookie);
                   return TouTiaoUtil.getJSONString(0,"注册成功");
              }
              else{
                  return TouTiaoUtil.getJSONString(1,map);
              }
           }catch(Exception e) {
               logger.error("注册异常" + e.getMessage());
               return TouTiaoUtil.getJSONString(1, "注册异常");

           }
    }


    @RequestMapping(path={"/login/"},method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String login(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam(value="rember",defaultValue="0") int rember, HttpServletResponse response )
    {
        try
        {
            Map<String,Object> map=userService.login(username,password);
            if(map.containsKey("ticket"))
            {
                Cookie cookie =new Cookie("ticket",map.get("ticket").toString());
                if(rember>0)
                {
                    cookie.setMaxAge(5*24*3600);
                }
                cookie.setPath("/");
                response.addCookie(cookie);
                return TouTiaoUtil.getJSONString(0, "成功");
            } else {
                return TouTiaoUtil.getJSONString(1, map);
            }
        }catch(Exception e) {
            logger.error("注册异常" + e.getMessage());
            return TouTiaoUtil.getJSONString(1, "注册异常");

        }
    }

    @RequestMapping(path={"/logout/"},method={RequestMethod.GET,RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }

}
