package com.sunl19ht.client.service;


import com.sunl19ht.client.pojo.entity.Address;
import com.sunl19ht.client.pojo.entity.PaymentInfo;
import com.sunl19ht.client.pojo.vo.PaymentInfoVO;
import com.sunl19ht.client.pojo.vo.PriceDetailVO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    /**
     * 用户下单
     * @param paymentInfo
     * @return
     */
    PaymentInfoVO submit(PaymentInfo paymentInfo);

    PriceDetailVO detail(Integer carId, LocalDateTime upCarTime, LocalDateTime returnCarTime, String priceDetailVO, String carWay);
    PriceDetailVO detail(Integer carId, LocalDateTime upCarTime, LocalDateTime returnCarTime, String guaranteeService, String carWay, List<Address> address);
}
