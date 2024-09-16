package com.jzo2o.health.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author itcast
 */

@Getter
@AllArgsConstructor
public enum OrderPayStatusEnum {
    NO_PAY(0, "未支付"),
    PAY_SUCCESS(1, "已支付"),
    REFUNDING(2, "退款中"),
    REFUND_SUCCESS(3, "退款成功"),
    REFUND_FAIL(4, "退款失败");

    private final Integer status;
    private final String desc;
}
