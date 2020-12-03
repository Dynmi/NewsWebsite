package com.example.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class helloController {
    @RequestMapping("/index.html")
    public String index(){
        return "index";
    }
    @RequestMapping("/musenews.html")
    public String muse(){
        return "musenews";
    }
    @RequestMapping("/financenews.html")
    public String finance(){
        return "financenews";
    }
    @RequestMapping("/sportsnews.html")
    public String sport(){
        return "sportsnews";
    }
    @RequestMapping("/technews.html")
    public String tech(){
        return "technews";
    }
    @RequestMapping("/militarynews.html")
    public String military(){
        return "militarynews";
    }
    @RequestMapping("/extra_profile.html")
    public String profile(){
        return "extra_profile";
    }
    @RequestMapping("/inbox.html")
    public String submits(){
        return "inbox";
    }
    @RequestMapping("/page_todo.html")
    public String collection(){
        return "page_todo";
    }
}