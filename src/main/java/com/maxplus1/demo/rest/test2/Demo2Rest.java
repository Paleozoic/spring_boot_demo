package com.maxplus1.demo.rest.test2;

import com.maxplus1.demo.common.BaseData;
import com.maxplus1.demo.common.ctrl.CtrlOpt;
import com.maxplus1.demo.config.swagger.Const;
import com.maxplus1.demo.service.remote.ITest1Service;
import com.maxplus1.demo.service.remote.ITest2Service;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by xiaolong.qiu on 2017/1/18.
 */
@RestController
@RequestMapping("/test2")
@Slf4j
public class Demo2Rest {


    @Resource
    private ITest1Service test1Service;

    @Resource
    private ITest2Service test2Service;

    @Resource(name = "redisTemplate")
    private ValueOperations<String,String> valueOperations;


    @GetMapping("demo")
    public void demo() {
        System.out.println(test1Service.getTest1(1l));
        System.out.println(test2Service.getTest2(2l));
        System.out.println(valueOperations.get("XXX"));
    }

    @ApiOperation(value = "sayHi", notes = "根据名字say一下Hi")
    @ApiImplicitParam(name = "name", value = "姓名", required = true, paramType = Const.PATH)
    @GetMapping("sayHi/{name}")
    public BaseData sayHi(@PathVariable("name") String name) {
        return CtrlOpt.ctrl(log, () -> {
            return "Hi," + name + "!";
        });
    }

}
