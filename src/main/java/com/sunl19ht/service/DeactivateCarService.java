package com.sunl19ht.service;

import com.sunl19ht.pojo.vo.DeactivateCarVO;

import java.util.List;

public interface DeactivateCarService {
    List<DeactivateCarVO> getDeactivateCar();

    void delCar(Integer id);
}
