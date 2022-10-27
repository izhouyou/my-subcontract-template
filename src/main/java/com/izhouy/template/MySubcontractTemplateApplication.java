package com.izhouy.template;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @title MySubcontractTemplateApplication
 * @description 启动类
 * @author izhouy
 * @dateTime 2022/10/14 10:08
 * @editNote
 */
@SpringBootApplication
@RetrofitScan("com.izhouy.template.rpc.service")
public class MySubcontractTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySubcontractTemplateApplication.class, args);
    }

}
