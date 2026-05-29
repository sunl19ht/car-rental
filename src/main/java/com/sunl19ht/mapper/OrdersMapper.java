package com.sunl19ht.mapper;

import com.sunl19ht.pojo.entity.Orders;
import com.sunl19ht.pojo.vo.OrdersInfoVO;
import com.sunl19ht.pojo.vo.OrdersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrdersMapper {
    Orders getOrderByNumber(String orderNumber);

    @Update("update dingdan set car_status = '待还车', user_order_status = '待还车' where order_number = #{orderNumber}")
    void updateOrderStatus(String orderNumber);

    @Update("update dingdan set car_status = '已完成', user_order_status = '已完成' where order_number = #{orderNumber}")
    void updateStatus(String orderNumber);

    @Select("select payment_status from dingdan where order_number = #{orderNumber}")
    String getStatus(String orderNumber);

    @Select("select car_status from dingdan where order_number = #{orderNumber}")
    String queryStatus(String orderNumber);
}
