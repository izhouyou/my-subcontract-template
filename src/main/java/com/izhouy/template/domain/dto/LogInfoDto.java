package com.izhouy.template.domain.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 操作日志(LogInfo)实体类
 *
 * @author izhouy
 * @since 2022-10-19 00:06:04
 */
@Getter
@Setter
@ApiModel("查询操作日志")
public class LogInfoDto implements Serializable {
    private static final long serialVersionUID = -54575271622569234L;
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * 功能模块
     */
    @ApiModelProperty(value = "功能模块")
    private String module;
    /**
     * 操作类型
     */
    @ApiModelProperty(value = "操作类型")
    private String type;
    /**
     * 操作描述
     */
    @ApiModelProperty(value = "操作描述")
    private String message;
    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String reqParam;
    /**
     * 响应参数
     */
    @ApiModelProperty(value = "响应参数")
    private String resParam;
    /**
     * 耗时
     */
    @ApiModelProperty(value = "耗时")
    private Long takeUpTime;
    /**
     * 操作用户id
     */
    @ApiModelProperty(value = "操作用户id")
    private String userId;
    /**
     * 操作用户名称
     */
    @ApiModelProperty(value = "操作用户名称")
    private String userName;
    /**
     * 操作方面
     */
    @ApiModelProperty(value = "操作方面")
    private String method;
    /**
     * 请求url
     */
    @ApiModelProperty(value = "请求url")
    private String uri;
    /**
     * 请求IP
     */
    @ApiModelProperty(value = "请求IP")
    private String ip;
    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private String version;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}

