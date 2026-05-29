package com.sunl19ht.client.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Address implements Serializable {
    // 地址名称
    private String name;

    // 详细地址
    private String address;

    // 区
    private String district;

    // 纬度
    private String latitude;

    // 经度
    private String longitude;
}