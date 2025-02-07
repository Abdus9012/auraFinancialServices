package com.example.aura.model;

public class Transaction {
    private String transactionId;
    private String userId;
    private double amount;
    private String timeStamp;
    private String location;
    private String deviceId;

    
    public Transaction(String transactionId, String userId, double amount, String timeStamp, String location,
            String deviceId) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.location = location;
        this.deviceId = deviceId;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    

}
