package com.jzo2o.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzo2o.health.model.domain.Orders;
import com.jzo2o.health.model.dto.OrderCountDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author itcast
 * @since 2023-11-02
 */
public interface OrdersMapper extends BaseMapper<Orders> {

    @Select("SELECT order_status AS orderStatus,COUNT(id) AS count FROM orders GROUP BY order_status")
    List<OrderCountDTO> countByStatus();
}
