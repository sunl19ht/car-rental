package com.sunl19ht.controller;

import com.sunl19ht.pojo.Result;
import com.sunl19ht.pojo.vo.OrdersStatusVO;
import com.sunl19ht.service.WorkspaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@Slf4j
@RestController
@RequestMapping("api/workspace")
public class WorkspaceController {
    @Autowired
    public WorkspaceService workspaceService;

    /**
     * 工作台首页
     * @param date
     */
    @GetMapping("status")
    public Result<OrdersStatusVO> getOrderStatus(Timestamp date) {
        log.info("我的订单");
        OrdersStatusVO ordersVO = workspaceService.getOrderStatus(date);
        return Result.success(ordersVO);
    }
}
