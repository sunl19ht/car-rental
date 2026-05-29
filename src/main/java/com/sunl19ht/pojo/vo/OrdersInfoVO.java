package com.sunl19ht.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class OrdersInfoVO {

    private Integer id;
    // 汽车品牌
    private String carBrand;

    // 汽车型号
    private String brandModel;
    // 车牌照
    private String licensePlate;

    // 价格
    private BigDecimal price;

    // 驾驶人姓名
    private String driverName;

    // 订单id
    private Integer orderNumber;

    // 取车时间
    private Timestamp pickupTime;

    // 还车时间
    private Timestamp returnTime;

    // 租车时间
    private Timestamp leaseTime;

    // 取车方式
    private Integer pickupMethod;

    // 地址
    private String address;

    // 备注
    private String orderNotes;

    // 状态
    private Integer orderStatus;
}
