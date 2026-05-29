package com.sunl19ht.client.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 订单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    // 订单id
    private Integer id;

    // 订单号
    private String orderNumber;

    // 用户id
    private Integer userId;

    // 用户姓名
    private String name;

    // 用户手机号
    private String phoneNumber;

    // 身份证
    private String idCard;

    // 牌照
    private String licensePlate;

    // 车辆品牌
    private String carBrand;

    // 金额
    private BigDecimal price;

    // 取车时间
    private Timestamp upCarTime;

    // 还车时间
    private Timestamp returnCarTime;

    // 总时间
    private Integer totalTimes;

    // 取车方式
    private String carWay;

    // 地址
    private List<Address> address;

    // 开始时间
    private Timestamp startTime;

    // 结束时间
    private Timestamp endTime;

    // 车辆状态 有无损伤
    private String status;

    // 车辆品牌 todo暂时用不上
    private String carStyle;

    // 车辆照片路径
    private String vehiclePhotos;

    // 身份证正面
    private String idCardPhoto;

    // 驾驶证正面
    private String drivingLicensePhoto;

    // 可退款金额
    private BigDecimal refundableAmount;

    // 租车合同 todo暂时用不上
    private String contractUrl;

    // 车辆视频
    private String vehicleVideos;

    // 外饰损伤 todo暂时用不上
    private String exteriorDamage;

    // 外饰损伤照片
    private String exteriorDamagePhotos;

    // 外饰损伤视频
    private String exteriorDamageVideos;

    // 内饰损伤 todo暂时用不上
    private String interiorDamage;

    // 内饰损伤照片
    private String interiorDamagePhotos;

    // 内饰损伤视频
    private String interiorDamageVideos;

    // 总金额
    private BigDecimal totalFee;

    // 服务保障
    private String serviceGuarantee;

    // 服务政策
    private String servicePolicy;

    // 基本租车费
    private BigDecimal baseRentalFee;

    // 车行手续费
    private BigDecimal agencyFee;

    // 上门取车费
    private BigDecimal pickupFee;

    // 基本保障费
    private BigDecimal basicInsuranceFee;

    // 优享保障费
    private BigDecimal premiumInsuranceFee;

    // 身份证反面
    private String idCardBackPhoto;

    // 驾驶证反面
    private String drivingLicenseBackPhoto;

    // 用户订单状态
    private String userOrderStatus;

    // 车辆照片
    private String carImageUrl;

    // 支付状态
    private String paymentStatus;

    // 订单状态
    private String carStatus;

    // 车辆id
    private Integer carId;

    // 还车照片
    private String returnCarImage;

    // 还车视频
    private String returnCarVideos;

    // 创建时间
    private Timestamp createTime;

    // 订单取消时间
    private Timestamp cancelTime;
}
