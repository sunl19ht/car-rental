package com.sunl19ht.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class OrdersDTO {
    // 主键id
    private Integer id;

    private String carStatus;

    private String userOrderStatus;
}
