package com.sunl19ht.pojo.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CarDO {
    // 主键id
    private Integer id;

    // 车型
    private Integer carType;

    // 汽车品牌
    private String brand;

    // 品牌型号
    private String brandType;

    // 座位
    private Integer seat;

    // 驾驶模式
    private Integer drivingMode;

    // 动力
    private Integer vehicleType;

    // 门数
    private Integer door;

    // 年款
    private Integer year;

    // 车牌照
    private String licensePlate;

    // 创建日期
    private Timestamp createTime;

    // 修改日期
    private Timestamp updateTime;
}
