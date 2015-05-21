package com.ozstrategy.webapp.controller;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lihao on 1/23/15.
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {
    
    @ExceptionHandler(value = Throwable.class)
    @RequestMapping(value = "/ji/{id}" )
    public String ji(@PathVariable("id") Long id,@RequestBody() Long x, HttpServletRequest request){
        System.out.println("sdfd"+request.getParameter("x"));
        return "dsd"+id;
    }
}
