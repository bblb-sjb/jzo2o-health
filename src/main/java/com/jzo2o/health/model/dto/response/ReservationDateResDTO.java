package com.jzo2o.health.model.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 预约日期响应模型
 *
 * @author itcast
 * @create 2023/11/6 10:19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("预约日期响应模型")
public class ReservationDateResDTO {
    /**
     * 可预约日期
     */
    @ApiModelProperty("可预约日期")
    private List<String> dateList;
}
