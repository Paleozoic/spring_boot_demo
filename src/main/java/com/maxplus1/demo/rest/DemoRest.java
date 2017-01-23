package com.maxplus1.demo.rest;

import com.maxplus1.demo.service.remote.ITest1Service;
import com.maxplus1.demo.service.remote.ITest2Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by xiaolong.qiu on 2017/1/18.
 */
@RestController
@RequestMapping("/")
public class DemoRest {


    @Resource
    private ITest1Service test1Service;

    @Resource
    private ITest2Service test2Service;

    @GetMapping("demo")
    public void demo(){
        System.out.println(test1Service.getTest1(1l));
        System.out.println(test2Service.getTest2(2l));
    }



}
