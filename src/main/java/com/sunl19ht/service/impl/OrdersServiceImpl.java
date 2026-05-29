package com.sunl19ht.service.impl;

import com.sunl19ht.mapper.CarMapper;
import com.sunl19ht.mapper.DeactivateCarMapper;
import com.sunl19ht.mapper.OrdersMapper;
import com.sunl19ht.pojo.entity.Orders;
import com.sunl19ht.pojo.entity.Refunds;
import com.sunl19ht.service.OrdersService;
import com.sunl19ht.utils.WeChatPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private WeChatPayUtil weChatPayUtil;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private DeactivateCarMapper deactivateCarMapper;

    @Autowired
    private CarMapper carMapper;

    @Override
    public void checkOrderImages(String orderNumber){
        log.info("checkOrderImages");
        Orders order = ordersMapper.getOrderByNumber(orderNumber);
        if (order.getId() != null &&
                order.getDrivingLicensePhoto() != null &&
                order.getDrivingLicenseBackPhoto() != null
                && order.getIdCardBackPhoto() != null &&
                order.getVehiclePhotos() != null &&
                order.getIdCardPhoto() != null && order.getVehicleVideos() != null) {
            ordersMapper.updateOrderStatus(orderNumber);
        }
    }

    /**
     * 退款
     * @param refunds
     */
    @Override
    public void refunds(Refunds refunds) throws Exception {
        Orders order = ordersMapper.getOrderByNumber(refunds.getOrderNumber());
        String outRefundNo = "TK" + System.currentTimeMillis(); // 商户退款号
        if (order == null) {
            throw new RuntimeException("订单不存在！");
        }
        if (!(refunds.getPrice() == null || refunds.getPrice() == 0)) {
            weChatPayUtil.refund(order, outRefundNo, new BigDecimal(refunds.getPrice()), order.getTotalFee());
        }
    }

    @Override
    @Transactional
    public void checkReturnImage(String orderNumber) {
        Orders order = ordersMapper.getOrderByNumber(orderNumber);
        if (order.getId() != null &&
                order.getReturnCarImage() != null &&
                order.getReturnCarVideos() != null &&
                order.getExteriorDamagePhotos() != null &&
                order.getExteriorDamageVideos() != null &&
                order.getInteriorDamagePhotos() != null &&
                order.getInteriorDamageVideos() != null) {
            ordersMapper.updateStatus(orderNumber);
            deactivateCarMapper.delLogOfTime(orderNumber);
        }
    }
}
