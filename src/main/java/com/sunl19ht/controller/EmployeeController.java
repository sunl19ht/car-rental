package com.sunl19ht.controller;

import com.sunl19ht.pojo.Result;
import com.sunl19ht.pojo.dto.EmployeeLoginDTO;
import com.sunl19ht.pojo.entity.Employee;
import com.sunl19ht.pojo.vo.EmployeeLoginVO;
import com.sunl19ht.properties.JwtProperties;
import com.sunl19ht.service.EmployeeService;
import com.sunl19ht.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("商家端用户登录:{}", employeeLoginDTO.getCode());
        // 微信登录
        Employee employee =  employeeService.wxLogin(employeeLoginDTO);
//        为微信用户生成Jwt令牌
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("employeeId", employee.getId());
        String token = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .openid(employee.getOpenid())
                .token(token)
                .build();
        log.info("商家端用户登录成功:{}", employeeLoginVO);
        return Result.success(employeeLoginVO);
    }
}
