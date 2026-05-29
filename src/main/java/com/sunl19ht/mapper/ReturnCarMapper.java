package com.sunl19ht.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ReturnCarMapper {
    @Update("UPDATE dingdan SET car_status = '已完成' WHERE order_number = #{orderNumber}")
    void updateStatusByOrderNumber(String orderNumber);
}
