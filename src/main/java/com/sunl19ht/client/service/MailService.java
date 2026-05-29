package com.sunl19ht.client.service;

import com.sunl19ht.client.pojo.entity.Feedback;

import java.io.IOException;

public interface MailService {
    void sendMail(Feedback feedback) throws IOException;
}
