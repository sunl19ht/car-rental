package com.sunl19ht.client.mapper;

import com.sunl19ht.client.pojo.vo.UseCarVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UseCarMapper {
    List<UseCarVO> findAvailableCarIds(LocalDateTime pickupTime, LocalDateTime returnTime);


}
