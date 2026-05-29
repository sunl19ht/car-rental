package com.sunl19ht.service;

import com.sunl19ht.pojo.entity.Refunds;
import com.sunl19ht.pojo.vo.OrdersInfoVO;
import com.sunl19ht.pojo.vo.OrdersVO;

import java.util.List;

public interface OrdersService {
    void checkOrderImages(String orderNumber);

    void refunds(Refunds refunds) throws Exception;

    void checkReturnImage(String orderNumber);
}
