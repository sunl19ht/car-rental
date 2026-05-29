package com.sunl19ht.client.pojo.vo;

import com.sunl19ht.client.pojo.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.util.Times;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO implements Serializable {
    private Integer id;

    // 用户订单状态
    private String userOrderStatus;

    // 订单号
    private String orderNumber;

    // 车辆图片
    private String carImageUrl;

    // 订单创建时间
    private Timestamp createTime;

    private List<Address> address;

    private String carBrand;

    private String provider = "振云出行";

    private LocalDateTime upCarTime;

    private LocalDateTime returnCarTime;

    private BigDecimal price;

    private String pickupLocation;

    private String returnLocation;

    private String cityPairs;
}
