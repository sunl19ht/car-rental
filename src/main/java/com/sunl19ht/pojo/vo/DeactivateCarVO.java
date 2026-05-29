package com.sunl19ht.pojo.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DeactivateCarVO {
    private Integer id;
    private Integer carId;
    private String desc;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer status;
    private String user;
}
