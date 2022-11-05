package com.izhouy.template.controller;

import com.izhouy.template.common.annotation.Log;
import com.izhouy.template.common.constant.LogAnnotConstants;
import info.jiatu.jtlsp.common.annotation.AddGroups;
import info.jiatu.jtlsp.common.annotation.UpdGroups;
import info.jiatu.jtlsp.common.response.Result;
import info.jiatu.jtlsp.common.util.ResultGeneratorUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhouy.template.domain.LogErrorInfo;
import com.izhouy.template.service.LogErrorInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 操作日志异常信息(LogErrorInfo)表控制层
 *
 * @author izhouy
 * @since 2022-10-21 01:51:11
 */
@RestController
@RequestMapping("logErrorInfo")
@Api(tags = "操作日志异常信息LogErrorInfo表控制层")
@Validated
public class LogErrorInfoController {
    /**
     * 服务对象
     */
    @Resource
    private LogErrorInfoService logErrorInfoService;

    /**
     * 分页查询所有数据
     *
     * @param page         分页对象
     * @param logErrorInfo 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation("操作日志异常信息分页查询接口")
    public Result<IPage<LogErrorInfo>> selectAll(Page<LogErrorInfo> page, @Valid LogErrorInfo logErrorInfo) {
        return ResultGeneratorUtils.success(this.logErrorInfoService.page(page, new QueryWrapper<>(logErrorInfo)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation("操作日志异常信息根据主键id查询接口")
    @ApiImplicitParam(name = "id", value = "主键id", defaultValue = "1", required = true, dataTypeClass = Long.class)
    public Result<LogErrorInfo> selectOne(@NotNull(message = "id不能为空") @PathVariable Long id) {
        return ResultGeneratorUtils.success(this.logErrorInfoService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param logErrorInfo 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation("操作日志异常信息新增接口")
    @Log(modul = "LogErrorInfo", type = LogAnnotConstants.INSERT, desc = "操作日志异常信息新增接口")
    public Result<Boolean> insert(@Validated(AddGroups.class) @RequestBody LogErrorInfo logErrorInfo) {
        return ResultGeneratorUtils.success(this.logErrorInfoService.save(logErrorInfo));
    }

    /**
     * 修改数据
     *
     * @param logErrorInfo 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation("操作日志异常信息更新接口")
    @Log(modul = "LogErrorInfo", type = LogAnnotConstants.UPDATE, desc = "操作日志异常信息更新接口")
    public Result<Boolean> update(@Validated(UpdGroups.class) @RequestBody LogErrorInfo logErrorInfo) {
        return ResultGeneratorUtils.success(this.logErrorInfoService.updateById(logErrorInfo));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ApiOperation("操作日志异常信息批量删除接口")
    @ApiImplicitParam(name = "idList", value = "主键id集合", defaultValue = "1,2", required = true, dataTypeClass = List.class)
    @Log(modul = "LogErrorInfo", type = LogAnnotConstants.DELETE, desc = "操作日志异常信息删除接口")
    public Result<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return ResultGeneratorUtils.success(this.logErrorInfoService.removeByIds(idList));
    }
}

