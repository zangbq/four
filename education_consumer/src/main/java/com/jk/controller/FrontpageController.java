package com.jk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("frontpage")
@Controller
public class FrontpageController {

    @RequestMapping("homepage")
    public String homepage(){
        return "homepage";
    }

    @RequestMapping("show")
    public String show(){
        return "show";
    }

    @RequestMapping("main")
    public String main(){
        return "main";
    }

    @RequestMapping("top")
    public String top(){
        return "top";
    }


    @RequestMapping("oneCenter")
    public String oneCenter(){
        return "oneCenter";
    }

}
