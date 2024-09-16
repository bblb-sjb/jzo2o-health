package com.jzo2o.health.model.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("检查项响应模型")
public class CheckItemResDTO {
    /**
     * 检查项id
     */
    @ApiModelProperty("检查项id")
    private Integer id;

    /**
     * 检查项名称
     */
    @ApiModelProperty("检查项名称")
    private String name;
}