package com.sunl19ht.client.pojo.dto;

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
public class OrderDetailDTO implements Serializable {
    // 订单id
    private String id;

    // 车辆id
    private Integer carId;

    // 取消订单时间
    private LocalDateTime cancelTime;

    // 创建时间
    private LocalDateTime createTime;

    // 订单号
    private String orderNumber;

    // 送取车方式
    private String carWay;

    // 用户订单状态
    private String userOrderStatus;

    // 订单总价格
    private BigDecimal totalFee;

    // 基本租车费
    private BigDecimal baseRentalFee;

    // 基本保障费
    private BigDecimal basicInsuranceFee;

    // 优享保障费
    private BigDecimal premiumInsuranceFee;

    // 手续费
    private BigDecimal agencyFee;

    // 车辆品牌
    private String carBrand;

    // 车辆座位
    private String carSeat;

    // 车辆挡位
    private String gearbox;

    // 车辆年份
    private String carYear;

    // 取还地址
    private String address;

    // 取车时间
    private LocalDateTime startTime;

    // 还车时间
    private LocalDateTime endTime;

    // 服务保障
    private String servicePolicy;

    // 车辆图片
    private String carImageUrl;

    // 上门去车费
    private BigDecimal pickupFee;
}
