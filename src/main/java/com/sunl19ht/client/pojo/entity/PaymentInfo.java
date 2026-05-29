package com.sunl19ht.client.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 用户提交订单信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo implements Serializable {

    // 用户id
    private Integer userId;

    // 车辆id
    private Integer carId;

    // 起租时间
    private Timestamp upCarTime;

    // 还车时间
    private Timestamp returnCarTime;

    // 送还方式
    private String carWay;

    // 地址   数据库存入json格式
    private List<Address> address;

    // 驾驶员姓名
    private String name;

    // 驾驶员电话
    private String phoneNumber;

    // 驾驶员身份证
    private String idCard;

    // 服务保障
    private String servicePolicy;

    // 唯一时间戳
    private String uniqueTimestamp;
}
