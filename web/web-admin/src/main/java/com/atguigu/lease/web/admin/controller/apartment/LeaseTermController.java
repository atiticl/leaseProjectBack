package com.atguigu.lease.web.admin.controller.apartment;


import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.LeaseTerm;
import com.atguigu.lease.web.admin.service.LeaseTermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "租期管理")
@RequestMapping("/admin/term")
@RestController
public class LeaseTermController {
    @Autowired
    private LeaseTermService leaseTermService;

    @Operation(summary = "查询全部租期信息")
    @GetMapping("list")
    public Result<List<LeaseTerm>> listLeaseTerm(){
        List<LeaseTerm> list = leaseTermService.list();
        return  Result.ok(list);
    }

    @Operation(summary = "保存或更新租期信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdateLeaseTerm(@RequestBody LeaseTerm leaseTerm){
        leaseTermService.saveOrUpdate(leaseTerm);
        return Result.ok();
    }

    @Operation(summary = "根据Id删除租期")
    @DeleteMapping("deleteById")
    public Result deleteLeaseTermById(@RequestParam Long id){
        leaseTermService.removeById(id);
        return Result.ok();
    }

}
