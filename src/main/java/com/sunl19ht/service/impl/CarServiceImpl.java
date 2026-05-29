package com.sunl19ht.service.impl;

import com.sunl19ht.exception.CarNotFoundException;
import com.sunl19ht.mapper.CarMapper;
import com.sunl19ht.mapper.DeactivateCarMapper;
import com.sunl19ht.pojo.dto.CarDTO;
import com.sunl19ht.pojo.dto.DisableCarDTO;
import com.sunl19ht.pojo.entity.LogOffTime;
import com.sunl19ht.pojo.vo.CarVO;
import com.sunl19ht.pojo.vo.DeactivateCarVO;
import com.sunl19ht.service.CarService;
import com.sunl19ht.utils.AliOSSUtils;
import com.sunl19ht.utils.TencentCOSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Autowired
    private TencentCOSUtils tencentCOSUtils;

    @Autowired
    private DeactivateCarMapper deactivateCarMapper;

    @Value("${cloud.tencent.cos.base-url}")
    private String baseUrl;

    /**
     * 查询车辆列表
     * @return 车辆列表
     */
    @Override
    public List<CarVO> getCar() {
        // 获取全部车辆
        List<CarVO> car = carMapper.getCar();

        // 遍历车辆列表
        for (CarVO carVO : car) {
            carVO.setState("");
            // 查询车辆对应的下线时间
            List<DeactivateCarVO> timeById = deactivateCarMapper.getTimeById(carVO.getId());
            if (timeById.isEmpty()) {
                carVO.setLogOffTime(new ArrayList<>());
                continue;
            }
            // 下线时间列表
            ArrayList<LogOffTime> logOffTimeArrayList = new ArrayList<>();
            for (DeactivateCarVO deactivateCarVO : timeById) {
                LogOffTime logOffTime = new LogOffTime();
                logOffTime.setId(deactivateCarVO.getId());
                logOffTime.setDesc(deactivateCarVO.getDesc());
                logOffTime.setStartTime(deactivateCarVO.getStartTime());
                logOffTime.setEndTime(deactivateCarVO.getEndTime());
                logOffTime.setUser(deactivateCarVO.getUser());
                logOffTimeArrayList.add(logOffTime);
            }
            carVO.setLogOffTime(logOffTimeArrayList);
        }
        return car;
    }

    /**
     * 设置车辆状态 上架 下架
     * @param id 车辆id
     * @param status 车辆状态
     */
    @Override
    @Transactional
    public void setStatus(Integer id, Integer status) {
        log.info("[设置车辆状态]id：{}，状态：{}", id, status);
        CarVO carVO = carMapper.getCarById(id);
        if (carVO == null) {
            log.error("[设置车辆状态]车辆不存在id：{}", id);
            throw new CarNotFoundException("车辆不存在");
        }
        carMapper.setStatus(id, status);
    }

    /**
     * 新增车辆
     * @param carDTO 车辆对象
     */
    @Override
    @Transactional
    public void addCar(CarDTO carDTO) {
        try {
            String fileName = tencentCOSUtils.simpleUpload(carDTO.getImage());
            String imageUrl = baseUrl + fileName;
            log.info("图片上传成功：{}", imageUrl);
            carDTO.setImageUrl(imageUrl);
        } catch (Exception e) {
            log.error("图片上传失败: {}", e.getMessage(), e);
            throw new RuntimeException("图片上传失败", e);
        }
        carDTO.setStatus(0);
        carDTO.setIsDelete(0);
        carMapper.addCar(carDTO);
    }

    /**
     * 删除车辆
     * @param id 车辆id
     */
    @Override
    @Transactional
    public void delCar(Integer id) {
        CarVO carVO = carMapper.getCarById(id);
        if (carVO == null) {
            log.error("[删除车辆]车辆不存在id：{}", id);
            throw new CarNotFoundException("车辆不存在");
        }
        carMapper.delCar(id);
    }

    @Override
    @Transactional
    public void updateCar(CarDTO carDTO) {
        if (carDTO.getImage() != null) {
            try {
                String fileName = tencentCOSUtils.simpleUpload(carDTO.getImage());
                String imageUrl = baseUrl + fileName;
                log.info("图片上传成功：{}", imageUrl);
                carDTO.setImageUrl(imageUrl);
            } catch (Exception e) {
                log.error("图片上传失败: {}", e.getMessage(), e);
                throw new RuntimeException("图片上传失败", e);
            }
        }
        carMapper.updateCar(carDTO);
    }

    @Override
    @Transactional
    public void updateCarPrice(Integer id, BigDecimal weekendPrice, BigDecimal normalPrice) {
        CarVO carVO = carMapper.getCarById(id);
        if (carVO == null) {
            log.error("[修改车辆价格]车辆不存在id：{}", id);
            throw new CarNotFoundException("车辆不存在");
        }
        carMapper.updateCarPrice(id, weekendPrice, normalPrice);
    }

    @Override
    @Transactional
    public void tempDisableCar(DisableCarDTO disableCarDTO) {
        disableCarDTO.setUser("临时下线");
        carMapper.addTempDisableCar(disableCarDTO);
    }

    @Override
    public CarVO getCarById(Integer id) {
        CarVO carById = carMapper.getCarById(id);
        if (carById == null) {
            log.error("[获取车辆详情]车辆不存在id：{}", id);
            throw new CarNotFoundException("车辆不存在");
        }
        return carById;
    }

    @Override
    public List<DeactivateCarVO> getDeactivateCarTime(Integer id) {
        return deactivateCarMapper.getTimeById(id);
    }

    @Override
    public void delDeactivateCarTime(Integer id, LocalDateTime startTime, LocalDateTime endTime) {
        deactivateCarMapper.delTime(id, startTime, endTime);
    }
}
