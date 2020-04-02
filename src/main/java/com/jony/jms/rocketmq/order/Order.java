package com.jony.jms.rocketmq.order;

public class Order {
    private int id;
    private String status;
//    订单顺序
    private int sendOrder;
//
    private String content;

    @Override
    public String toString() {
        return String.format("id=%d,status=%s,sendOrder=%d,content=%s",id,status,sendOrder,content);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSendOrder() {
        return sendOrder;
    }

    public void setSendOrder(int sendOrder) {
        this.sendOrder = sendOrder;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
