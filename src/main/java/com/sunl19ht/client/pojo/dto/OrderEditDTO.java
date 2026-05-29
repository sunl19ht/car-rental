package com.sunl19ht.client.pojo.dto;

import com.sunl19ht.client.pojo.entity.Address;
import com.sunl19ht.client.pojo.entity.PaymentInfo;
import com.sunl19ht.client.pojo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEditDTO implements Serializable {

    // 订单号
    private String orderNumber;

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

}
