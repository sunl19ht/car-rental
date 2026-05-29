package com.sunl19ht.client.controller;

import com.sunl19ht.client.pojo.dto.UserLoginDTO;
import com.sunl19ht.client.pojo.entity.User;
import com.sunl19ht.client.pojo.vo.UserLoginVO;
import com.sunl19ht.client.service.UserService;
import com.sunl19ht.pojo.Result;
import com.sunl19ht.properties.JwtProperties;
import com.sunl19ht.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RequestMapping("api/user")
@Tag(name = "用户端-用户接口")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Operation(summary = "用户登录")
    @Parameter(name = "code",description = "wx.login()获取的code",required = true,in=ParameterIn.QUERY)
    @PostMapping("login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信用户登录:{}", userLoginDTO.getCode());
        // 微信登录
        User user =  userService.wxLogin(userLoginDTO);
//        为微信用户生成Jwt令牌
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        log.info("微信用户登录成功:{}", userLoginVO);
        return Result.success(userLoginVO);
    }
}
