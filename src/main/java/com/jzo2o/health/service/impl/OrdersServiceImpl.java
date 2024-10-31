package com.jzo2o.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzo2o.api.trade.dto.response.TradingResDTO;
import com.jzo2o.api.trade.enums.TradingStateEnum;
import com.jzo2o.common.constants.UserType;
import com.jzo2o.common.model.CurrentUserInfo;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.common.model.msg.TradeStatusMsg;
import com.jzo2o.common.utils.BeanUtils;
import com.jzo2o.health.handler.OrdersHandler;
import com.jzo2o.health.mapper.OrdersCancelledMapper;
import com.jzo2o.health.mapper.OrdersRefundMapper;
import com.jzo2o.health.model.domain.OrdersCancelled;
import com.jzo2o.health.model.domain.OrdersRefund;
import com.jzo2o.health.model.dto.OrdersCancelledDTO;
import com.jzo2o.health.model.dto.request.OrdersPageQueryReqDTO;
import com.jzo2o.health.model.dto.response.*;
import com.jzo2o.health.service.client.TradingClient;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.DbRuntimeException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.api.trade.dto.request.NativePayReqDTO;
import com.jzo2o.api.trade.dto.response.NativePayResDTO;
import com.jzo2o.api.trade.enums.PayChannelEnum;
import com.jzo2o.common.expcetions.BadRequestException;
import com.jzo2o.common.expcetions.CommonException;
import com.jzo2o.common.utils.DateUtils;
import com.jzo2o.common.utils.ObjectUtils;
import com.jzo2o.health.enums.OrderPayStatusEnum;
import com.jzo2o.health.enums.OrderStatusEnum;
import com.jzo2o.health.mapper.MemberMapper;
import com.jzo2o.health.mapper.OrdersMapper;
import com.jzo2o.health.model.domain.Orders;
import com.jzo2o.health.model.dto.request.PlaceOrderReqDTO;
import com.jzo2o.health.properties.TradeProperties;
import com.jzo2o.health.service.IOrdersService;
import com.jzo2o.health.service.ISetmealService;
import com.jzo2o.health.service.client.NativePayClient;
import com.jzo2o.mvc.utils.UserContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jzo2o.health.constant.RedisConstants;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.jzo2o.common.constants.ErrorInfo.Code.TRADE_FAILED;

