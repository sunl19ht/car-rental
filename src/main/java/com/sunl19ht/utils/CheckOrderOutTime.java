package com.sunl19ht.utils;

import com.sunl19ht.client.mapper.UserOrderMapper;
import com.sunl19ht.client.pojo.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class CheckOrderOutTime {

    @Autowired
    private UserOrderMapper userOrderMapper;

    /**
     * 订单超时15分钟设置状态为已取消
     */
    @Scheduled(fixedRate = 5000)
    public void checkOrderOutTime() {
        List<Orders> ordersList = userOrderMapper.getNotTimeOutOrder();
        for (Orders orders : ordersList) {
            // 计算时间差
            Duration duration = Duration.between(orders.getCreateTime().toLocalDateTime().plusHours(8), LocalDateTime.now());
            long minutesDifference = Math.abs(duration.toMinutes()); // 取绝对值
            if (!orders.getOrderNumber().isEmpty() && minutesDifference >= 15) {
                log.info("订单：{}超时，设置订单状态为已取消！", orders.getOrderNumber());
                userOrderMapper.cancelOrder(orders.getOrderNumber());
            }
        }
    }
}
