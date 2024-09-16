package com.jzo2o.health.model.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author itcast
 * @create 2023/11/3 18:53
 **/
@Data
@ApiModel("检查组详情")
public class CheckGroupDetailResDTO {
    /**
     * 检查组id
     */
    @ApiModelProperty("检查组id")
    private Integer id;

    /**
     * 检查组名称
     */
    @ApiModelProperty("检查组名称")
    private String name;

    /**
     * 检查项列表
     */
    @ApiModelProperty("检查项列表")
    private List<CheckItemResDTO> checkItemList;
}
