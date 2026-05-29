package com.sunl19ht.service;

import com.sunl19ht.pojo.vo.OrdersStatusVO;

import java.sql.Timestamp;

public interface WorkspaceService {
    OrdersStatusVO getOrderStatus(Timestamp date);
}
