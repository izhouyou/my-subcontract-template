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
 * 操作日志异常信息(LogErrorInfo)实体类
 *
 * @author izhouy
 * @since 2022-10-13 21:27:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("log_error_info")
public class LogErrorInfo extends Model<LogErrorInfo> implements Serializable {
    private static final long serialVersionUID = 222686391374495730L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 请求参数
     */
    private String reqParam;
    /**
     * 异常名称
     */
    private String name;
    /**
     * 异常信息
     */
    private String message;
    /**
     * 操作用户id
     */
    private String userId;
    /**
     * 操作用户名称
     */
    private String userName;
    /**
     * 请求方法
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

