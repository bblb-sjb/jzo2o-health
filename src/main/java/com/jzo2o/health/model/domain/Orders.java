package com.jzo2o.health.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单状态，0：未支付，100：待体检，200：已体检，300：已关闭，400：已取消
     */
    private Integer orderStatus;

    /**
     * 支付状态，0：未支付，1：已支付，2：退款中，3：退款成功，4：退款失败
     */
    private Integer payStatus;

    /**
     * 套餐id
     */
    private Integer setmealId;

    /**
     * 套餐名称
     */
    private String setmealName;

    /**
     * 套餐价格
     */
    private BigDecimal setmealPrice;

    /**
     * 套餐适用性别，0：不限，1：男，2：女
     */
    private Integer setmealSex;

    /**
     * 套餐适用年龄
     */
    private String setmealAge;

    /**
     * 套餐图片
     */
    private String setmealImg;

    /**
     * 套餐说明
     */
    private String setmealRemark;

    /**
     * 预约日期，格式：yyyy-MM-dd
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;

    /**
     * 体检人姓名
     */
    private String checkupPersonName;

    /**
     * 体检人性别，0：不限，1：男，2女
     */
    private Integer checkupPersonSex;

    /**
     * 体检人电话
     */
    private String checkupPersonPhone;

    /**
     * 体检人身份证号
     */
    private String checkupPersonIdcard;

    /**
     * 用户id
     */
    private Long memberId;

    /**
     * 用户电话
     */
    private String memberPhone;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 支付渠道
     */
    private String tradingChannel;

    /**
     * 支付服务交易单号
     */
    private Long tradingOrderNo;

    /**
     * 第三方支付的交易号
     */
    private String transactionId;

    /**
     * 支付服务退款单号
     */
    private Long refundNo;

    /**
     * 第三方支付的退款单号
     */
    private String refundId;

    /**
     * 排序字段（取创建时间的时间戳）
     */
    private Long sortBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
