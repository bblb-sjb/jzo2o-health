package com.jzo2o.health.model.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author itcast
 * @since 2023-11-01
 */
@Data
@Builder
@ApiModel("预约设置响应模型")
public class ReservationSettingResDTO {
    /**
     * 预约日期
     */
    @ApiModelProperty("预约日期")
    private String date;

    /**
     * 可预约人数
     */
    @ApiModelProperty("可预约人数")
    private Integer number;

    /**
     * 已预约人数
     */
    @ApiModelProperty("已预约人数")
    private Integer reservations;
}
