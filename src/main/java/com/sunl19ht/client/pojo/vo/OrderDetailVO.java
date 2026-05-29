package com.sunl19ht.client.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sunl19ht.client.pojo.entity.Address;
import com.sunl19ht.client.pojo.entity.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVO implements Serializable {
    // 订单id
    private String id;

    // 车辆id
    private Integer carId;

    // 订单号
    private String orderNumber;

    // 取消订单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime cancelTime;

    // 用户订单状态
    private String userOrderStatus;

    // 订单总价格
    private BigDecimal totalFee;

    // 基本租车费
    private BigDecimal baseRentalFee;

    // 保障费
    private BigDecimal insuranceFee;

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
    private List<Address> address;

    // 送取车方式
    private String carWay;

    // 车辆图片
    private String carImageUrl;

    // 服务保障
    private String servicePolicy;

    // 取车时间
    private LocalDateTime startTime;

    // 还车时间
    private LocalDateTime endTime;

    private String timeOut;
}
