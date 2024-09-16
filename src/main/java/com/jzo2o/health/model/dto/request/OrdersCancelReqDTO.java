package com.jzo2o.health.model.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单取消或退款模型
 *
 * @author itcast
 * @create 2023/11/10 10:04
 **/
@Data
@ApiModel("订单取消或退款请求模型")
public class OrdersCancelReqDTO {

    @ApiModelProperty(value = "订单id", required = true)
    private Long id;

    @ApiModelProperty(value = "取消或退款原因", required = true)
    private String cancelReason;
}
