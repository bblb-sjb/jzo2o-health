package com.jzo2o.health.model.dto.request;

import com.jzo2o.common.model.dto.PageQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 检查组分页查询类
 *
 * @author itcast
 * @create 2023/7/4 12:43
 **/
@Data
@ApiModel("检查组分页查询类")
public class CommonPageQueryReqDTO extends PageQueryDTO {
    @ApiModelProperty("关键词，查询编码或者名称")
    private String keyword;
}
