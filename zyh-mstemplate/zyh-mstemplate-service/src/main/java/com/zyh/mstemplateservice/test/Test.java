package com.zyh.mstemplateservice.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyuheng
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class Test {
    @GetMapping("/get")
    public String fun() {
        return "测试成功！";
    }
}
