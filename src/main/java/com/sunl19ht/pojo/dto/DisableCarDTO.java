package com.sunl19ht.pojo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DisableCarDTO {
    // 车辆id
    private Integer carId;

    // 下线说明
    private String desc;

    // 下线开始时间
    private Timestamp startTime;

    // 下线结束时间
    private Timestamp endTime;

    // 临时下线1/订单下线2
    private String user;
}
