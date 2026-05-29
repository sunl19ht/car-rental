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
public class Orders implements Serializable {
    // 订单id
    private Integer id;

    // 车辆品牌
    private String carBrand;

    // 订单号
    private String orderNumber;

    // 身份证正面
    private String idCardPhoto;

    // 身份证反面
    private String idCardBackPhoto;

    // 驾驶证正面
    private String drivingLicensePhoto;

    // 驾驶证反面
    private String drivingLicenseBackPhoto;

    // 车辆照片
    private String vehiclePhotos;

    // 车辆视频
    private String vehicleVideos;

    // 总金额
    private BigDecimal totalFee;

    private String returnCarImage;

    private String returnCarVideos;

    private String exteriorDamagePhotos;

    private String exteriorDamageVideos;

    private String interiorDamagePhotos;

    private String interiorDamageVideos;
}
