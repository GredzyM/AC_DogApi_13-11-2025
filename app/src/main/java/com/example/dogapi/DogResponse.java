package com.example.dogapi;

import java.util.Map;

public class DogResponse {
    private Map<String, Object> message;
    private String status;

    public Map<String, Object> getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
