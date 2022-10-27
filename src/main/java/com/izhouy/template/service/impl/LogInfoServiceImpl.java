package com.izhouy.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhouy.template.mapper.LogInfoMapper;
import com.izhouy.template.domain.LogInfo;
import com.izhouy.template.service.LogInfoService;
import org.springframework.stereotype.Service;

/**
 * 操作日志(LogInfo)表服务实现类
 *
 * @author izhouy
 * @since 2022-10-13 21:27:26
 */
@Service("logInfoService")
public class LogInfoServiceImpl extends ServiceImpl<LogInfoMapper, LogInfo> implements LogInfoService {

}

