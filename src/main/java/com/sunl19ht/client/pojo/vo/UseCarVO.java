package com.sunl19ht.client.pojo.vo;

import com.sunl19ht.client.pojo.entity.CarTypePrice;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class UseCarVO {
    private String id; // 车辆id
//    private String carId;
    private String carBrand; // 品牌型号
    private String seat; // 座位数
    private String gearbox; // 挡位
    private String carYear; // 年份
    private String carType;
    private String imageUrl; // 图片
    private String licensePlate; // 牌照
    private BigDecimal price; // 价格
    private BigDecimal weekendPriceFake; // 周末虚价
    private BigDecimal weekendPrice; // 周末价格
    private BigDecimal normalPriceFake; // 平时虚价
    private BigDecimal normalPrice; // 平时价格
    private String option; // 可选
    private String trait; // 车辆特质
//    private List<UseCarVO> carTypePrices = new ArrayList<>(); // 车辆类型价格
}
