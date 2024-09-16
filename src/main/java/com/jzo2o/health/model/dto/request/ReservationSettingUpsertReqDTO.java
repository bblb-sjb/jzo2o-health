package com.jzo2o.health.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author itcast
 * @since 2023-11-01
 */
@Data
@ApiModel("预约设置更新请求模型")
public class ReservationSettingUpsertReqDTO {
    /**
     * 预约日期
     */
    @ApiModelProperty(value = "预约日期，格式：yyyy-MM", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    /**
     * 可预约人数
     */
    @ApiModelProperty(value = "可预约人数", required = true)
    private Integer number;
}
