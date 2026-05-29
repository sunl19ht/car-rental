package com.sunl19ht.mapper;

import com.sunl19ht.pojo.dto.OrdersDTO;
import com.sunl19ht.pojo.vo.OrdersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface WorkspaceMapper {

    @Select("select * from dingdan")
    List<OrdersDTO> getOrderStatus(Timestamp date);
}
