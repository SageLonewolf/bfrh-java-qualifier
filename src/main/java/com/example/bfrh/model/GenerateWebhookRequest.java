package com.example.bfrh.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerateWebhookRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("regNo")
    private String regNo;
    @JsonProperty("email")
    private String email;

    public GenerateWebhookRequest() {}
    public GenerateWebhookRequest(String name, String regNo, String email) {
        this.name = name;
        this.regNo = regNo;
        this.email = email;
    }
    public String getName() { return name; }
    public String getRegNo() { return regNo; }
    public String getEmail() { return email; }
    public void setName(String name) { this.name = name; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    public void setEmail(String email) { this.email = email; }
}
