package com.sunl19ht.client.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyOrdersVO implements Serializable {
    // 全部
    private List<OrderVO> allOrder = new ArrayList<>();

    // 待出行
    private List<OrderVO> tripTaking = new ArrayList<>();

    // 已出行
    private List<OrderVO> tripHaveTaking = new ArrayList<>();
}
