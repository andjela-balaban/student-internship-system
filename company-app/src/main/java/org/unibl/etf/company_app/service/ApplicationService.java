package org.unibl.etf.company_app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.unibl.etf.company_app.model.dto.ApplicationResponseDTO;
import org.unibl.etf.company_app.model.dto.ApplicationStatusUpdateDTO;
import org.unibl.etf.company_app.util.Config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class ApplicationService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    public ApplicationService() {
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

    public List<ApplicationResponseDTO> getAllByInternshipId(Integer internshipId, String jwtToken) {
        try {
            String url = getBaseUrl() + "/internships/" + internshipId + "/applications";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + jwtToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<ApplicationResponseDTO>>() {});
            } else {
                System.err.println("getAllByInternshipId HTTP " + response.statusCode() + ": " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public ApplicationResponseDTO getByInternshipIdAndApplicationId(Integer internshipId, Integer applicationId, String jwtToken) {
        try {
            String url = getBaseUrl() + "/internships/" + internshipId + "/applications/" + applicationId;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + jwtToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), ApplicationResponseDTO.class);
            } else {
                System.err.println("getByInternshipIdAndApplicationId HTTP " + response.statusCode() + ": " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ApplicationResponseDTO updateApplicationStatus(Integer internshipId, Integer applicationId, String statusStr, String jwtToken) throws Exception {
        String url = getBaseUrl() + "/internships/" + internshipId + "/applications/" + applicationId;
        ApplicationStatusUpdateDTO dto = new ApplicationStatusUpdateDTO(statusStr);
        String jsonBody = objectMapper.writeValueAsString(dto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), ApplicationResponseDTO.class);
        } else {
            throw new RuntimeException("Error updating application status: Status " + response.statusCode() + " - " + response.body());
        }
    }
}
