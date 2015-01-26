package com.ozstrategy.webapp.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lihao on 1/23/15.
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {
    
    @ExceptionHandler(value = Throwable.class)
    @RequestMapping(value = "/ji/{id}")
    public String ji(@PathVariable("id")Long id){
        
        System.out.println("sdfd");
        return "dsd"+id;
    }
}
