package com.sunl19ht.client.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDetailVO implements Serializable {

    // 总金额
    private BigDecimal totalPrice;

    // 总时间
    private Integer totalTime;

    // 优惠金额
    private BigDecimal discountPrice;

    // 原价
    private BigDecimal originalPrice;

    // 车辆租金
    private BigDecimal rentalPrice;

    // 服务费
    private BigDecimal servicePrice;

    // 保障费
    private BigDecimal guaranteePrice;
}
