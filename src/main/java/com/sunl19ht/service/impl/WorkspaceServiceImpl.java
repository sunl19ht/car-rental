package com.sunl19ht.service.impl;

import com.sunl19ht.mapper.WorkspaceMapper;
import com.sunl19ht.pojo.dto.OrdersDTO;
import com.sunl19ht.pojo.vo.OrdersStatusVO;
import com.sunl19ht.pojo.vo.WorkspaceVO;
import com.sunl19ht.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private WorkspaceMapper workspaceMapper;

    @Override
    public OrdersStatusVO getOrderStatus(Timestamp date) {
//        // date为空 使用当前时间
//        if (date == null) {
//            date = new Timestamp(System.currentTimeMillis());
//        }
        List<OrdersDTO> ordersDTOList = workspaceMapper.getOrderStatus(date);
        OrdersStatusVO ordersVO = new OrdersStatusVO();
        Integer pickup = 0;
        Integer waitReturn = 0;
        Integer success = 0;
        if (ordersDTOList != null && ordersDTOList.size() > 0) {
            for (OrdersDTO ordersDTO : ordersDTOList) {
                if (ordersDTO.getUserOrderStatus().equals("待取车")) {
                    pickup++;
                } else if (ordersDTO.getCarStatus().equals("待还车")) {
                    waitReturn++;
                } else if (ordersDTO.getCarStatus().equals("已完成")) {
                    success++;
                }
            }
        }
        ordersVO.setWaitPickUp(pickup);
        ordersVO.setSuccess(success);
        ordersVO.setWaitReturn(waitReturn);
        return ordersVO;
    }
}
