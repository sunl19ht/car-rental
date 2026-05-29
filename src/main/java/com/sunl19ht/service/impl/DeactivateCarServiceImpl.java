package com.sunl19ht.service.impl;

import com.sunl19ht.mapper.DeactivateCarMapper;
import com.sunl19ht.pojo.vo.DeactivateCarVO;
import com.sunl19ht.service.DeactivateCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeactivateCarServiceImpl implements DeactivateCarService {
    @Autowired
    private DeactivateCarMapper deactivateCarMapper;

    @Override
    public List<DeactivateCarVO> getDeactivateCar() {
        return deactivateCarMapper.getDeactivateCar();
    }

    @Override
    public void delCar(Integer id) {
        deactivateCarMapper.delCar(id);
    }
}
