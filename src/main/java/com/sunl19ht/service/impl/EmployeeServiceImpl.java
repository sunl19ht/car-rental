package com.sunl19ht.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunl19ht.client.mapper.UserMapper;
import com.sunl19ht.client.pojo.dto.UserLoginDTO;
import com.sunl19ht.client.pojo.entity.User;
import com.sunl19ht.constant.MessageConstant;
import com.sunl19ht.exception.LoginFailedException;
import com.sunl19ht.mapper.EmployeeMapper;
import com.sunl19ht.pojo.dto.EmployeeLoginDTO;
import com.sunl19ht.pojo.entity.Employee;
import com.sunl19ht.properties.WeChatProperties;
import com.sunl19ht.service.EmployeeService;
import com.sunl19ht.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String GRANT_TYPE = "authorization_code";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee wxLogin(EmployeeLoginDTO employeeLoginDTO) {
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", employeeLoginDTO.getCode());
        map.put("grant_type", GRANT_TYPE);

        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        Employee employee = employeeMapper.getByOpenid(openid);
        if (employee == null) {
            // 新用户
            employee = Employee.builder().openid(openid)
                    .createAt(Timestamp.valueOf(LocalDateTime.now()))
                    .phone("")
                    .build();
            employeeMapper.insert(employee);
        }
        return employee;
    }
}
