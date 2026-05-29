package com.sunl19ht.client.controller;

import com.sunl19ht.client.pojo.entity.Feedback;
import com.sunl19ht.client.service.MailService;
import com.sunl19ht.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("api/user/feedback")
@Tag(name = "用户端-用户反馈")
public class FeedbackController {

    @Autowired
    private MailService mailService;

    @PostMapping
    @Operation(summary = "发送消息到商家邮箱")
    @Parameter(name = "type",description = "反馈类型",required = true,in= ParameterIn.QUERY)
    @Parameter(name = "content",description = "正文",required = true,in= ParameterIn.QUERY)
    @Parameter(name = "phone",description = "电话",required = true,in= ParameterIn.QUERY)
    @Parameter(name = "image",description = "图片",required = false,in= ParameterIn.QUERY)
    public Result feedback(@RequestBody Feedback feedback) throws IOException {
        log.info("用户反馈：{}", feedback);
        mailService.sendMail(feedback);
        return Result.success();
    }
}
