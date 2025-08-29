package com.example.bfrh.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmitPayload {
    @JsonProperty("finalQuery")
    private String finalQuery;

    public SubmitPayload() {}
    public SubmitPayload(String finalQuery) {
        this.finalQuery = finalQuery;
    }
    public String getFinalQuery() { return finalQuery; }
    public void setFinalQuery(String finalQuery) { this.finalQuery = finalQuery; }
}
