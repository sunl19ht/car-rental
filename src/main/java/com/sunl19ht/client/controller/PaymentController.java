package com.sunl19ht.client.controller;

import com.sunl19ht.client.pojo.entity.Address;
import com.sunl19ht.client.pojo.entity.Detail;
import com.sunl19ht.client.pojo.entity.PaymentInfo;
import com.sunl19ht.client.pojo.vo.PaymentInfoVO;
import com.sunl19ht.client.pojo.vo.PriceDetailVO;
import com.sunl19ht.client.service.PaymentService;
import com.sunl19ht.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Tag(name = "用户端-下单接口")
@RestController
@RequestMapping("api/user/pay")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * 用户下单
     * @param paymentInfo
     * @return
     */
    @Operation(summary = "用户下单")
    @PostMapping("payment")
    @Parameter(name = "paymentInfo",description = "下单信息",required = true,in= ParameterIn.QUERY)
    public Result<PaymentInfoVO> payment(@RequestBody PaymentInfo paymentInfo) {
        log.info("用户下单{}", paymentInfo);
        PaymentInfoVO paymentInfoVO = paymentService.submit(paymentInfo);
        return Result.success(paymentInfoVO);
    }

    @PostMapping("detail")
    @Operation(summary = "获取详细金额")
    @Parameter(name = "carId",description = "车辆id",required = true,in= ParameterIn.QUERY)
    @Parameter(name = "upCarTime",description = "取车时间",required = true,in= ParameterIn.QUERY)
    @Parameter(name = "returnCarTime",description = "还车时间",required = true,in= ParameterIn.QUERY)
    @Parameter(name = "guaranteeService",description = "服务保障",required = true,in= ParameterIn.QUERY)
    @Parameter(name = "guaranteeService",description = "服务保障",required = true,in= ParameterIn.QUERY)
    @Parameter(name = "carWay",description = "取车方式",required = true,in= ParameterIn.QUERY)
    @Parameter(name = "address",description = "地址",required = true,in= ParameterIn.QUERY)
    public Result<PriceDetailVO> detail(@RequestBody Detail detail) {
        log.info("用户下单{}", detail);
        PriceDetailVO priceDetailVO = paymentService.detail(detail.getCarId(), detail.getUpCarTime(), detail.getReturnCarTime(), detail.getGuaranteeService(), detail.getCarWay(), detail.getAddress());
        return Result.success(priceDetailVO);
    }

}
