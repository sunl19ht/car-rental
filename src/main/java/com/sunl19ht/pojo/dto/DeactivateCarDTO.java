package com.sunl19ht.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeactivateCarDTO implements Serializable {
    private Integer carId;
    private String desc;
    private Timestamp startTime;
    private Timestamp endTime;
    private String user;
    private Integer status;
    private String orderNumber;
}
