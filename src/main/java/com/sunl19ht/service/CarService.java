package com.sunl19ht.service;

import com.sunl19ht.pojo.dto.CarDTO;
import com.sunl19ht.pojo.dto.DisableCarDTO;
import com.sunl19ht.pojo.vo.CarVO;
import com.sunl19ht.pojo.vo.DeactivateCarVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface CarService {
    List<CarVO> getCar();

    void setStatus(Integer id, Integer status);

    void addCar(CarDTO carDTO);

    void delCar(Integer id);

    void updateCar(CarDTO carDTO);

    void updateCarPrice(Integer id, BigDecimal weekendPrice, BigDecimal normalPrice);

    void tempDisableCar(DisableCarDTO disableCarDTO);

    CarVO getCarById(Integer id);

    List<DeactivateCarVO> getDeactivateCarTime(Integer id);

    void delDeactivateCarTime(Integer id, LocalDateTime startTime, LocalDateTime endTime);
}
