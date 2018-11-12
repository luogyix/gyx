package cn.gyx.mybatis.model;

import java.util.Date;

public class Orders {
    private Integer orderId;

    private Integer personId;

    private Float orderSum;

    private Date orderTime;

    private String orderAddr;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Float getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(Float orderSum) {
        this.orderSum = orderSum;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderAddr() {
        return orderAddr;
    }

    public void setOrderAddr(String orderAddr) {
        this.orderAddr = orderAddr;
    }
}