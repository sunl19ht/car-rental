package com.sunl19ht.client.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {
    // 地址名称
    String name;

    // 详细地址
    String address;

    // 区
    String district;

    // 纬度
    String latitude;

    // 经度
    String longitude;
}