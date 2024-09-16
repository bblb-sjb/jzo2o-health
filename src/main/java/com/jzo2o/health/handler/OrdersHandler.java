package com.jzo2o.health.handler;

import com.jzo2o.api.trade.RefundRecordApi;
import com.jzo2o.api.trade.dto.response.ExecutionResultResDTO;
import com.jzo2o.health.properties.OrdersJobProperties;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 订单相关定时任务
 *
 * @author itcast
 * @create 2023/9/2 16:44
 **/
@Slf4j
@Component
public class OrdersHandler {

    @Resource
    private RefundRecordApi refundRecordApi;
    //解决同级方法调用，事务失效问题
    @Resource
    private OrdersHandler orderHandler;
    @Resource
    private OrdersJobProperties ordersJobProperties;

    /**
     * 支付超时取消订单
     * 每分钟执行一次
     */
    @XxlJob(value = "cancelOverTimePayOrder")
    public void cancelOverTimePayOrder() {

    }

    /**
     * 订单退款异步任务
     */
    @XxlJob(value = "handleRefundOrders")
    public void handleRefundOrders() {

    }

    /**
     * 订单退款处理
     *
     * @param id                    订单id
     * @param executionResultResDTO 第三方退款信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void refundOrder(Long id, ExecutionResultResDTO executionResultResDTO) {

    }
}
