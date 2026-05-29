package com.sunl19ht.client.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubmitDTO implements Serializable {
//
//    // 订单id
//    private Integer id;

    // 车辆id
    private Integer carId;

    // 租车开始时间
    private Timestamp rentalStartTime;

    // 租车结束时间
    private Timestamp rentalEndTime;

    // 取车日期
    private Timestamp pickupDate;

    // 还车日期
    private Timestamp returnDate;

    // 取车地址
    private String pickupLocation;

    // 还车地址
    private String returnLocation;

    // 用户id
    private Integer userId;

    // 车行保障服务
    private String insuranceType;

    // 总价格
    private BigDecimal totalPrice;

    // 优惠金额
    private BigDecimal discount;

    // 支付状态
    private Integer paymentStatus;

//    // 订单创建时间
//    private Timestamp createAt;

//    // 订单更新时间
//    private Timestamp updateAt;
}
