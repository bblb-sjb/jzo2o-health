package com.jzo2o.health.controller.user;

import cn.hutool.core.bean.BeanUtil;
import com.jzo2o.common.model.CurrentUserInfo;
import com.jzo2o.health.model.dto.OrdersCancelledDTO;
import com.jzo2o.health.model.dto.request.OrdersCancelReqDTO;
import com.jzo2o.health.model.dto.response.OrdersDetailResDTO;
import com.jzo2o.health.service.IOrdersService;
import com.jzo2o.mvc.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author itcast
 */
@RestController("userOrdersRefundController")
@RequestMapping("/user/orders")
@Api(tags = "用户端 - 取消订单退款相关接口")
public class OrdersRefundController {

    @Resource
    private IOrdersService ordersService;


    @PutMapping("/cancel")
    @ApiOperation("订单取消")
    public void cancel(@RequestBody OrdersCancelReqDTO ordersCancelReqDTO) {
        OrdersCancelledDTO ordersCancelledDTO = BeanUtil.copyProperties(ordersCancelReqDTO, OrdersCancelledDTO.class);
        CurrentUserInfo currentUserInfo = UserContext.currentUser();
        ordersCancelledDTO.setCancellerId(currentUserInfo.getId());
        ordersCancelledDTO.setCancellerName(currentUserInfo.getName());
        ordersCancelledDTO.setCancellerType(currentUserInfo.getUserType());
        ordersService.cancel(ordersCancelledDTO);
    }

    @PutMapping("/refund")
    @ApiOperation("订单退款")
    public void refund(@RequestBody OrdersCancelReqDTO ordersCancelReqDTO) {
        OrdersCancelledDTO ordersCancelledDTO = BeanUtil.copyProperties(ordersCancelReqDTO, OrdersCancelledDTO.class);
        CurrentUserInfo currentUserInfo = UserContext.currentUser();
        ordersCancelledDTO.setCancellerId(currentUserInfo.getId());
        ordersCancelledDTO.setCancellerName(currentUserInfo.getName());
        ordersCancelledDTO.setCancellerType(currentUserInfo.getUserType());
        ordersService.cancel(ordersCancelledDTO);
    }
}
