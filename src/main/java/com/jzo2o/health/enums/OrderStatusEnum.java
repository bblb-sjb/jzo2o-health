package com.jzo2o.health.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author itcast
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    NO_PAY(0, "待支付"),
    WAITING_CHECKUP(100, "待体检"),
    COMPLETED_CHECKUP(200, "已体检"),
    CLOSED(300, "已关闭"),
    CANCELLED(400, "已取消");

    private final Integer status;
    private final String desc;

    /**
     * 根据状态值获得对应枚举
     *
     * @param status 状态
     * @return 状态对应枚举
     */
    public static OrderStatusEnum codeOf(Integer status) {
        for (OrderStatusEnum orderStatusEnum : values()) {
            if (orderStatusEnum.status.equals(status)) {
                return orderStatusEnum;
            }
        }
        return null;
    }
}
