package com.jzo2o.health.constant;

/**
 * 支付相关常量
 *
 * @author itcast
 * @create 2023/11/7 17:46
 **/
public class TradeConstants {
    /**
     * 支付来源
     */
    public static final String PRODUCT_APP_ID="health";

    /**
     * rabbitmq支付更新状态队列
     */
    public static final String MQ_TRADE_QUEUE="jzo2o.queue.health.trade.update.Status";

}
