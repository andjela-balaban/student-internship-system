package org.unibl.etf.company_app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.unibl.etf.company_app.model.dto.CompanyReviewLogDTO;
import org.unibl.etf.company_app.model.dto.InternshipLogResponseDTO;
import org.unibl.etf.company_app.util.Config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class InternshipLogService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    public InternshipLogService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private String getBaseUrl() {
        String baseUrl = Config.getProperty("api.base.url");
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            baseUrl = "http://localhost:8080/api/v1";
        }
        return baseUrl;
    }

    public List<InternshipLogResponseDTO> getAllLogsForStudent(Integer studentId, String jwtToken) {
        try {
            String url = getBaseUrl() + "/internship-logs/student/" + studentId;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + jwtToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<InternshipLogResponseDTO>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public boolean reviewLogByCompany(Integer logId, String companyComment, String jwtToken) throws Exception {
        String url = getBaseUrl() + "/internship-logs/" + logId + "/company-review";
        CompanyReviewLogDTO dto = new CompanyReviewLogDTO(companyComment);
        String jsonBody = objectMapper.writeValueAsString(dto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 200;
    }
}
