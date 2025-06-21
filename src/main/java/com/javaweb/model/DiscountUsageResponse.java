package com.javaweb.model;

import java.util.List;

public class DiscountUsageResponse {
    private List<String> usedCodes;
    private String message;

    public DiscountUsageResponse(List<String> usedCodes, String message) {
        this.usedCodes = usedCodes;
        this.message = message;
    }

    public List<String> getUsedCodes() {
        return usedCodes;
    }

    public void setUsedCodes(List<String> usedCodes) {
        this.usedCodes = usedCodes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}