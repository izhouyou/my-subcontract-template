package com.izhouy.template.domain;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作日志(LogInfo)实体类
 *
 * @author izhouy
 * @since 2022-10-13 21:27:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("log_info")
public class LogInfo extends Model<LogInfo> implements Serializable {
    private static final long serialVersionUID = 578215460409965497L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 功能模块
     */
    private String module;
    /**
     * 操作类型
     */
    private String type;
    /**
     * 操作描述
     */
    private String message;
    /**
     * 请求参数
     */
    private String reqParam;
    /**
     * 响应参数
     */
    private String resParam;
    /**
     * 耗时
     */
    private Long takeUpTime;
    /**
     * 操作用户id
     */
    private String userId;
    /**
     * 操作用户名称
     */
    private String userName;
    /**
     * 操作方面
     */
    private String method;
    /**
     * 请求url
     */
    private String uri;
    /**
     * 请求IP
     */
    private String ip;
    /**
     * 版本号
     */
    private String version;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

