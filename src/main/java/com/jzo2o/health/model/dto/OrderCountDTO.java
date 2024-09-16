package com.jzo2o.health.model.dto;

import lombok.Data;

/**
 * 订单数量响应
 *
 * @author itcast
 * @create 2023/11/9 16:54
 **/
@Data
public class OrderCountDTO {

    private Integer orderStatus;

    private Integer count;
}
