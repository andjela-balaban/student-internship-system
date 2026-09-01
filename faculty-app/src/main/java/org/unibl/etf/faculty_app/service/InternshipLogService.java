package org.unibl.etf.faculty_app.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.unibl.etf.faculty_app.model.dto.FacultyReviewLogDTO;
import org.unibl.etf.faculty_app.model.dto.InternshipLogResponseDTO;
import org.unibl.etf.faculty_app.util.Config;

public class InternshipLogService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;
    private static final String LOGS_URL = "/internship-logs";

    public InternshipLogService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    private String getBaseUrl() {
        return Config.getProperty("api.base.url") + LOGS_URL;
    }

    public List<InternshipLogResponseDTO> getLogsForStudent(Integer studentId, String jwtToken) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getBaseUrl() + "/student/" + studentId))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), new TypeReference<List<InternshipLogResponseDTO>>() {});
        } else {
            throw new RuntimeException("Error fetching student logs: HTTP " + response.statusCode());
        }
    }

    public void reviewLogByFaculty(Integer logId, FacultyReviewLogDTO dto, String jwtToken) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(dto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getBaseUrl() + "/" + logId + "/faculty-review"))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error reviewing log: HTTP " + response.statusCode());
        }
    }
}