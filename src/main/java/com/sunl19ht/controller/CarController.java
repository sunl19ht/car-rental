package com.sunl19ht.controller;

import com.sunl19ht.pojo.Result;
import com.sunl19ht.pojo.dto.CarDTO;
import com.sunl19ht.pojo.dto.DisableCarDTO;
import com.sunl19ht.pojo.vo.CarVO;
import com.sunl19ht.pojo.vo.DeactivateCarVO;
import com.sunl19ht.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/car")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping("/id")
    public Result<CarVO> getCarById(@RequestParam Integer id) {
        log.info("[获取车辆详情]id：{}", id);
        CarVO carVO = carService.getCarById(id);
        return Result.success(carVO);
    }

    @GetMapping("get")
    public Result<List<CarVO>> getCar() {
        log.info("[查询车辆列表]");
        List<CarVO> carVOList = carService.getCar();
        return Result.success(carVOList);
    }

    @PostMapping("update")
    public Result<String> updateCar(@ModelAttribute CarDTO carDTO) {
        log.info("[修改车辆]carDTO：{}", carDTO);
        carService.updateCar(carDTO);
        return Result.success("ok");
    }

    @PostMapping("add")
    public Result<String> addCar(@ModelAttribute CarDTO carDTO) {
        log.info("[增加车辆]carDTO：{}", carDTO);
        carService.addCar(carDTO);
        return Result.success("ok");
    }

    @DeleteMapping("del")
    public Result<String> delCar(@RequestParam Integer id) {
        log.info("[删除车辆]id：{}", id);
        carService.delCar(id);
        return Result.success("ok");
    }

    @PostMapping("setStatus")
    public Result<String> setStatus(@RequestParam Integer id, @RequestParam Integer status) {
        log.info("[更改车辆状态]id：{}，状态：{}", id, status);
        carService.setStatus(id, status);
        return Result.success("ok");
    }

    @PostMapping("disable")
    public Result<String> tempDisableCar(@RequestBody DisableCarDTO disableCarDTO) {
        log.info("[临时下线车辆]id：{}", disableCarDTO.getCarId());
        carService.tempDisableCar(disableCarDTO);
        return Result.success("ok");
    }

    @PostMapping("update/price")
    public Result<String> updatePrice(@RequestParam Integer id, @RequestParam BigDecimal weekendPrice, @RequestParam BigDecimal normalPrice) {
        log.info("[更新车辆价格]id：{}，周末价格：{}，平时价格：{}", id, weekendPrice, normalPrice);
        carService.updateCarPrice(id, weekendPrice, normalPrice);
        return Result.success("ok");
    }

    @GetMapping("deactivate")
    public Result<List<DeactivateCarVO>> deactivateCarTime(@RequestParam Integer id) {
        List<DeactivateCarVO> timeById = carService.getDeactivateCarTime(id);
        log.info("[获取车辆下架时间]id：{}", id);
        return Result.success(timeById);
    }

    @DeleteMapping("del/deactivate")
    public Result<String> delDeactivateCar(@RequestParam("id") Integer id,
                                           @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm") LocalDateTime startTime,
                                           @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm") LocalDateTime endTime) {
        log.info("[删除车辆下架时间]id：{}", id);
        carService.delDeactivateCarTime(id, startTime, endTime);
        return Result.success("ok");
    }
}
