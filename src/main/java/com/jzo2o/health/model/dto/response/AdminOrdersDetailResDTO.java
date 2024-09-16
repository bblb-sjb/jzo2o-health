package com.jzo2o.health.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 管理端订单详情
 *
 * @author itcast
 * @create 2023/11/6 17:31
 **/
@Data
public class AdminOrdersDetailResDTO {
    @ApiModelProperty("订单信息")
    private OrderInfo orderInfo;

    @ApiModelProperty("支付信息")
    private PayInfo payInfo;

    @ApiModelProperty("退款信息")
    private RefundInfo refundInfo;

    @ApiModelProperty("订单取消理由")
    private CancelInfo cancelInfo;

    @Data
    @ApiModel("订单信息模型")
    public static class OrderInfo {
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
         * 创建时间
         */
        @ApiModelProperty("创建时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;
    }

    @Data
    @ApiModel("支付记录模型")
    public static class PayInfo {
        /**
         * 支付状态，0：未支付，1：已支付
         */
        @ApiModelProperty("支付状态，0：未支付，1：已支付")
        private Integer payStatus;

        /**
         * 支付渠道，ALI_PAY：支付宝，WECHAT_PAY：微信
         */
        @ApiModelProperty("支付渠道，ALI_PAY：支付宝，WECHAT_PAY：微信")
        private String tradingChannel;

        /**
         * 三方流水,微信支付订单号或支付宝订单号
         */
        @ApiModelProperty("三方流水,微信支付订单号或支付宝订单号")
        private String thirdOrderId;

        /**
         * 支付时间
         */
        @ApiModelProperty("支付时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime payTime;
    }

    @Data
    @ApiModel("退款信息模型")
    public static class RefundInfo {
        /**
         * 退款状态，2：退款中，3：退款成功，4：退款失败
         */
        @ApiModelProperty("退款状态，2：退款中，3：退款成功，4：退款失败")
        private Integer refundStatus;

        /**
         * 退款时间;和取消时间保持一致
         */
        @ApiModelProperty("退款时间;和取消时间保持一致")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime cancelTime;

        /**
         * 退款理由和取消理由一致
         */
        @ApiModelProperty("退款理由和取消理由一致")
        private String cancelReason;

        /**
         * 支付渠道，ALI_PAY：支付宝，WECHAT_PAY：微信
         */
        @ApiModelProperty("支付渠道，ALI_PAY：支付宝，WECHAT_PAY：微信")
        private String tradingChannel;

        /**
         * 第三方支付的退款单号
         */
        @ApiModelProperty("第三方支付的退款单号")
        private String refundId;
    }

    @Data
    @ApiModel("订单取消模型")
    public static class CancelInfo {
        /**
         * 取消时间
         */
        @ApiModelProperty("取消时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime cancelTime;

        /**
         * 取消理由
         */
        @ApiModelProperty("取消理由")
        private String cancelReason;
    }
}
