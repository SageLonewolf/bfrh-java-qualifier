package com.example.bfrh.service;

import com.example.bfrh.model.GenerateWebhookRequest;
import com.example.bfrh.model.GenerateWebhookResponse;
import com.example.bfrh.model.SubmitPayload;
import com.example.bfrh.persistence.Submission;
import com.example.bfrh.persistence.SubmissionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.reactive.function.client.WebClient;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Service
public class QualifierService {

    private final WebClient webClient;
    private final SubmissionRepository repo;

    @Value("${bfrh.generate.url}")
    private String generateUrl;

    @Value("${bfrh.fallback.submit.url}")
    private String fallbackSubmitUrl;

    public QualifierService(WebClient webClient, SubmissionRepository repo) {
        this.webClient = webClient;
        this.repo = repo;
    }

    public void executeOnceOnStartup(String name, String regNo, String email) {
        try {
            GenerateWebhookResponse resp = webClient.post()
                    .uri(generateUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new GenerateWebhookRequest(name, regNo, email))
                    .retrieve()
                    .bodyToMono(GenerateWebhookResponse.class)
                    .block();

            if (resp == null) throw new IllegalStateException("Invalid response from generateWebhook");

            String webhookUrl = (resp.getWebhook() != null && !resp.getWebhook().isBlank()) ? resp.getWebhook() : fallbackSubmitUrl;
            String accessToken = resp.getAccessToken();
            String questionType = "ODD" ;
            String finalQuery = loadFinalSql(questionType);

            Submission saved = repo.save(new Submission(regNo, questionType, finalQuery, webhookUrl, accessToken, Instant.now()));
            SubmitPayload payload = new SubmitPayload(finalQuery);

            String submitResponse = webClient.post()
                    .uri(webhookUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", accessToken)
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorResume(err -> webClient.post()
                            .uri(fallbackSubmitUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", accessToken)
                            .bodyValue(payload)
                            .retrieve()
                            .bodyToMono(String.class))
                    .block();

            System.out.println("Submitted successfully. Server says: " + submitResponse);
            System.out.println("Local submission id: " + saved.getId());

        } catch (Exception ex) {
            System.err.println("Startup flow failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private String loadFinalSql(String questionType) throws Exception {
        String path = "final_query_odd.sql" ;
        ClassPathResource res = new ClassPathResource(path);
        String sql = StreamUtils.copyToString(res.getInputStream(), StandardCharsets.UTF_8).trim();
        if (sql.isBlank()) throw new IllegalStateException("Missing SQL in " + path);
        return sql.replaceAll("\s+", " ").trim();
    }
}
