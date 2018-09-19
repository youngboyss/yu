package com.example.toutiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SettingController {

    @RequestMapping("/user/setting")
    @ResponseBody
    public String Setting()
    {
        return "Setting:ok:";
    }

    @RequestMapping("/user/settingss")
    @ResponseBody
    public String Settingx()
    {
        return "Setting:s:";
    }
}
