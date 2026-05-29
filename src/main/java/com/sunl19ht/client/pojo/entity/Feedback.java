package com.sunl19ht.client.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback implements Serializable {
    private String type;

    private String content;

    private String phone;

    private MultipartFile image;
}
