package com.sunl19ht.utils;

import com.sunl19ht.pojo.vo.DeactivateCarVO;
import com.sunl19ht.service.CarService;
import com.sunl19ht.service.DeactivateCarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class VehicleScheduler {

    @Autowired
    private CarService carService;

    @Autowired
    private DeactivateCarService deactivateCarService;

    @Scheduled(fixedRate = 5000)
    @Transactional // 添加事务
    public void updateVehicleStatus() {
        // 先处理上架
        // 获取上下架车辆列表
        List<DeactivateCarVO> deactivateCarList = deactivateCarService.getDeactivateCar();
        for (DeactivateCarVO deactivateCarVO : deactivateCarList) {
            // 如果当前时间大于结束时间 重新上架车辆
            if (deactivateCarVO.getEndTime().getTime() < System.currentTimeMillis() && deactivateCarVO.getStatus() == 0) {
                log.info("[定时任务]车辆自动上架：{}", deactivateCarList);
                // 上架车辆
                carService.setStatus(deactivateCarVO.getCarId(), 0);
//                // 上架车辆之后将数据删除(逻辑删除 status 设置为1)
                deactivateCarService.delCar(deactivateCarVO.getId());
                // 打印日志
                log.info("[定时任务]车辆上架：{}", deactivateCarVO.getCarId());
            }
        }
    }

    // todo定时下架

}