package com.sunl19ht.client.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Detail implements Serializable {
    private Integer carId;
    private LocalDateTime upCarTime;
    private LocalDateTime returnCarTime;
    private String guaranteeService;
    private String carWay;
    private List<Address> address;
}