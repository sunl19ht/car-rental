package com.sunl19ht.pojo.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LogOffTime {
    private Integer id;
    private String desc;
    private Timestamp startTime;
    private Timestamp endTime;
    private String user;
}
