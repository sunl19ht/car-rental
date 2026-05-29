package com.sunl19ht.client.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersSubmitVO implements Serializable {

    // 订单id
    private Integer id;

    // 支付状态
    private Integer paymentStatus;

    // 取车地址
    private String pickupLocation;

    // 还车地址
    private String returnLocation;

    // 车辆id
    private Integer carId;
}
