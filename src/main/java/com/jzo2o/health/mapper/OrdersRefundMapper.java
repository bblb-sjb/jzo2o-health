package com.jzo2o.health.mapper;

import com.jzo2o.health.model.domain.OrdersRefund;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 订单退款表 Mapper 接口
 * </p>
 *
 * @author itcast
 * @since 2023-11-07
 */
public interface OrdersRefundMapper extends BaseMapper<OrdersRefund> {

    //查询正在退款的订单100条
    @Select("SELECT * FROM orders_refund LIMIT #{count} ORDER BY create_time ASC")
    List<OrdersRefund> selectRefundingOrders(Integer count);

    @Delete("DELETE FROM orders_refund WHERE id = #{id}")
    void removeById(Long id);

    @Select("SELECT * FROM orders_refund WHERE id = #{ordersRefundId}")
    OrdersRefund getById(Long ordersRefundId);
}
