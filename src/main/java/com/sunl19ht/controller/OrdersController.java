package com.sunl19ht.controller;

import com.sunl19ht.pojo.Result;
import com.sunl19ht.pojo.entity.Refunds;
import com.sunl19ht.pojo.vo.OrdersInfoVO;
import com.sunl19ht.pojo.vo.OrdersVO;
import com.sunl19ht.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("api/orders")
@RestController
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
//
//    /**
//     * 我的订单 -> 获取订单
//     * @param orderStatus 订单状态
//     * @return
//     */
//    @GetMapping("get")
//    public Result<List<OrdersVO>> getOrders(@RequestParam Integer orderStatus){
//        log.info("获取订单：{}", orderStatus);
//        List<OrdersVO> ordersVOList = ordersService.getOrders(orderStatus);
//        return Result.success(ordersVOList);
//    }
//
//    @GetMapping("get/info")
//    public Result<OrdersInfoVO> getOrderByOrderNumber(@RequestParam Integer orderNumber){
//        log.info("获取订单详情：{}", orderNumber);
//        OrdersInfoVO ordersInfoVO = ordersService.getOrderByOrderNumber(orderNumber);
//        return Result.success(ordersInfoVO);
//    }

    @PostMapping("refunds")
    public Result refunds(@RequestBody Refunds refunds) throws Exception {
        log.info("退款：{}", refunds);
        ordersService.refunds(refunds);
        return Result.success();
    }
}
