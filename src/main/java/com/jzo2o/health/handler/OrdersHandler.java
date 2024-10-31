package com.jzo2o.health.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jzo2o.api.trade.RefundRecordApi;
import com.jzo2o.api.trade.dto.response.ExecutionResultResDTO;
import com.jzo2o.api.trade.enums.RefundStatusEnum;
import com.jzo2o.common.constants.UserType;
import com.jzo2o.health.enums.OrderPayStatusEnum;
import com.jzo2o.health.mapper.OrdersCancelledMapper;
import com.jzo2o.health.mapper.OrdersMapper;
import com.jzo2o.health.mapper.OrdersRefundMapper;
import com.jzo2o.health.model.domain.Orders;
import com.jzo2o.health.model.domain.OrdersCancelled;
import com.jzo2o.health.model.domain.OrdersRefund;
import com.jzo2o.health.model.dto.OrdersCancelledDTO;
import com.jzo2o.health.properties.OrdersJobProperties;
import com.jzo2o.health.service.IOrdersService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    private OrdersHandler owner;
    @Resource
    private OrdersJobProperties ordersJobProperties;

    @Resource
    private IOrdersService ordersService;
    @Resource
    private OrdersRefundMapper ordersRefundMapper;

    @Resource
    private OrdersMapper ordersMapper;


    /**
     * 支付超时取消订单
     * 每分钟执行一次
     */
    @XxlJob(value = "cancelOverTimeHealthPayOrder")
    public void cancelOverTimeHealthPayOrder() {
        //查询支付超时状态订单
        List<Orders> ordersList = ordersService.queryOverTimePayOrdersListByCount(100);
        if (CollUtil.isEmpty(ordersList)) {
            XxlJobHelper.log("查询超时订单列表为空！");
            return;
        }
        for (Orders order : ordersList) {
            //取消订单
            OrdersCancelledDTO orderCancelDTO = BeanUtil.toBean(order, OrdersCancelledDTO.class);
            orderCancelDTO.setCancellerType(UserType.SYSTEM);
            orderCancelDTO.setCancelReason("订单超时支付，自动取消");
            ordersService.cancel(orderCancelDTO);
        }
    }

    /**
     * 订单退款异步任务
     */
    @XxlJob(value = "handleRefundHealthOrders")
    public void handleRefundHealthOrders() {
        //查询退款中订单

        List<OrdersRefund> ordersRefundList = ordersRefundMapper.selectRefundingOrders(100);
        for (OrdersRefund ordersRefund : ordersRefundList) {
            //请求退款
            requestRefundOrder(ordersRefund);
        }
    }

    /**
     * 请求退款
     * @param ordersRefund 退款记录
     */
    public void requestRefundOrder(OrdersRefund ordersRefund){
        //调用第三方进行退款
        ExecutionResultResDTO executionResultResDTO = null;
        try {
            executionResultResDTO = refundRecordApi.refundTrading(ordersRefund.getTradingOrderNo(), ordersRefund.getRealPayAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(executionResultResDTO!=null){
            //退款后处理订单相关信息
            owner.refundOrder(ordersRefund, executionResultResDTO);
        }
    }

    /**
     * 订单退款处理
     *
     * @param ordersRefund
     * @param executionResultResDTO 第三方退款信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void refundOrder(OrdersRefund ordersRefund, ExecutionResultResDTO executionResultResDTO) {
        //根据响应结果更新退款状态
        int refundStatus = OrderPayStatusEnum.REFUNDING.getStatus();//退款中
        if (ObjectUtil.equal(RefundStatusEnum.SUCCESS.getCode(), executionResultResDTO.getRefundStatus())) {
            //退款成功
            refundStatus = OrderPayStatusEnum.REFUND_SUCCESS.getStatus();
        } else if (ObjectUtil.equal(RefundStatusEnum.FAIL.getCode(), executionResultResDTO.getRefundStatus())) {
            //退款失败
            refundStatus = OrderPayStatusEnum.REFUND_FAIL.getStatus();
        }

        //如果是退款中状态，程序结束
        if (ObjectUtil.equal(refundStatus, OrderPayStatusEnum.REFUNDING.getStatus())) {
            return;
        }

        //非退款中状态，更新订单的退款状态
        LambdaUpdateWrapper<Orders> updateWrapper = new LambdaUpdateWrapper<Orders>()
                .eq(Orders::getId, ordersRefund.getId())
                .ne(Orders::getPayStatus, refundStatus)
                .set(Orders::getPayStatus, refundStatus)
                .set(ObjectUtil.isNotEmpty(executionResultResDTO.getRefundId()), Orders::getRefundId, executionResultResDTO.getRefundId())
                .set(ObjectUtil.isNotEmpty(executionResultResDTO.getRefundNo()), Orders::getRefundNo, executionResultResDTO.getRefundNo());
        int update = ordersMapper.update(null, updateWrapper);
        //非退款中状态，删除申请退款记录，删除后定时任务不再扫描
        if(update>0){
            //非退款中状态，删除申请退款记录，删除后定时任务不再扫描
            ordersRefundMapper.removeById(ordersRefund.getId());
        }

    }

    /**
     * 新启动一个线程请求退款
     * @param ordersRefundId
     */
    public void requestRefundNewThread(Long ordersRefundId){
        //启动一个线程请求第三方退款接口
        new Thread(()->{
            //查询退款记录
            OrdersRefund ordersRefund = ordersRefundMapper.getById(ordersRefundId);
            if(ObjectUtil.isNotNull(ordersRefund)){
                //请求退款
                requestRefundOrder(ordersRefund);
            }
        }).start();
    }
}
