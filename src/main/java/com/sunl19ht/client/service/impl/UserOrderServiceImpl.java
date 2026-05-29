package com.sunl19ht.client.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sunl19ht.client.mapper.UserMapper;
import com.sunl19ht.client.mapper.UserOrderMapper;
import com.sunl19ht.client.pojo.dto.*;
import com.sunl19ht.client.pojo.entity.*;
import com.sunl19ht.client.pojo.vo.*;
import com.sunl19ht.client.service.UserOrderService;
import com.sunl19ht.context.BaseContext;
import com.sunl19ht.exception.OrderBusinessException;
import com.sunl19ht.mapper.CarMapper;
import com.sunl19ht.mapper.DeactivateCarMapper;
import com.sunl19ht.mapper.OrdersMapper;
import com.sunl19ht.pojo.vo.CarVO;
import com.sunl19ht.utils.WeChatPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private WeChatPayUtil weChatPayUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserOrderMapper userOrderMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private DeactivateCarMapper deactivateCarMapper;
//    @Autowired
//    private UserOrderService userOrderService;

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // 当前登录用户id
        Long userId = Long.valueOf(BaseContext.getCurrentId()); // 获取当前用户的微信id
        log.info("当前用户微信id：" + userId);

        User user = userMapper.getById(userId); // 根据用户的微信id查询数据库
        com.sunl19ht.pojo.entity.Orders order = ordersMapper.getOrderByNumber(ordersPaymentDTO.getOrderNumber());

        //调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtil.pay(
                ordersPaymentDTO.getOrderNumber(), //商户订单号
                order.getTotalFee(), //支付金额，单位 元
