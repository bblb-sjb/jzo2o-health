package com.jzo2o.health.service.client;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.jzo2o.api.trade.TradingApi;
import com.jzo2o.api.trade.dto.response.TradingResDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class TradingClient {
    @Resource
    private TradingApi tradingApi;

    @SentinelResource(value = "findTradResultByTradingOrderNo", fallback = "findTradResultByTradingOrderNoFallback", blockHandler = "findTradResultByTradingOrderNoBlockHandler")
    public TradingResDTO findTradResultByTradingOrderNo(Long tradingOrderNo) {
        log.error("根据订单号查询订单信息，拿到交易交易单号,tradingOrderNo:{}",tradingOrderNo);
        // 调用其他微服务方法
        TradingResDTO tradingResDTO = tradingApi.findTradResultByTradingOrderNo(tradingOrderNo);
        return tradingResDTO;
    }

    //执行异常走
    public TradingResDTO findTradResultByTradingOrderNoFallback(Long tradingOrderNo, Throwable throwable) {
        log.error("非限流、熔断等导致的异常执行的降级方法，tradingOrderNo:{},throwable:", tradingOrderNo, throwable);
        return null;
    }

    //熔断后的降级逻辑
    public TradingResDTO findTradResultByTradingOrderNoBlockHandler(Long tradingOrderNo, BlockException blockException) {
        log.error("触发限流、熔断时执行的降级方法，tradingOrderNo:{},blockException:", tradingOrderNo, blockException);
        return null;
    }
}
