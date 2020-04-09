package com.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author asus
 */
@Controller
public class Test01Controller {
    @ResponseBody
    @GetMapping("test01")
    public String test01(String a,String b){
        return a + b;
    }
}
