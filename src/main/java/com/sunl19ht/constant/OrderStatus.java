package com.sunl19ht.constant;

public enum OrderStatus {
    PENDING_PAYMENT("待支付"),
    PAID("已支付"),
    PENDING_PICKUP("待取车"),
    PENDING_RETURN("待还车"),
    COMPLETED("已完成"),
    CANCELLED("已取消");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
