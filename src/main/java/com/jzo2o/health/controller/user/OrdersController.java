package com.jzo2o.health.controller.user;

import com.jzo2o.api.trade.enums.PayChannelEnum;
import com.jzo2o.health.model.dto.request.PlaceOrderReqDTO;
import com.jzo2o.health.model.dto.response.OrdersPayResDTO;
import com.jzo2o.health.model.dto.response.PlaceOrderResDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author itcast
 */
@RestController("userOrdersController")
@RequestMapping("/user/orders")
@Api(tags = "用户端 - 下单支付相关接口")
public class OrdersController {

    @ApiOperation("下单接口")
    @PostMapping("/place")
    public PlaceOrderResDTO place(@RequestBody PlaceOrderReqDTO placeOrderReqDTO) {
        return null;
    }

    @PutMapping("/pay/{id}")
    @ApiOperation("订单支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "tradingChannel", value = "支付渠道：ALI_PAY、WECHAT_PAY", required = true, dataTypeClass = PayChannelEnum.class),
    })
    public OrdersPayResDTO pay(@PathVariable("id") Long id, @RequestParam("tradingChannel") PayChannelEnum tradingChannel) {
        return null;
    }

    @GetMapping("/pay/{id}/result")
    @ApiOperation("查询订单支付结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataTypeClass = Long.class)
    })
    public OrdersPayResDTO payResult(@PathVariable("id") Long id) {
        return null;
    }
}
