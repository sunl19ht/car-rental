package com.sunl19ht.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Refunds implements Serializable {
    private String orderNumber;
    private Integer price; // 单位分， 1元 = 100分
}
