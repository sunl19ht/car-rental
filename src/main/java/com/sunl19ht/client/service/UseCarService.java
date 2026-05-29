package com.sunl19ht.client.service;

import com.sunl19ht.client.pojo.entity.UseCarResultVO;
import com.sunl19ht.client.pojo.vo.UseCarVO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface UseCarService {
    HashMap<String, List<UseCarVO>> getAvailableCarList(LocalDateTime pickupTime, LocalDateTime returnTime, String carType);
}
