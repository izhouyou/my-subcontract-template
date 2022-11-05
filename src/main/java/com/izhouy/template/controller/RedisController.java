package com.izhouy.template.controller;

import com.izhouy.template.common.annotation.Log;
import com.izhouy.template.common.constant.LogAnnotConstants;
import com.izhouy.template.util.RedisUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author izhouy
 * @title RedisController
 * @Decription
 * @CreateDate 2021/6/23 23:52
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisUtils redisUtils;

    @GetMapping("setValue")
    @Log(modul = "redis", type = LogAnnotConstants.INSERT, desc = "新增redis缓存")
    public String setValue(@RequestParam("key") String key, @RequestParam("value") String value){
        redisUtils.setStr(key, value);
        return "success";
    }

    @GetMapping("getValue")
    public String getValue(@RequestParam("key") String key){
        return redisUtils.getStr(key);
    }

    @GetMapping("uptValue")
    public String uptValue(@RequestParam("key") String key, @RequestParam("value") String value){
        redisUtils.setStr(key, value);
        return "success";
    }

    @GetMapping("getValue2")
    public String uptValue(@RequestParam("key") String key){
        redisUtils.getStr(key);
        return "success";
    }
}