package com.example.toutiao.controller;


import com.example.toutiao.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

@Controller
public class IndexController {

    private final static Logger log= LoggerFactory.getLogger(IndexController.class);


    @Autowired
    private IndexService indexService;

    @RequestMapping(path={"/index/"})
    @ResponseBody
    public String index(HttpSession session) {
        log.info("hhhhhhhhhhhhhhhhhh");
        return "Hello"+session.getAttribute("msg")+indexService.say();
    }

    @RequestMapping(path={"/profile/{groupid}/{userid}"})
    @ResponseBody
    public String profile(@PathVariable("groupid") String grouid, @PathVariable("userid") int userid, @RequestParam(value="type",defaultValue="1")
            int type,@RequestParam(value="key",defaultValue="nowcoder") String key){

        return String.format("GID{%s} UID{%d} TYPE{%d} KEY{%s}",grouid,userid,type,key);
    }

    @RequestMapping("/vm")
    public String vm(Model model)
    {
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        map.put("xiaowang",1000);
        map.put("xiaohong",2000);
        map.put("xiaoming",3000);
        model.addAttribute("map",map);
        List<Integer> a=new ArrayList<Integer>();
        a.add(100);
        a.add(200);
        a.add(300);
        a.add(400);
        model.addAttribute("list",a);
        return "home";
    }

    @RequestMapping(value={"/request"})
    @ResponseBody
    public String request(HttpServletRequest request, HttpServletResponse response,HttpSession session){
               StringBuffer sb=new StringBuffer();
        Enumeration<String> headerNames=request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name=headerNames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        return sb.toString();
    }
    @RequestMapping(value={"/response"})
    @ResponseBody
    public String response(@CookieValue(value="nowcoderid",defaultValue="a") String nowcoderid
                            ,@RequestParam(value="key",defaultValue="key") String key,
                               @RequestParam(value="value",defaultValue="value") String value,HttpServletResponse response)
    {
        response.addCookie(new Cookie(key,value));
        response.addHeader(key,value);
        return "NowcoderId from cookie:"+nowcoderid;
    }
    @RequestMapping("/redirect/{code}")
    public RedirectView redirect(@PathVariable("code") int code) {

        RedirectView red=new RedirectView("/",true);
        if(code==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }
    @RequestMapping("/redirect/")
    public String rediects(HttpSession session)
    {
        session.setAttribute("msg","Jump from redirect.");

        return "redirect:/";
    }
    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value="key",required=false) String key)
    {
        if("admin".equals(key))
        {
            return "hello admin";
        }
        throw new IllegalArgumentException("Key 错误");
    }
    @ExceptionHandler
    @ResponseBody
    public String error(Exception e)
    {
        return "error:"+e.getMessage();
    }
}
