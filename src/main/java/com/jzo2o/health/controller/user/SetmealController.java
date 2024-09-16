package com.jzo2o.health.controller.user;

import com.jzo2o.health.annotation.IgnoreToken;
import com.jzo2o.health.model.domain.Setmeal;
import com.jzo2o.health.model.dto.response.SetmealDetailResDTO;
import com.jzo2o.health.service.ISetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags = "用户端 - 套餐相关接口")
public class SetmealController {
    @Resource
    private ISetmealService setmealService;

    @GetMapping("/getSetmeal")
    @IgnoreToken
    @ApiOperation("获取所有套餐信息")
    public List<Setmeal> getSetmeal() {
        return setmealService.findAll();
    }

    @GetMapping("/findDetail/{id}")
    @IgnoreToken
    @ApiOperation("根据id查询套餐信息")
    @ApiImplicitParam(name = "id", value = "套餐id", required = true, dataTypeClass = Integer.class)
    public SetmealDetailResDTO findById(@PathVariable("id") Integer id) {
        return setmealService.findDetail(id);
    }
}
