package com.jzo2o.health.model.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单数量
 *
 * @author itcast
 * @create 2023/11/8 16:02
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("订单数量响应模型")
public class OrdersCountResDTO {

    /**
     * 待支付数量
     */
    @ApiModelProperty("待支付数量")
    private Integer noPayCount = 0;

    /**
     * 待体检数量
     */
    @ApiModelProperty("待体检数量")
    private Integer waitingCheckupCount = 0;

    /**
     * 已体检数量
     */
    @ApiModelProperty("已体检数量")
    private Integer completedCheckupCount = 0;

    /**
     * 已关闭数量
     */
    @ApiModelProperty("已关闭数量")
    private Integer closedCount = 0;

    /**
     * 已取消数量
     */
    @ApiModelProperty("已取消数量")
    private Integer cancelledCount = 0;

    /**
     * 全部数量
     */
    @ApiModelProperty("全部数量")
    private Integer totalCount = 0;
}
