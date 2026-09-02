package org.unibl.etf.company_app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.unibl.etf.company_app.model.dto.InternshipRequestDTO;
import org.unibl.etf.company_app.model.dto.InternshipResponseDTO;
import org.unibl.etf.company_app.util.Config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class InternshipService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;
    private static final String INTERNSHIPS_URL = "/internships";

    public InternshipService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    private String getBaseUrl() {
        String baseUrl = Config.getProperty("api.base.url");
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            baseUrl = "http://localhost:8080/api/v1";
        }
        return baseUrl + INTERNSHIPS_URL;
    }

    public List<InternshipResponseDTO> getAll(String jwtToken) {
        try {
            String url = getBaseUrl();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + jwtToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<InternshipResponseDTO>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public InternshipResponseDTO getById(Integer id, String jwtToken) {
        try {
            String url = getBaseUrl() + "/" + id;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + jwtToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), InternshipResponseDTO.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public InternshipResponseDTO createInternship(InternshipRequestDTO dto, String jwtToken) throws Exception {
        String url = getBaseUrl();
        String jsonBody = objectMapper.writeValueAsString(dto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 || response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), InternshipResponseDTO.class);
        } else {
            throw new RuntimeException("Error creating internship: Status " + response.statusCode() + " - " + response.body());
        }
    }

    public InternshipResponseDTO updateInternship(Integer id, InternshipRequestDTO dto, String jwtToken) throws Exception {
        String url = getBaseUrl() + "/" + id;
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
            return objectMapper.readValue(response.body(), InternshipResponseDTO.class);
        } else {
            throw new RuntimeException("Error updating internship: Status " + response.statusCode() + " - " + response.body());
        }
    }

    public boolean deleteInternship(Integer id, String jwtToken) throws Exception {
        String url = getBaseUrl() + "/" + id;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwtToken)
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 204 || response.statusCode() == 200;
    }
}
