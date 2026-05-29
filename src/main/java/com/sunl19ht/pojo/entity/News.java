package com.sunl19ht.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News  implements Serializable {

    private Integer id;

    private String carBrand;

    private String licensePlate;

    private Timestamp upCarTime;

    private Timestamp returnCarTime;

    // 消息状态 预定订单 取消订单
    private String newStatus;

    private Integer read;

    private Integer dingdanId;
}
