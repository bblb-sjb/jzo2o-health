package com.jzo2o.health.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author itcast
 * @since 2023-11-02
 */
@Data
@ApiModel("订单详情响应体")
public class OrdersDetailResDTO {
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
     * 套餐适用性别，0：不限，1：男，2：女
     */
    @ApiModelProperty("套餐适用性别，0：不限，1：男，2：女")
    private Integer setmealSex;

    /**
     * 套餐适用年龄
     */
    @ApiModelProperty("套餐适用年龄")
    private String setmealAge;

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
     * 预约日期，格式：yyyy-MM-dd
     */
    @ApiModelProperty("预约日期，格式：yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;

    /**
     * 体检人姓名
     */
    @ApiModelProperty("体检人姓名")
    private String checkupPersonName;

    /**
     * 体检人性别，0：不限，1：男，2女
     */
    @ApiModelProperty("体检人性别，0：不限，1：男，2女")
    private Integer checkupPersonSex;

    /**
     * 体检人电话
     */
    @ApiModelProperty("体检人电话")
    private String checkupPersonPhone;

    /**
     * 体检人身份证号
     */
    @ApiModelProperty("体检人身份证号")
    private String checkupPersonIdcard;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long memberId;

    /**
     * 用户电话
     */
    @ApiModelProperty("用户电话")
    private String memberPhone;

    /**
     * 支付时间
     */
    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    /**
     * 支付渠道
     */
    @ApiModelProperty("支付渠道")
    private String tradingChannel;

    /**
     * 支付服务交易单号
     */
    @ApiModelProperty("支付服务交易单号")
    private Long tradingOrderNo;

    /**
     * 第三方支付的交易号
     */
    @ApiModelProperty("第三方支付的交易号")
    private String transactionId;

    /**
     * 支付服务退款单号
     */
    @ApiModelProperty("支付服务退款单号")
    private Long refundNo;

    /**
     * 第三方支付的退款单号
     */
    @ApiModelProperty("第三方支付的退款单号")
    private String refundId;

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

    /**
     * 取消或退款时间
     */
    @ApiModelProperty("取消或退款时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cancelTime;

    /**
     * 取消或退款原因
     */
    @ApiModelProperty("取消或退款原因")
    private String cancelReason;
}
