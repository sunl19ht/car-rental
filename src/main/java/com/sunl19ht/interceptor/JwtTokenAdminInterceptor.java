package com.sunl19ht.interceptor;

import com.alibaba.fastjson.JSON;
import com.sunl19ht.context.BaseContext;
import com.sunl19ht.properties.JwtProperties;
import com.sunl19ht.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader(jwtProperties.getAdminTokenName());

        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Map<String, Object> error = new HashMap<>();
            error.put("code", 401);
            error.put("message", "请登录");
            response.getOutputStream().write(JSON.toJSONString(error).getBytes(StandardCharsets.UTF_8));
            return false;
        }

        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = Long.valueOf(claims.get("empId").toString());
            log.info("当前员工id：{}", empId);
            BaseContext.setCurrentId(empId);
            return true;
        } catch (Exception ex) {
            log.error("jwt校验失败: {}", ex.getMessage());
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Map<String, Object> error = new HashMap<>();
            error.put("code", 401);
            error.put("message", "请登录");
            response.getOutputStream().write(JSON.toJSONString(error).getBytes(StandardCharsets.UTF_8));
            return false;
        }
    }
}
