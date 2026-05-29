package com.sunl19ht.client.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import com.sunl19ht.client.mapper.UseCarMapper;
import com.sunl19ht.client.pojo.entity.CarTypePrice;
import com.sunl19ht.client.pojo.entity.UseCarResultVO;
import com.sunl19ht.client.pojo.vo.UseCarVO;
import com.sunl19ht.client.service.UseCarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
public class UseCarServiceImpl implements UseCarService {
    @Autowired
    private UseCarMapper useCarMapper;

    @Override
    public HashMap<String, List<UseCarVO>> getAvailableCarList(LocalDateTime pickupTime, LocalDateTime returnTime, String carType) {
        if (pickupTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("取车时间不能早于当前时间");
        }
        List<UseCarVO> availableCarIds = useCarMapper.findAvailableCarIds(pickupTime, returnTime);
        List<UseCarVO> useCarVOS = filteredPrice(availableCarIds, pickupTime);
        return filteredType(useCarVOS);
    }



    public HashMap<String, List<UseCarVO>> filteredType(List<UseCarVO> filteredCars) {
        Set<String> typeSet = new HashSet<>();
        HashMap<String, List<UseCarVO>> carType = new HashMap<>();
        carType.put("全部类型", filteredCars);
        for (UseCarVO car : filteredCars) {
            typeSet.add(car.getCarType());
        }

        for (String type : typeSet) {
            carType.put(type, new ArrayList<>());
        }

        for (UseCarVO car : filteredCars) {
            if (carType.get(car.getCarType()) != null) {
                carType.get(car.getCarType()).add(car);
            }
        }
        return carType;
    }

    public List<UseCarVO> filteredPrice(List<UseCarVO> carList, LocalDateTime pickupTime) {
        // 过滤价格
        for (UseCarVO car : carList) {
            // 判断租车时间是否周末
            Date date = Date.from(pickupTime.atZone(ZoneId.systemDefault()).toInstant());
            Week week = DateUtil.dayOfWeekEnum(date); // 获取星期枚举
            if (week == Week.SATURDAY || week == Week.SUNDAY) {
                // 周末
                car.setPrice(car.getWeekendPrice());
            } else {
                // 工作日
                car.setPrice(car.getNormalPrice());
            }

            // 脱敏车牌号
            String licensePlate = car.getLicensePlate();
            if (licensePlate != null && licensePlate.length() >= 2) {
                String maskedLicensePlate = licensePlate.charAt(0) + "牌";
                car.setLicensePlate(maskedLicensePlate);
            }
        }
        return carList;
    }
}
