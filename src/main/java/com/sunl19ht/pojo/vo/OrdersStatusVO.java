package com.sunl19ht.pojo.vo;

import lombok.Data;

@Data
public class OrdersStatusVO {
    private Integer waitReturn; // 待还车
    private Integer waitPickUp; // 待取车
    private Integer success; // 已完成
}
