package com.jzo2o.health.controller.admin;

import com.jzo2o.common.model.PageResult;
import com.jzo2o.health.model.domain.Setmeal;
import com.jzo2o.health.model.dto.request.CommonPageQueryReqDTO;
import com.jzo2o.health.service.ISetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 体检套餐管理
 */
@RestController("adminSetmealController")
@RequestMapping("/admin/setmeal")
@Api(tags = "管理端 - 套餐相关接口")
public class SetmealController {
    @Resource
    private ISetmealService setmealService;

    @PostMapping("/add")
    @ApiOperation("新增套餐")
    @ApiImplicitParam(name = "checkgroupIds", value = "检查组id列表", required = true, dataTypeClass = Integer.class)
    public void add(@RequestBody Setmeal setmeal, @RequestParam("checkgroupIds") Integer[] checkgroupIds) {
        setmealService.add(setmeal, checkgroupIds);
    }

    @GetMapping("/findPage")
    @ApiOperation("分页查询套餐")
    public PageResult<Setmeal> findPage(CommonPageQueryReqDTO commonPageQueryReqDTO) {
        return setmealService.findPage(commonPageQueryReqDTO);
    }
}
