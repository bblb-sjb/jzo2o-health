package com.jzo2o.health.controller.user;

import com.jzo2o.health.model.dto.response.OrdersDetailResDTO;
import com.jzo2o.health.model.dto.response.OrdersResDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author itcast
 */
@RestController("userOrdersQueryController")
@RequestMapping("/user/orders")
@Api(tags = "用户端 - 订单查询相关接口")
public class OrdersQueryController {

    @ApiOperation("滚动分页查询")
    @GetMapping("/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ordersStatus", value = "订单状态，0：未支付，100：待体检，200：已体检，300：已关闭，400：已取消", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "sortBy", value = "排序字段", required = false, dataTypeClass = Long.class)
    })
    public List<OrdersResDTO> pageQuery(@RequestParam(value = "ordersStatus", required = false) Integer ordersStatus,
                                        @RequestParam(value = "sortBy", required = false) Long sortBy) {
        return null;
    }

    @GetMapping("/{id}")
    @ApiOperation("根据订单id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataTypeClass = Long.class)
    })
    public OrdersDetailResDTO detail(@PathVariable("id") Long id) {
        return null;
    }

}
