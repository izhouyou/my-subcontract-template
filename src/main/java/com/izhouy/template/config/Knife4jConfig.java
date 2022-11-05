package com.izhouy.template.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * title Knife4jConfig
 * Description swagger配置类
 * CreateDate 2022/9/5 10:10
 *
 * @author izhouy
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfig {
    /**
     * 发布时间戳
     */
    private static final String TIME_TAG = String.valueOf(System.currentTimeMillis());
    @Value("${swagger.title:default}")
    private String title;
    @Value("${swagger.author-name:default}")
    private String authorName;
    @Value("${swagger.author-email:default@163.com}")
    private String authorEmail;
    @Value("${swagger.author-url:default.com}")
    private String authorUrl;
    @Value("${swagger.terms-of-service-url:default}")
    private String termsOfServiceUrl;
    @Value("${swagger.version:default}")
    private String version;
    @Value("${swagger.base-package:info.jiatu.jtlsp}")
    private String basePackage;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(new ApiInfoBuilder()
                .title(title + "系统")
                .description(title + "系统api接口文档@" + TIME_TAG)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(authorName, authorUrl, authorEmail))
                .version(version)
                .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

}
