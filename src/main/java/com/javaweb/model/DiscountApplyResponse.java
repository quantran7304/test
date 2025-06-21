package com.javaweb.model;

public class DiscountApplyResponse {
    private Double amount;
    private String message;

    public DiscountApplyResponse(Double amount, String message) {
        this.amount = amount;
        this.message = message;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}