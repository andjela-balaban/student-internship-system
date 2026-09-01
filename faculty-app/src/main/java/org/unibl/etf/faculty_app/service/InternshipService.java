package org.unibl.etf.faculty_app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.unibl.etf.faculty_app.model.dto.InternshipResponseDTO;
import org.unibl.etf.faculty_app.util.Config;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
        return Config.getProperty("api.base.url") + INTERNSHIPS_URL;
    }

    // Dobijanje svih praksi
    public List<InternshipResponseDTO> getAll(String jwtToken) throws Exception {
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
        } else {
            throw new RuntimeException("Error retrieving internships: HTTP " + response.statusCode());
        }
    }
}