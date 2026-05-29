package com.sunl19ht.pojo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class CarDTO {
    private Integer id;

    // 车辆型号
    private String carBrand;

    // 车牌号
    private String licensePlate;

    private MultipartFile image;

    // 车辆类别
    private String carType;

    // 车辆座位
    private String seat;

    // 挡位需求
    private String gearbox;

    // 车辆年份
    private String carYear;

    // 后缀
    private String suffix;

//    // 可选条件
//    private String option;

    // 车辆特质
    private String trait;

    // 周末价格(虚)
    private BigDecimal weekendPriceFake;

    // 平时价格(虚)
    private BigDecimal normalPriceFake;

    // 车辆图片
    private String imageUrl;

    // 周末价格
    private BigDecimal weekendPrice;

    // 平时价格
    private BigDecimal normalPrice;

    // 车辆状态
    private Integer status;

    // 是否删除
    private Integer isDelete;
}
