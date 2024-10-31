package com.jzo2o.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.api.trade.enums.PayChannelEnum;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.common.model.msg.TradeStatusMsg;
import com.jzo2o.health.model.domain.Orders;
import com.jzo2o.health.model.dto.OrdersCancelledDTO;
import com.jzo2o.health.model.dto.request.OrdersPageQueryReqDTO;
import com.jzo2o.health.model.dto.request.PlaceOrderReqDTO;
import com.jzo2o.health.model.dto.response.*;

import java.util.List;

public interface IOrdersService extends IService<Orders> {

    PlaceOrderResDTO userPlaceOrder(PlaceOrderReqDTO placeOrderReqDTO);

    OrdersPayResDTO pay(Long id, PayChannelEnum tradingChannel);

    OrdersPayResDTO payResult(Long id);

    void paySuccess(TradeStatusMsg tradeStatusMsg);

    void cancel(OrdersCancelledDTO ordersCancelledDTO);

    /**
     * 查询超时订单id列表
     *
     * @param count 数量
     * @return 订单id列表
     */
    public List<Orders> queryOverTimePayOrdersListByCount(Integer count);

    OrdersDetailResDTO getDetailById(Long id);

    List<OrdersResDTO> pageQuery(Integer ordersStatus, Long sortBy);

    PageResult<OrdersResDTO> adminPageQuery(OrdersPageQueryReqDTO ordersPageQueryReqDTO);

    AdminOrdersDetailResDTO adminAggregation(Long id);

    OrdersCountResDTO countByStatus();
}
