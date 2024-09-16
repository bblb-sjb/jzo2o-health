package com.jzo2o.health.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author itcast
 */
@Data
@Component
@ConfigurationProperties(prefix = "jzo2o.job")
public class OrdersJobProperties {
    /**
     * 退款订单数量，默认100
     */
    private Integer refundOrderCount = 100;

    /**
     * 超时支付订单数量，默认100
     */
    private Integer overTimePayOrderCount = 100;
}