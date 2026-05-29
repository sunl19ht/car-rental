package com.sunl19ht.client.service;

import com.sunl19ht.client.pojo.dto.UserLoginDTO;
import com.sunl19ht.client.pojo.entity.User;

public interface UserService {
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
