package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class helloController {

    @RequestMapping("/inbox.html")
    public String submits(){
        return "inbox";
    }
    @RequestMapping("/page_todo.html")
    public String collection(){
        return "page_todo";
    }
}