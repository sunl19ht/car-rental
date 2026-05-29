package com.sunl19ht.client.controller;

import com.sunl19ht.client.pojo.entity.UseCarResultVO;
import com.sunl19ht.client.pojo.vo.UseCarVO;
import com.sunl19ht.client.service.UseCarService;
import com.sunl19ht.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@Tag(name="用户端-首页")
@RequestMapping("api/user/useCar")
public class UseCarController {

    @Autowired
    private UseCarService useCarService;

    /**
     * 获取可用车辆列表
     * @param pickupTime    取车时间
     * @param returnTime    还车时间
     * @return 车辆列表
     */

    @Operation(summary = "获取可用车辆列表")
    @Parameter(name = "pickupTime",description = "取车时间",required = true,in= ParameterIn.QUERY)
    @Parameter(name = "returnTime",description = "还车时间",required = true,in= ParameterIn.QUERY)
    @Parameter(name = "carType",description = "车辆类型",required = false,in= ParameterIn.QUERY)
    @GetMapping("available")
    public Result<HashMap<String, List<UseCarVO>>> getCarList(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime pickupTime,
                                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime returnTime,
                                                              @RequestParam(value = "carType", required = false) String carType) {
        log.info("获取{}到{}时间可用车辆列表", pickupTime, returnTime);
        HashMap<String, List<UseCarVO>> useCarVOList = useCarService.getAvailableCarList(pickupTime, returnTime, carType);
        return Result.success(useCarVOList);
    }
}
