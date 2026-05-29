package com.sunl19ht.pojo.vo;

import com.sunl19ht.pojo.entity.LogOffTime;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CarVO {
    // 车辆id
    private Integer id;

    // 车辆类别
    private String carType;

    // 汽车品牌
    private String carBrand;

    // 车牌号
    private String licensePlate;

    // 平时价格
    private BigDecimal normalPrice;

    // 车辆图片url
    private String imageUrl;

    // 周末价格
    private BigDecimal weekendPrice;

    // 车辆座位
    private String seat;

    // 挡位需求
    private String gearbox;

    // 车辆年份
    private String carYear;

    // 可选条件
    private String option;

    // 后缀
    private String suffix;

    // 车辆特质
    private String trait;

    // 周末价格(虚)
    private BigDecimal weekendPriceFake;

    // 平时价格(虚)
    private BigDecimal normalPriceFake;

    // 车辆状态
    private String state;

    // 下线记录
    private ArrayList<LogOffTime> logOffTime;
}
