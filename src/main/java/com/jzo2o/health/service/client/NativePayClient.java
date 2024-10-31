package com.jzo2o.health.service.client;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.jzo2o.api.trade.NativePayApi;
import com.jzo2o.api.trade.dto.request.NativePayReqDTO;
import com.jzo2o.api.trade.dto.response.NativePayResDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author SJB
 * @author 1.0
 * @description TODO
 * @date 2024/7/11 22:38
 */

@Component
@Slf4j
public class NativePayClient {
    @Resource
    private NativePayApi nativePayApi;

    @SentinelResource(value = "createHealthDownLineTrading", fallback = "createHealthDownLineTradingFallback", blockHandler = "createHealthDownLineTradingBlockHandler")
    public NativePayResDTO createHealthDownLineTrading(NativePayReqDTO nativePayDTO) {
        log.error("扫码支付，收银员通过收银台或商户后台调用此接口，生成二维码后，展示给用户，由用户扫描二维码完成订单支付。");
        // 调用其他微服务方法
        NativePayResDTO nativePayResDTO = nativePayApi.createDownLineTrading(nativePayDTO);
        return nativePayResDTO;
    }

    //执行异常走
    public NativePayResDTO createHealthDownLineTradingFallback(Long id, Throwable throwable) {
        log.error("非限流、熔断等导致的异常执行的降级方法，id:{},throwable:", id, throwable);
        return null;
    }

    //熔断后的降级逻辑
    public NativePayResDTO createHealthDownLineTradingBlockHandler(Long id, BlockException blockException) {
        log.error("触发限流、熔断时执行的降级方法，id:{},blockException:", id, blockException);
        return null;
    }
}
