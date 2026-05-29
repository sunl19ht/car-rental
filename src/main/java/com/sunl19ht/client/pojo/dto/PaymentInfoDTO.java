package com.sunl19ht.client.pojo.dto;

import com.sunl19ht.client.pojo.entity.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.util.Times;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoDTO implements Serializable {
    private Integer id;
    // 用户id
    private Integer userId;

    private Timestamp createTime;

    // 订单号
    private String orderNumber;

    // 车辆id
    private Integer carId;

    // 起租时间
    private Timestamp upCarTime;

    // 还车时间
    private Timestamp returnCarTime;

    // 送还方式
    private String carWay;

    // 地址   数据库存入json格式
    private String address;

    // 驾驶员姓名
    private String name;

    // 驾驶员电话
    private String phoneNumber;

    // 驾驶员身份证
    private String idCard;

    // 服务保障
    private String servicePolicy;

    // 价格
    private BigDecimal price;

    // 总时长
    private Integer totalTimes;

    // 车辆状态
    private String carStatus;

    // 起始时间
    private Timestamp startTime;

    // 结束时间
    private Timestamp endTime;

    // 车牌号
    private String licensePlate;

    // 车辆品牌
    private String carBrand;

    // 可退还金额
    private BigDecimal refundableAmount;

    // 总金额
    private BigDecimal totalFee;

    // 服务保障
    private String serviceGuarantee;

    // 基本租车费
    private BigDecimal baseRentalFee;

    // 优享租车费
    private BigDecimal premiumInsuranceFee;

    // 用户订单状态
    private String userOrderStatus;

    // 车辆图片
    private String carImageUrl;

    // 支付状态
    private String paymentStatus;

    // 上门取车费
    private BigDecimal pickupFee;
}
