package com.sunl19ht.client.controller;

import com.sunl19ht.client.pojo.dto.OrdersPaymentDTO;
import com.sunl19ht.client.pojo.entity.CancelOrder;
import com.sunl19ht.client.pojo.vo.MyOrdersVO;
import com.sunl19ht.client.pojo.vo.OrderDetailVO;
import com.sunl19ht.client.pojo.vo.OrderPaymentVO;
import com.sunl19ht.client.service.UserOrderService;
import com.sunl19ht.context.BaseContext;
import com.sunl19ht.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "用户端-订单接口")
@RequestMapping("api/user/order")
@RestController
public class OrderController {
    @Autowired
    private UserOrderService userOrderService;

    @GetMapping("detail")
    @Operation(summary = "获取订单详情")
    @Parameter(name = "orderNumber",description = "订单号",required = true,in= ParameterIn.QUERY)
    public Result<OrderDetailVO> detail(@RequestParam String orderNumber) {
        Long currentId = Long.valueOf(BaseContext.getCurrentId());
        OrderDetailVO orderDetailVO = userOrderService.detail(orderNumber, Math.toIntExact(currentId));
        return Result.success(orderDetailVO);
    }

    /**
     * 查询用户列表
     * @return
     */
    @GetMapping("/my")
    @Operation(summary = "查询用户订单列表")
    public Result<MyOrdersVO> getOrderList() {
        Long currentId = Long.valueOf(BaseContext.getCurrentId());
        MyOrdersVO res = userOrderService.getOrderListById(Math.toIntExact(currentId));
        log.info("查询用户{}的订单列表：{}", currentId, res);
        return Result.success(res);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO // 订单号 付款方式（仅支持微信支付）
     * @return
     */
    @PutMapping("/payment")
    @Operation(summary = "订单支付")
    @Parameter(name = "orderNumber",description = "订单号",required = true,in= ParameterIn.QUERY)
    @Parameter(name = "payMethod",description = "支付方式，默认为1",required = true,in= ParameterIn.QUERY)
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = userOrderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }

    /**
     * 取消订单
     * @return
     */
    @PostMapping("/cancel")
    @Operation(summary = "取消订单")
    @Parameter(name = "orderNumber",description = "订单号",required = true,in= ParameterIn.QUERY)
    public Result cancelOrder(@RequestBody CancelOrder cancelOrder) {
        String s = userOrderService.cancelOrder(cancelOrder);
        return Result.success(s);
    }

    @GetMapping("/payStatus")
    @Operation(summary = "查询订单支付状态")
    @Parameter(name = "orderNumber",description = "订单号",required = true,in= ParameterIn.QUERY)
    public Result<String> payStatus(String orderNumber) {
        String s = userOrderService.getOrderStatus(orderNumber);
        return Result.success(s);
    }

    @GetMapping("/orderStatus")
    public Result<String> orderStatus(String orderNumber) {
        String s = userOrderService.queryOrderStatus(orderNumber);
        return Result.success(s);
    }
}
