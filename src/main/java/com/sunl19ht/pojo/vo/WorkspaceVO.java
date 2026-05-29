package com.sunl19ht.pojo.vo;

import com.sunl19ht.pojo.Result;
import lombok.Data;

@Data
public class WorkspaceVO {
    private Integer waitReturn; // 待归还
    private Integer waitPickUp; // 待取车
    private Integer success; // 已完成
}
