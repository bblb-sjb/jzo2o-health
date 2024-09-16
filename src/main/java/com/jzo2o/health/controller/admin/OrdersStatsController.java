package com.jzo2o.health.controller.admin;

import com.jzo2o.health.model.dto.response.OrdersCountResDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author itcast
 */
@RestController("adminOrdersStatsController")
@RequestMapping("/admin/orders")
@Api(tags = "管理端 - 根据状态统计订单数量")
public class OrdersStatsController {

    @GetMapping("/countByStatus")
    @ApiOperation("根据状态统计数量")
    public OrdersCountResDTO countByStatus() {
        return null;
    }
}
