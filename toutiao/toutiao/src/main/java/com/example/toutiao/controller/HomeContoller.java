package com.example.toutiao.controller;


import com.example.toutiao.model.News;
import com.example.toutiao.model.ViewObject;
import com.example.toutiao.service.NewsService;
import com.example.toutiao.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeContoller {

    private final static Logger log=LoggerFactory.getLogger(HomeContoller.class);
        @Autowired
        NewsService newsService;

        @Autowired
        UserService userService;

        private  List<ViewObject> getNews(int userId,int offset,int limit)
        {
            List<News> newList=newsService.getLatestNews(userId,offset,limit);
            List<ViewObject> vos=new ArrayList<ViewObject>();
            for(News news :newList) {
                ViewObject vo=new ViewObject();
                vo.set("news",news);
                vo.set("user",userService.getUser(news.getUserId()));
                vos.add(vo);
            }
            return vos;
        }
        @RequestMapping(path= {"/","/indexd/"},method={RequestMethod.GET,RequestMethod.POST})
        public String index(Model model,@RequestParam(value="pop",defaultValue="0") int pop)
        {

             model.addAttribute("vos",getNews(0,0,10));
            model.addAttribute("pop",pop);
            return "home";
        }

    @RequestMapping(path= {"/user/{userid}/"},method={RequestMethod.GET,RequestMethod.POST})
    public String userindex(Model model, @PathVariable("userid")int userid)
    {

        model.addAttribute("vos",getNews(userid,0,10));

        return "home";
    }


}
