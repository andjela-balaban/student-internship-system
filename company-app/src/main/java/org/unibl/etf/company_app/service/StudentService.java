package org.unibl.etf.company_app.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.unibl.etf.company_app.model.dto.CvResponseDTO;
import org.unibl.etf.company_app.model.dto.StudentResponseDTO;
import org.unibl.etf.company_app.util.Config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StudentService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    public StudentService() {
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

    public StudentResponseDTO getStudentById(Integer studentId, String jwtToken) {
        try {
            String url = getBaseUrl() + "/students/" + studentId;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + jwtToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), StudentResponseDTO.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CvResponseDTO getCvByStudentId(Integer studentId, String jwtToken) {
        try {
            String url = getBaseUrl() + "/cvs/student/" + studentId;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + jwtToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), CvResponseDTO.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getCvPdfBytes(Integer studentId, String jwtToken) throws Exception {
        String url = getBaseUrl() + "/students/" + studentId + "/cv/pdf";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Accept", "application/pdf")
                .GET()
                .build();

        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Error fetching CV PDF: Status " + response.statusCode());
        }
    }
}
