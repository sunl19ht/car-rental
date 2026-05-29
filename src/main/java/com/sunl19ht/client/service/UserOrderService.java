package com.sunl19ht.client.service;

import com.sunl19ht.client.pojo.dto.OrderEditDTO;
import com.sunl19ht.client.pojo.dto.OrderSubmitDTO;
import com.sunl19ht.client.pojo.dto.OrdersPaymentDTO;
import com.sunl19ht.client.pojo.entity.CancelOrder;
import com.sunl19ht.client.pojo.vo.MyOrdersVO;
import com.sunl19ht.client.pojo.vo.OrderDetailVO;
import com.sunl19ht.client.pojo.vo.OrderPaymentVO;
import com.sunl19ht.client.pojo.vo.OrdersSubmitVO;

public interface UserOrderService {
    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    MyOrdersVO getOrderListById(Integer userId);

    OrderDetailVO detail(String orderNumber, Integer userId);

    String cancelOrder(CancelOrder cancelOrder);

    String getOrderStatus(String orderNumber);

    String queryOrderStatus(String orderNumber);

    void refundSuccess(String outTradeNo);
}
