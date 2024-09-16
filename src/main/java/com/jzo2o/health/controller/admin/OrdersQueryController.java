package com.jzo2o.health.controller.admin;

import com.jzo2o.common.model.PageResult;
import com.jzo2o.health.model.dto.request.OrdersPageQueryReqDTO;
import com.jzo2o.health.model.dto.response.AdminOrdersDetailResDTO;
import com.jzo2o.health.model.dto.response.OrdersResDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author itcast
 */
@RestController("adminOrdersQueryController")
@RequestMapping("/admin/orders")
@Api(tags = "管理端 - 订单查询接口")
public class OrdersQueryController {

    @ApiOperation("分页查询")
    @GetMapping("/page")
    public PageResult<OrdersResDTO> pageQuery(OrdersPageQueryReqDTO ordersPageQueryReqDTO) {
        return null;
    }

    @GetMapping("/{id}")
    @ApiOperation("根据订单id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataTypeClass = Long.class)
    })
    public AdminOrdersDetailResDTO aggregation(@PathVariable("id") Long id) {
        return null;
    }

}
