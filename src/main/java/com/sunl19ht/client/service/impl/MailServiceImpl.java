package com.sunl19ht.client.service.impl;

import com.sunl19ht.client.mapper.FeedbackMapper;
import com.sunl19ht.client.pojo.dto.FeedbackDTO;
import com.sunl19ht.client.pojo.entity.Feedback;
import com.sunl19ht.client.service.MailService;
import com.sunl19ht.utils.TencentCOSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private TencentCOSUtils tencentCOSUtils;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${my-mail.to}")
    private String to;

    @Value("${cloud.tencent.cos.base-url}")
    private String baseUrl;

    @Override
    public void sendMail(Feedback feedback) throws IOException {
        String imageUrl = null;
        if (feedback.getImage() != null) {
            imageUrl = baseUrl + tencentCOSUtils.simpleUpload(feedback.getImage());
        }
        FeedbackDTO feedbackDTO = FeedbackDTO.builder()
                .type(feedback.getType())
                .image(imageUrl)
                .phone(feedback.getPhone())
                .content(feedback.getContent() + "附带图片：" + imageUrl)
                .build();
        feedbackMapper.insertIntoFeedback(feedbackDTO);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setText(feedback.getContent() + "附带图片：" + imageUrl);
        mailMessage.setSubject(feedback.getPhone() + " 发来的意见反馈！");
        mailSender.send(mailMessage);
        log.info("发送完成");
    }
}
