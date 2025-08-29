package com.example.bfrh.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerateWebhookResponse {
    @JsonProperty("webhook")
    private String webhook;
    @JsonProperty("accessToken")
    private String accessToken;

    public String getWebhook() { return webhook; }
    public String getAccessToken() { return accessToken; }
    public void setWebhook(String webhook) { this.webhook = webhook; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}
