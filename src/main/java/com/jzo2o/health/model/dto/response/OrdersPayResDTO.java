package com.jzo2o.health.model.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单支付响应体
 *
 * @author itcast
 * @create 2023/9/4 10:00
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("订单支付响应体")
public class OrdersPayResDTO {

    @ApiModelProperty(value = "二维码base64数据")
    private String qrCode;
    @ApiModelProperty(value = "业务系统订单号")
    private Long productOrderNo;
    @ApiModelProperty(value = "交易系统订单号【对于三方来说：商户订单】")
    private Long tradingOrderNo;
    @ApiModelProperty(value = "支付渠道【支付宝、微信、现金、免单挂账】")
    private String tradingChannel;
    @ApiModelProperty(value = "支付状态，0：待支付，1：支付成功")
    private Integer payStatus;
}
