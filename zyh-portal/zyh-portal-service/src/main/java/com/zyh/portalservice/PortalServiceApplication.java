package com.zyh.portalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zhangyuheng
 */
@EnableFeignClients(basePackages = {"com.zyh.**.feign"})
@SpringBootApplication
public class PortalServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortalServiceApplication.class, args);
    }
}