//                new BigDecimal("1"),
                order.getCarBrand(), //商品描述
                user.getOpenid() //微信用户的openid
        );

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new OrderBusinessException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;
    }

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    public void paySuccess(String outTradeNo) {

        // 根据订单号查询订单
        Orders ordersDB = userOrderMapper.getByNumber(outTradeNo);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .orderNumber(ordersDB.getOrderNumber())
                .userOrderStatus("待取车")   // 订单状态
                .carStatus("待取车")
                .paymentStatus("已支付")
                .build();

        userOrderMapper.update(orders);
    }

    @Override
    public void refundSuccess(String outTradeNo) {
        Orders ordersDB = userOrderMapper.getByNumber(outTradeNo);
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .orderNumber(ordersDB.getOrderNumber())
                .userOrderStatus("已退款")
                .carStatus("已退款")
                .paymentStatus("已退款")
                .build();
        userOrderMapper.update(orders);
    }

    @Override
    public MyOrdersVO getOrderListById(Integer userId) {
        MyOrdersVO myOrdersVO = new MyOrdersVO();
        List<PaymentInfoDTO> paymentInfoDTOList = userOrderMapper.getOrderListById(userId);
        for (PaymentInfoDTO paymentInfoDTO : paymentInfoDTOList) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(paymentInfoDTO, orderVO);
            List<Address> addressList = JSONObject.parseObject(paymentInfoDTO.getAddress(), new TypeReference<List<Address>>() {});

            orderVO.setAddress(addressList);
            orderVO.setId(paymentInfoDTO.getId());
            orderVO.setUserOrderStatus(paymentInfoDTO.getUserOrderStatus());
            orderVO.setUpCarTime(paymentInfoDTO.getUpCarTime().toLocalDateTime());
            orderVO.setReturnCarTime(paymentInfoDTO.getReturnCarTime().toLocalDateTime());

            if (addressList.size() == 1) {
                orderVO.setPickupLocation(addressList.get(0).getAddress());
                orderVO.setReturnLocation(addressList.get(0).getAddress());
                orderVO.setCityPairs(addressList.get(0).getDistrict() + "-" + addressList.get(0).getDistrict());
            }
            if (addressList.size() == 2) {
                orderVO.setPickupLocation(addressList.get(0).getAddress());
                orderVO.setReturnLocation(addressList.get(1).getAddress());
                orderVO.setCityPairs(addressList.get(0).getDistrict() + "-" + addressList.get(1).getDistrict());
            }


            if (paymentInfoDTO.getUserOrderStatus().equals("待取车"))
                myOrdersVO.getTripTaking().add(orderVO);
            if (paymentInfoDTO.getUserOrderStatus().equals("已完成"))
                myOrdersVO.getTripHaveTaking().add(orderVO);
            myOrdersVO.getAllOrder().add(orderVO);
        }
        return myOrdersVO;
    }

    @Override
    @Transactional
    public OrderDetailVO detail(String orderNumber, Integer userId) {
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        OrderDetailDTO orderDetailDTO = userOrderMapper.getOrderDetail(orderNumber, userId);
        if (orderDetailDTO == null) {
            throw new OrderBusinessException("订单不存在");
        }
        CarVO carVO = carMapper.getCarById(orderDetailDTO.getCarId());
        orderDetailDTO.setCarSeat(carVO.getSeat());
        orderDetailDTO.setGearbox(carVO.getGearbox());
        orderDetailDTO.setCarYear(carVO.getCarYear());
        orderDetailDTO.setCarImageUrl(carVO.getImageUrl());

        if (orderDetailDTO.getBasicInsuranceFee() != null) {
            orderDetailVO.setInsuranceFee(orderDetailDTO.getBasicInsuranceFee());
        }
        orderDetailVO.setInsuranceFee(orderDetailDTO.getPremiumInsuranceFee());

        BeanUtils.copyProperties(orderDetailDTO, orderDetailVO);
        List<Address> addresses = JSONObject.parseObject(orderDetailDTO.getAddress(), new TypeReference<List<Address>>() {
        });
        orderDetailVO.setAddress(addresses);
        LocalDateTime createTime = orderDetailDTO.getCreateTime();
        createTime = createTime.plusHours(8);
        createTime = createTime.plusMinutes(15);
        orderDetailVO.setTimeOut("请在" + createTime.getMonthValue() + "月" + createTime.getDayOfMonth() + "日 " + createTime.getHour() + ":" + createTime.getMinute() + "前付款，此订单保留15分钟");
        orderDetailVO.setAgencyFee(orderDetailDTO.getPickupFee());
        log.info("查询用户{}的订单详情：{}", userId, orderDetailDTO);
        return orderDetailVO;
    }

    @Override
    @Transactional
    public String cancelOrder(CancelOrder cancelOrder) {
        PaymentInfoDTO paymentInfoDTO = userOrderMapper.getOrderByOrderNumber(cancelOrder.getOrderNumber());
        if (!Objects.equals(paymentInfoDTO.getUserId(), BaseContext.getCurrentId())) {
            throw new IllegalArgumentException("订单取消失败，订单不属于当前用户");
        }
        if (paymentInfoDTO.getUserOrderStatus().equals("待还车") || paymentInfoDTO.getCarStatus().equals("待还车")) {
            throw new OrderBusinessException("订单取消失败，订单状态为待还车");
        }
        log.error("订单取消：{}", paymentInfoDTO);
        long hours = Duration.between(Instant.now(), paymentInfoDTO.getUpCarTime().toInstant()).toHours();
        Integer carId = paymentInfoDTO.getCarId();

        // 启用车辆
        carMapper.enableCar(carId);

        // 删除车辆的下线时间
        deactivateCarMapper.delLogOfTime(paymentInfoDTO.getOrderNumber());
        if (hours <= 4) {
            // 超出四小时收取订单总金额的100%作为违约金
            userOrderMapper.cancelOrder(cancelOrder.getOrderNumber());
            return "订单取消成功，违约金为订单总金额的100%";
        }

        return "订单取消成功，取车前四小时免费取消";
    }

    @Override
    public String getOrderStatus(String orderNumber) {
        return ordersMapper.getStatus(orderNumber);
    }

    @Override
    public String queryOrderStatus(String orderNumber) {
        String s = ordersMapper.queryStatus(orderNumber);
        return s;
    }

    public void validateOrder(OrderSubmitDTO order) {
        // 校验必填字段
        if (order.getCarId() == null || order.getUserId() == null) {
            throw new IllegalArgumentException("车辆ID和用户ID不能为空");
        }

        // 校验时间逻辑
        if (order.getRentalStartTime() == null || order.getRentalEndTime() == null) {
            throw new IllegalArgumentException("租车时间不能为空");
        }
        if (order.getRentalStartTime().after(order.getRentalEndTime())) {
            throw new IllegalArgumentException("租车开始时间不能晚于结束时间");
        }
        if (order.getPickupDate() != null && order.getPickupDate().after(order.getRentalStartTime())) {
            throw new IllegalArgumentException("取车日期不能晚于租车开始时间");
        }

        // 校验金额
        if (order.getTotalPrice() == null || order.getTotalPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("总价格必须大于0");
        }
        if (order.getDiscount() != null && order.getDiscount().compareTo(order.getTotalPrice()) > 0) {
            throw new IllegalArgumentException("优惠金额不能大于总价格");
        }

        // 校验支付状态
        if (order.getPaymentStatus() != null && (order.getPaymentStatus() < 0 || order.getPaymentStatus() > 1)) {
            throw new IllegalArgumentException("支付状态不合法");
        }

//        // 校验保险类型（示例）
//        if (order.getInsuranceType() != null && !isValidInsuranceType(order.getInsuranceType())) {
//            throw new IllegalArgumentException("无效的保险类型");
//        }
    }

//    // 保险类型校验
//    private boolean isValidInsuranceType(String insuranceType) {
//        List<String> validInsuranceTypes = Arrays.asList("基础服务", "优享服务");
//        return validInsuranceTypes.contains(insuranceType);
//    }


}
