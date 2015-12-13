package com.tw.bookshelf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GreetingController {

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public ModelAndView greeting(@PathVariable String name){
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.getModel().put("name", name);
        return  modelAndView;
    }
}
