package com.maxplus1.demo.rest.test1;

import com.maxplus1.demo.common.BaseData;
import com.maxplus1.demo.common.ctrl.CtrlOpt;
import com.maxplus1.demo.config.swagger.Const;
import com.maxplus1.demo.data.entity.Test1Pojo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xiaolong.qiu on 2017/1/18.
 */
@RestController
@RequestMapping("/test1")
@Slf4j
public class Demo1Rest {

    @ApiOperation(value = "sayHello", notes = "根据名字say一下Hello")
    @ApiImplicitParam(name = "name", value = "姓名", required = true, paramType = Const.PATH)
    @GetMapping("sayHello/{name}")
    public BaseData sayHello(@PathVariable("name") String name) {
        return CtrlOpt.ctrl(log, () -> {
            return "Hello," + name + "!";
        });
    }


    @ApiOperation(value = "sayHelloPojo", notes = "根据名字和ID和say一下Hello")
    @ApiImplicitParam(name = "test1Pojo", value = "test1Pojo Bean", required = true, dataType = "Test1Pojo")
    @PostMapping("sayHelloPojo")
    public BaseData sayHelloPojo(@RequestBody Test1Pojo test1Pojo) {
        return CtrlOpt.ctrl(log, () -> {
            return "Hello,[" + test1Pojo.getId() + "]" + test1Pojo.getName() + "!";
        });
    }

}
