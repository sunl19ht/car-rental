package com.sunl19ht.client.mapper;

import com.sunl19ht.client.pojo.dto.OrderDetailDTO;
import com.sunl19ht.client.pojo.dto.PaymentInfoDTO;
import com.sunl19ht.client.pojo.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserOrderMapper {
    /**
     * 更新订单信息
     * @param orders  // 订单信息
     */
    @Update("UPDATE dingdan SET user_order_status = '待取车', car_status = '待取车', payment_status = '已付款' WHERE order_number = #{orderNumber}")
    void update(Orders orders);

    @Select("SELECT * FROM dingdan WHERE user_id = #{userId} AND car_image_url IS NOT NULL ORDER BY create_time DESC")
    List<PaymentInfoDTO> getOrderListById(Integer userId);

    @Select("SELECT * FROM dingdan WHERE order_number = #{orderNumber} and user_id = #{userId}")
    OrderDetailDTO getOrderDetail(String orderNumber, Integer userId);

    @Select("SELECT * FROM dingdan WHERE order_number = #{orderNumber}")
    PaymentInfoDTO getOrderByOrderNumber(String orderNumber);

    @Update("UPDATE dingdan SET user_order_status = '已取消', car_status = '已取消', cancel_time = NOW() WHERE order_number = #{orderNumber}")
    void cancelOrder(String orderNumber);

    @Select("SELECT * FROM dingdan WHERE order_number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    @Select("SELECT * FROM dingdan WHERE order_number IS NOT NULL AND user_order_status = '待支付' ORDER BY create_time DESC")
    List<Orders> getNotTimeOutOrder();

    @Update("UPDATE dingdan SET user_order_status = '待取车', car_status = '待取车', payment_status = '已付款' WHERE order_number = #{outTradeNo}")
    void updateByOrderNumber(String outTradeNo);
}
