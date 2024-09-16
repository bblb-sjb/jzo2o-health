package com.jzo2o.health.model.dto.request;

import com.jzo2o.common.model.dto.PageQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 订单分页查询请求模型
 *
 * @author itcast
 * @create 2023/11/3 19:25
 **/
@Data
@ApiModel("订单分页查询请求模型")
public class OrdersPageQueryReqDTO extends PageQueryDTO {

    @ApiModelProperty(value = "订单id列表", hidden = true)
    private List<Long> ids;

    /**
     * 订单状态，0：未支付，100：待体检，200：已体检，300：已关闭，400：已取消
     */
    @ApiModelProperty("订单状态，0：未支付，100：待体检，200：已体检，300：已关闭，400：已取消")
    private Integer orderStatus;

    /**
     * 用户电话
     */
    @ApiModelProperty("用户电话")
    private String memberPhone;
}
