package com.lzp.leaseProject.web.admin.controller.apartment;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lzp.leaseProject.common.result.Result;
import com.lzp.leaseProject.model.entity.LabelInfo;
import com.lzp.leaseProject.model.enums.ItemType;
import com.lzp.leaseProject.web.admin.service.LabelInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李志平
 * @version 1.0
 * @data 2025/5/29 14:33
 * @desc
 */

@Tag(name = "标签信息管理")
@RestController
@RequestMapping("/admin/label")
public class LabelInfoController {

    @Autowired
    private LabelInfoService  labelInfoService;

    /**
     * 查询标签列表*
     */
    @Operation(summary = "根据类型查询标签列表")
    @GetMapping("list")
    public Result<List<LabelInfo>> LabelList(@RequestParam(required = false) ItemType itemType){
        LambdaQueryWrapper<LabelInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(itemType != null, LabelInfo::getType , itemType);
        List<LabelInfo> list = labelInfoService.list(queryWrapper);
        return Result.ok(list);
    }
}
