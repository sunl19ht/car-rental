package com.sunl19ht.client.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoVO implements Serializable {
    // 订单id
    private Long id;

    // 订单号
    private String orderNumber;

    // 订单总金额
    private BigDecimal orderAmount;

    // 租车费
    private BigDecimal rentalPrice;

    // 服务费
    private BigDecimal servicePrice;

    // 保障费
    private BigDecimal guaranteePrice;

    // 下单时间
    private LocalDateTime orderTime;
}