@Slf4j
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    @Resource
    private RedisTemplate<String, Long> redisTemplate;
    @Resource
    private ISetmealService setmealService;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private OrdersServiceImpl owner;
    @Resource
    private TradeProperties tradeProperties;
    @Resource
    private NativePayClient nativePayClient;
    @Resource
    private TradingClient tradingClient;
    @Resource
    private OrdersHandler ordersHandler;
    @Resource
    private OrdersCancelledMapper ordersCancelledMapper;
    @Resource
    private OrdersRefundMapper ordersRefundMapper;

    /**
     * 生成订单id 格式：{yyMMdd}{13位id}
     *
     * @return
     */
    private Long generateOrderId() {
        //通过Redis自增序列得到序号
        Long id = redisTemplate.opsForValue().increment(RedisConstants.ORDER_PAGE_QUERY, 1);
        //生成订单号   2位年+2位月+2位日+13位序号
        long orderId = DateUtils.getFormatDate(LocalDateTime.now(), "yyMMdd") * 10000000000000L + id;
        return orderId;
    }

    @Override
    public PlaceOrderResDTO userPlaceOrder(PlaceOrderReqDTO placeOrderReqDTO) {
        //1.远程调用获取套餐相关信息
        SetmealDetailResDTO setmealDetail = setmealService.findDetail(placeOrderReqDTO.getSetmealId());
        if (setmealDetail == null) {
            throw new BadRequestException("套餐不存在");
        }
        //2 下单前数据准备
        Orders orders = new Orders();
        //2.1 生成订单id
        Long orderId = generateOrderId();
        //2.2 设置订单id
        orders.setId(orderId);
        //2.3 设置订单状态
        orders.setOrderStatus(OrderStatusEnum.NO_PAY.getStatus());
        //2.4 设置支付状态
        orders.setPayStatus(OrderPayStatusEnum.NO_PAY.getStatus());

        //3 套餐信息
        //3.1 设置套餐id
        orders.setSetmealId(placeOrderReqDTO.getSetmealId());
        //3.2 设置套餐名称
        orders.setSetmealName(setmealDetail.getName());
        //3.3 设置套餐类型
        orders.setSetmealSex(Integer.valueOf(setmealDetail.getSex()));
        //3.4 设置套餐年龄
        orders.setSetmealAge(setmealDetail.getAge());
        //3.5 设置套餐服务
        orders.setSetmealImg(setmealDetail.getImg());
        //3.6 设置套餐服务
        orders.setSetmealRemark(setmealDetail.getRemark());
        //3.7 设置套餐价格
        orders.setSetmealPrice(BigDecimal.valueOf(setmealDetail.getPrice()));

        //4 用户信息
        //4.1 设置预约时间
        orders.setReservationDate(placeOrderReqDTO.getReservationDate());
        //4.2 设置体检人姓名
        orders.setCheckupPersonName(placeOrderReqDTO.getCheckupPersonName());
        //4.3 设置体检人性别
        orders.setCheckupPersonSex(placeOrderReqDTO.getCheckupPersonSex());
        //4.4 设置体检人电话
        orders.setCheckupPersonPhone(placeOrderReqDTO.getCheckupPersonPhone());
        //4.5 设置体检人身份证
        orders.setCheckupPersonIdcard(placeOrderReqDTO.getCheckupPersonIdcard());
        //4.6 设置下单人id
        orders.setMemberId(UserContext.currentUserId());
        //4.7 设置下单人姓名
        orders.setMemberPhone(memberMapper.selectById(UserContext.currentUserId()).getPhone());

        //5 支付信息
        //5.1 设置支付时间
        orders.setPayTime(null);
        //5.2 设置支付方式
        orders.setTradingChannel(null);
        //5.3 设置支付服务交易单号
        orders.setTradingOrderNo(null);
        //5.4 设置第三方支付的交易号
        orders.setTransactionId(null);
        //5.5 设置支付服务退款单号
        orders.setRefundNo(null);
        //5.6 设置第三方支付的退款单号
        orders.setRefundId(null);

        //6 其他信息
        //6.1 设置排序字段
        //LocalDate变为LocalDateTime
        LocalDate date=placeOrderReqDTO.getReservationDate();
        LocalDateTime localDateTime = date.atStartOfDay();
        long sortBy = DateUtils.toEpochMilli(localDateTime) + orders.getId() % 100000;
        orders.setSortBy(sortBy);
        //6.2 设置创建时间
        orders.setCreateTime(LocalDateTime.now());
        //6.3 设置更新时间
        orders.setUpdateTime(LocalDateTime.now());

        //7 插入数据库
        owner.add(orders);
        //8 返回结果
        return new PlaceOrderResDTO(orders.getId());
    }


    @Override
    public OrdersPayResDTO pay(Long id, PayChannelEnum tradingChannel) {
        Orders orders =  baseMapper.selectById(id);
        if (ObjectUtil.isNull(orders)) {
            throw new CommonException(TRADE_FAILED, "订单不存在");
        }
        //订单的支付状态为成功直接返回
        if (Objects.equals(OrderPayStatusEnum.PAY_SUCCESS.getStatus(), orders.getPayStatus())
                && ObjectUtil.isNotEmpty(orders.getTradingOrderNo())) {
            OrdersPayResDTO ordersPayResDTO = new OrdersPayResDTO();
            BeanUtil.copyProperties(orders, ordersPayResDTO);
            ordersPayResDTO.setProductOrderNo(orders.getId());
            return ordersPayResDTO;
        } else {
            //生成二维码
            NativePayResDTO nativePayResDTO = generateQrCode(orders, tradingChannel);
            OrdersPayResDTO ordersPayResDTO = BeanUtil.toBean(nativePayResDTO, OrdersPayResDTO.class);
            return ordersPayResDTO;
        }
    }

    @Override
    public OrdersPayResDTO payResult(Long id) {
        //查看订单是否存在
        Orders orders =  baseMapper.selectById(id);
        if (ObjectUtil.isNull(orders)) {
            throw new CommonException(TRADE_FAILED, "订单不存在");
        }
        //如果当前订单支付状态为未支付，并且交易单号不为空，则调用支付服务查询支付结果
        Integer payStatus = orders.getPayStatus();
        if (Objects.equals(OrderPayStatusEnum.NO_PAY.getStatus(), orders.getPayStatus())
                && ObjectUtil.isNotEmpty(orders.getTradingOrderNo())) {
            //拿到交易单号
            Long tradingOrderNo = orders.getTradingOrderNo();
            //根据交易单号请求支付结果接口
            TradingResDTO tradResultByTradingOrderNo = tradingClient.findTradResultByTradingOrderNo(tradingOrderNo);
            //如果支付结果为成功，则更新订单状态
            if (ObjectUtil.isNotNull(tradResultByTradingOrderNo)
                    && ObjectUtil.equals(tradResultByTradingOrderNo.getTradingState(), TradingStateEnum.YJS)) {
                //设置订单的支付状态成功
                TradeStatusMsg msg = TradeStatusMsg.builder()
                        .productOrderNo(orders.getId())
                        .tradingChannel(tradResultByTradingOrderNo.getTradingChannel())
                        .statusCode(TradingStateEnum.YJS.getCode())
                        .tradingOrderNo(tradResultByTradingOrderNo.getTradingOrderNo())
                        .transactionId(tradResultByTradingOrderNo.getTransactionId())
                        .build();
                owner.paySuccess(msg);
                //构造返回数据
                OrdersPayResDTO ordersPayResDTO = BeanUtils.toBean(tradResultByTradingOrderNo, OrdersPayResDTO.class);
                ordersPayResDTO.setPayStatus(OrderPayStatusEnum.PAY_SUCCESS.getStatus());
                return ordersPayResDTO;
            }
        }
        OrdersPayResDTO ordersPayResDTO = new OrdersPayResDTO();
        ordersPayResDTO.setPayStatus(payStatus);
        ordersPayResDTO.setProductOrderNo(orders.getId());
        ordersPayResDTO.setTradingOrderNo(orders.getTradingOrderNo());
        ordersPayResDTO.setTradingChannel(orders.getTradingChannel());
        return ordersPayResDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void paySuccess(TradeStatusMsg tradeStatusMsg) {
        //查询订单
        Orders orders = baseMapper.selectById(tradeStatusMsg.getProductOrderNo());
        if (ObjectUtil.isNull(orders)) {
            throw new CommonException(TRADE_FAILED, "订单不存在");
        }
        //校验支付状态如果不是待支付状态则不作处理
        if (ObjectUtil.notEqual(OrderPayStatusEnum.NO_PAY.getStatus(), orders.getPayStatus())) {
            log.info("更新订单支付成功，当前订单:{}支付状态不是待支付状态", orders.getId());
            return;
        }
        //校验订单状态如果不是待支付状态则不作处理
        if (ObjectUtils.notEqual(OrderStatusEnum.NO_PAY.getStatus(),orders.getOrderStatus())) {
            log.info("更新订单支付成功，当前订单:{}状态不是待支付状态", orders.getId());
        }

        //第三方支付单号校验
        if (ObjectUtil.isEmpty(tradeStatusMsg.getTransactionId())) {
            throw new CommonException("支付成功通知缺少第三方支付单号");
        }
        //更新订单的支付状态及第三方交易单号等信息
        boolean update = lambdaUpdate()
                .eq(Orders::getId, orders.getId())
                .set(Orders::getPayTime, LocalDateTime.now())//支付时间
                .set(Orders::getTradingOrderNo, tradeStatusMsg.getTradingOrderNo())//交易单号
                .set(Orders::getTradingChannel, tradeStatusMsg.getTradingChannel())//支付渠道
                .set(Orders::getTransactionId, tradeStatusMsg.getTransactionId())//第三方支付交易号
                .set(Orders::getPayStatus, OrderPayStatusEnum.PAY_SUCCESS.getStatus())//支付状态
                .set(Orders::getOrderStatus, OrderStatusEnum.WAITING_CHECKUP.getStatus())//更新订单状态为待体检
                .update();
        if(!update){
            log.info("更新订单:{}支付成功失败", orders.getId());
            throw new CommonException("更新订单"+orders.getId()+"支付成功失败");
        }
    }

    @Override
    public void cancel(OrdersCancelledDTO ordersCancelledDTO) {
        //查询订单信息
        Orders orders = getById(ordersCancelledDTO.getId());
        BeanUtils.copyProperties(orders,ordersCancelledDTO);
        ordersCancelledDTO.setRealPayAmount(orders.getSetmealPrice());
        if (ObjectUtil.isNull(orders)) {
            throw new DbRuntimeException("找不到要取消的订单,订单号：{}",ordersCancelledDTO.getId());
        }
        //订单状态
        Integer ordersStatus = orders.getOrderStatus();

        if(Objects.equals(OrderStatusEnum.NO_PAY.getStatus(), ordersStatus)){ //订单状态为待支付
            owner.cancelByNoPay(ordersCancelledDTO);
        }else if(Objects.equals(OrderStatusEnum.WAITING_CHECKUP.getStatus(), ordersStatus)){ //订单状态为待体检
            owner.cancelByDispatching(ordersCancelledDTO);
            //新启动一个线程请求退款
            ordersHandler.requestRefundNewThread(orders.getId());
        }else{
            throw new CommonException("当前订单状态不支持取消");
        }
    }

    @Override
    public List<Orders> queryOverTimePayOrdersListByCount(Integer count) {
        //根据订单创建时间查询超过15分钟未支付的订单
        List<Orders> list = lambdaQuery()
                //查询待支付状态的订单
                .eq(Orders::getOrderStatus, OrderStatusEnum.NO_PAY.getStatus())
                //小于当前时间减去15分钟，即待支付状态已过15分钟
                .lt(Orders::getCreateTime, LocalDateTime.now().minusMinutes(15))
                .last("limit " + count)
                .list();
        return list;
    }

    @Override
    public OrdersDetailResDTO getDetailById(Long id) {
        Orders orders = getById(id);
        if (ObjectUtil.isNull(orders)) {
            throw new CommonException("订单不存在");
        }
        //懒加载
        //如果支付过期则取消订单
        orders = canalIfPayOvertime(orders);
        OrdersDetailResDTO ordersDetailResDTO = BeanUtil.toBean(orders, OrdersDetailResDTO.class);
        //查询是否取消
        OrdersCancelled ordersCancelled = ordersCancelledMapper.selectById(id);
        if (ObjectUtil.isNotNull(ordersCancelled)) {
            ordersDetailResDTO.setCancelReason(ordersCancelled.getCancelReason());
            ordersDetailResDTO.setCancelTime(ordersCancelled.getCancelTime());
        }
        return ordersDetailResDTO;
    }

    /**
     * 如果支付过期则取消订单
     * @param orders
     */
    private Orders canalIfPayOvertime(Orders orders){
        //创建订单未支付15分钟后自动取消
        if(Objects.equals(orders.getOrderStatus(), OrderStatusEnum.NO_PAY.getStatus()) && orders.getCreateTime().plusMinutes(15).isBefore(LocalDateTime.now())){
            //查询支付结果，如果支付最新状态仍是未支付进行取消订单
            OrdersPayResDTO ordersPayResDTO = payResult(orders.getId());
            int payResultFromTradServer = ordersPayResDTO.getPayStatus();
            if(payResultFromTradServer != OrderPayStatusEnum.PAY_SUCCESS.getStatus()){
                //取消订单
                OrdersCancelled orderCancelDTO = BeanUtil.toBean(orders, OrdersCancelled.class);
                orderCancelDTO.setCancellerType(UserType.SYSTEM);
                orderCancelDTO.setCancelReason("订单超时支付，自动取消");

                OrdersCancelledDTO ordersCancelledDTO = BeanUtil.copyProperties(orderCancelDTO, OrdersCancelledDTO.class);
                CurrentUserInfo currentUserInfo = UserContext.currentUser();
                ordersCancelledDTO.setCancellerId(currentUserInfo.getId());
                ordersCancelledDTO.setCancellerName(currentUserInfo.getName());
                ordersCancelledDTO.setCancellerType(currentUserInfo.getUserType());

                cancel(ordersCancelledDTO);
                orders = getById(orders.getId());
            }
        }
        return orders;
    }

    @Override
    public List<OrdersResDTO> pageQuery(Integer ordersStatus, Long sortBy) {
        LambdaQueryWrapper<Orders> queryWrapper = Wrappers.<Orders>lambdaQuery()
                .eq(ObjectUtils.isNotNull(ordersStatus), Orders::getOrderStatus, ordersStatus)
                .lt(ObjectUtils.isNotNull(sortBy), Orders::getSortBy, sortBy)
                .eq(Orders::getMemberId, UserContext.currentUserId());
        queryWrapper.orderByDesc(Orders::getSortBy);
        List<Orders> ordersList = list(queryWrapper);
        return ordersList.stream().map(orders -> BeanUtil.toBean(orders, OrdersResDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PageResult adminPageQuery(OrdersPageQueryReqDTO ordersPageQueryReqDTO) {
        // 初始化分页对象
        Page<Orders> page = new Page<>(ordersPageQueryReqDTO.getPageNo(), ordersPageQueryReqDTO.getPageSize());

        // 创建LambdaQueryWrapper，添加条件时确保非空判断
        LambdaQueryWrapper<Orders> queryWrapper = Wrappers.<Orders>lambdaQuery()
                .eq(ObjectUtils.isNotNull(ordersPageQueryReqDTO.getOrderStatus()), Orders::getOrderStatus, ordersPageQueryReqDTO.getOrderStatus())
                .like(ObjectUtils.isNotEmpty(ordersPageQueryReqDTO.getMemberPhone()), Orders::getMemberPhone, ordersPageQueryReqDTO.getMemberPhone());

        // 执行分页查询
        this.page(page, queryWrapper);  // 确认this.page方法调用正确

        // 将查询结果转换为DTO列表
        List<OrdersResDTO> ordersResDTOList = page.getRecords().stream()
                .map(orders -> BeanUtil.toBean(orders, OrdersResDTO.class))
                .collect(Collectors.toList());

        PageResult pageResult = new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setList(ordersResDTOList);
        // 返回分页结果
        return pageResult;
    }


    @Override
    public AdminOrdersDetailResDTO adminAggregation(Long id) {
        Orders orders = getById(id);
        if (ObjectUtil.isNull(orders)) {
            throw new CommonException("订单不存在");
        }

        //查询订单信息
        AdminOrdersDetailResDTO adminOrdersDetailResDTO = new AdminOrdersDetailResDTO();
        AdminOrdersDetailResDTO.OrderInfo orderInfo = new AdminOrdersDetailResDTO.OrderInfo();
        BeanUtil.copyProperties(orders, orderInfo);
        //查询支付信息
        AdminOrdersDetailResDTO.PayInfo payInfo = new AdminOrdersDetailResDTO.PayInfo();
        BeanUtil.copyProperties(orders, payInfo);
        payInfo.setThirdOrderId(orders.getTransactionId());
        //查询退款信息
        AdminOrdersDetailResDTO.RefundInfo refundInfo = new AdminOrdersDetailResDTO.RefundInfo();
        AdminOrdersDetailResDTO.CancelInfo cancelInfo = new AdminOrdersDetailResDTO.CancelInfo();
        OrdersCancelled ordersCancelled = ordersCancelledMapper.selectById(id);
        if(ObjectUtil.isNotNull(ordersCancelled)) {
            if (Objects.equals(orders.getOrderStatus(), OrderStatusEnum.CANCELLED.getStatus())) {
                cancelInfo.setCancelReason(ordersCancelled.getCancelReason());
                cancelInfo.setCancelTime(ordersCancelled.getCancelTime());
            }
            else if (Objects.equals(orders.getOrderStatus(), OrderStatusEnum.CLOSED.getStatus())) {
                refundInfo.setRefundId(orders.getRefundId());
                refundInfo.setRefundStatus(orders.getPayStatus());
                refundInfo.setCancelTime(ordersCancelled.getCancelTime());
                refundInfo.setTradingChannel(orders.getTradingChannel());
                refundInfo.setCancelReason(ordersCancelled.getCancelReason());
            }
        }
        adminOrdersDetailResDTO.setOrderInfo(orderInfo);
        adminOrdersDetailResDTO.setPayInfo(payInfo);
        adminOrdersDetailResDTO.setRefundInfo(refundInfo);
        adminOrdersDetailResDTO.setCancelInfo(cancelInfo);
        return adminOrdersDetailResDTO;
    }

    @Override
    public OrdersCountResDTO countByStatus() {
        OrdersCountResDTO ordersCountResDTO = new OrdersCountResDTO();
        //查询已体检订单数量
        Integer checkedCount = lambdaQuery().eq(Orders::getOrderStatus, OrderStatusEnum.COMPLETED_CHECKUP.getStatus()).count();
        ordersCountResDTO.setCompletedCheckupCount(checkedCount);
        //查询待体检订单数量
        Integer waitingCheckupCount = lambdaQuery().eq(Orders::getOrderStatus, OrderStatusEnum.WAITING_CHECKUP.getStatus()).count();
        ordersCountResDTO.setWaitingCheckupCount(waitingCheckupCount);
        //查询已取消订单数量
        Integer cancelledCount = lambdaQuery().eq(Orders::getOrderStatus, OrderStatusEnum.CANCELLED.getStatus()).count();
        ordersCountResDTO.setCancelledCount(cancelledCount);
        //查询待支付订单数量
        Integer noPayCount = lambdaQuery().eq(Orders::getOrderStatus, OrderStatusEnum.COMPLETED_CHECKUP.getStatus()).count();
        ordersCountResDTO.setNoPayCount(noPayCount);
        //查询已关闭订单数量
        Integer closedCount = lambdaQuery().eq(Orders::getOrderStatus, OrderStatusEnum.CLOSED.getStatus()).count();
        ordersCountResDTO.setClosedCount(closedCount);
        //全部订单数量
        ordersCountResDTO.setTotalCount(checkedCount + waitingCheckupCount + cancelledCount + noPayCount + closedCount);
        return ordersCountResDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelByDispatching(OrdersCancelledDTO ordersCancelledDTO) {
        //保存取消订单记录
        OrdersCancelled ordersCanceled = BeanUtil.toBean(ordersCancelledDTO, OrdersCancelled.class);
        ordersCanceled.setCancelTime(LocalDateTime.now());
        ordersCancelledMapper.insert(ordersCanceled);
        //更新订单状态为关闭订单
        Orders orders = getById(ordersCancelledDTO.getId());
        orders.setOrderStatus(OrderStatusEnum.CLOSED.getStatus());
        orders.setPayStatus(OrderPayStatusEnum.REFUNDING.getStatus());
        orders.setUpdateTime(LocalDateTime.now());
        boolean update = updateById(orders);
        if(!update){
            throw new CommonException("订单:"+orders.getId()+"取消失败");
        }
        //添加退款记录
        OrdersRefund ordersRefund = new OrdersRefund();
        ordersRefund.setId(ordersCancelledDTO.getId());
        ordersRefund.setTradingOrderNo(ordersCancelledDTO.getTradingOrderNo());
        ordersRefund.setRealPayAmount(ordersCancelledDTO.getRealPayAmount());
        ordersRefundMapper.insert(ordersRefund);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelByNoPay(OrdersCancelledDTO ordersCancelledDTO) {
        //保存取消订单记录
        OrdersCancelled ordersCanceled = BeanUtil.toBean(ordersCancelledDTO, OrdersCancelled.class);
        ordersCanceled.setCancelTime(LocalDateTime.now());
        ordersCancelledMapper.insert(ordersCanceled);
        //更新订单状态为取消订单
        Orders orders = getById(ordersCancelledDTO.getId());
        orders.setOrderStatus(OrderStatusEnum.CANCELLED.getStatus());
        orders.setUpdateTime(LocalDateTime.now());
        boolean update = updateById(orders);
        if(!update){
            throw new CommonException("订单:"+orders.getId()+"取消失败");
        }
    }

    //生成二维码
    private NativePayResDTO generateQrCode(Orders orders, PayChannelEnum tradingChannel) {

        //判断支付渠道
        Long enterpriseId = ObjectUtil.equal(PayChannelEnum.ALI_PAY, tradingChannel) ?
                tradeProperties.getAliEnterpriseId() : tradeProperties.getWechatEnterpriseId();

        //构建支付请求参数
        NativePayReqDTO nativePayReqDTO = new NativePayReqDTO();
        //商户号
        nativePayReqDTO.setEnterpriseId(enterpriseId);
        //体检业务系统标识
        nativePayReqDTO.setProductAppId("jzo2o.health");
        //体检订单号
        nativePayReqDTO.setProductOrderNo(orders.getId());
        //支付渠道
        nativePayReqDTO.setTradingChannel(tradingChannel);
        //支付金额
        nativePayReqDTO.setTradingAmount(orders.getSetmealPrice());
        //备注信息
        nativePayReqDTO.setMemo(orders.getSetmealName());
        //判断是否切换支付渠道
        if (ObjectUtil.isNotEmpty(orders.getTradingChannel())
                && ObjectUtil.notEqual(orders.getTradingChannel(), tradingChannel.toString())) {
            nativePayReqDTO.setChangeChannel(true);
        }
        //生成支付二维码
        NativePayResDTO downLineTrading = nativePayClient.createHealthDownLineTrading(nativePayReqDTO);
        if(ObjectUtils.isNotNull(downLineTrading)){
            log.info("订单:{}请求支付,生成二维码:{}",orders.getId(),downLineTrading.toString());
            boolean update = lambdaUpdate()
                    .eq(Orders::getId, downLineTrading.getProductOrderNo())
                    .set(Orders::getTradingOrderNo, downLineTrading.getTradingOrderNo())
                    .set(Orders::getTradingChannel, downLineTrading.getTradingChannel())
                    .update();
            if(!update){
                throw new CommonException("订单:"+orders.getId()+"请求支付更新交易单号失败");
            }
        }
        return downLineTrading;
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(Orders orders) {
        boolean save = this.save(orders);
        if (!save) {
            throw new DbRuntimeException("下单失败");
        }
    }
}
