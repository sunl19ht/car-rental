package com.sunl19ht.client.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarTypeVO implements Serializable {
    private ArrayList<Object> allType; // 全部车型
    private ArrayList<Object> ecoType; // 经济型

}
