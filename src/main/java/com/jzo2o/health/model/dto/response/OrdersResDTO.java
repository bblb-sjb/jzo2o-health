package com.jzo2o.health.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 订单响应
 *
 * @author itcast
 * @create 2023/11/3 19:17
 **/
@Data
@ApiModel("订单响应模型")
public class OrdersResDTO {
    /**
     * 订单id
     */
    @ApiModelProperty("订单id")
    private Long id;

    /**
     * 订单状态，0：未支付，100：待体检，200：已体检，300：已关闭，400：已取消
     */
    @ApiModelProperty("订单状态，0：未支付，100：待体检，200：已体检，300：已关闭，400：已取消")
    private Integer orderStatus;

    /**
     * 支付状态，0：未支付，1：已支付，2：退款中，3：退款成功，4：退款失败
     */
    @ApiModelProperty("支付状态，0：未支付，1：已支付，2：退款中，3：退款成功，4：退款失败")
    private Integer payStatus;


    /**
     * 套餐id
     */
    @ApiModelProperty("套餐id")
    private Integer setmealId;

    /**
     * 套餐名称
     */
    @ApiModelProperty("套餐名称")
    private String setmealName;

    /**
     * 套餐价格
     */
    @ApiModelProperty("套餐价格")
    private BigDecimal setmealPrice;

    /**
     * 套餐适用年龄
     */
    @ApiModelProperty("套餐适用年龄")
    private String setmealAge;

    /**
     * 套餐适用性别
     */
    @ApiModelProperty("套餐适用性别")
    private Integer setmealSex;

    /**
     * 套餐图片
     */
    @ApiModelProperty("套餐图片")
    private String setmealImg;

    /**
     * 套餐说明
     */
    @ApiModelProperty("套餐说明")
    private String setmealRemark;

    /**
     * 预约日期
     */
    @ApiModelProperty("预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;

    /**
     * 体检人
     */
    @ApiModelProperty("体检人")
    private String checkupPersonName;

    /**
     * 体检人身份证号
     */
    @ApiModelProperty("体检人身份证号")
    private String checkupPersonIdcard;

    /**
     * 用户电话
     */
    @ApiModelProperty("用户电话")
    private String memberPhone;

    /**
     * 排序字段（取创建时间的时间戳）
     */
    @ApiModelProperty("排序字段（取创建时间的时间戳）")
    private Long sortBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
