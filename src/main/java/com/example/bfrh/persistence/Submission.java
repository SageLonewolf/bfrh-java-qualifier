package com.example.bfrh.persistence;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "submissions")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String regNo;
    private String questionType;
    @Column(length = 10000)
    private String finalQuery;
    private String webhookUrl;
    private String accessToken;
    private Instant submittedAt;

    public Submission() {}
    public Submission(String regNo, String questionType, String finalQuery,
                      String webhookUrl, String accessToken, Instant submittedAt) {
        this.regNo = regNo;
        this.questionType = questionType;
        this.finalQuery = finalQuery;
        this.webhookUrl = webhookUrl;
        this.accessToken = accessToken;
        this.submittedAt = submittedAt;
    }
    public Long getId() { return id; }
    public String getRegNo() { return regNo; }
    public String getQuestionType() { return questionType; }
    public String getFinalQuery() { return finalQuery; }
    public String getWebhookUrl() { return webhookUrl; }
    public String getAccessToken() { return accessToken; }
    public Instant getSubmittedAt() { return submittedAt; }
    public void setId(Long id) { this.id = id; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    public void setQuestionType(String questionType) { this.questionType = questionType; }
    public void setFinalQuery(String finalQuery) { this.finalQuery = finalQuery; }
    public void setWebhookUrl(String webhookUrl) { this.webhookUrl = webhookUrl; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public void setSubmittedAt(Instant submittedAt) { this.submittedAt = submittedAt; }
}
