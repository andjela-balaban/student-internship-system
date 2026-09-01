package org.unibl.etf.faculty_app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.unibl.etf.faculty_app.model.dto.CompanyRequestDTO;
import org.unibl.etf.faculty_app.model.dto.CompanyResponseDTO;
import org.unibl.etf.faculty_app.model.dto.CompanyStatusUpdateDTO;
import org.unibl.etf.faculty_app.util.Config;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CompanyService {
	
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String COMPANIES_URL = "/companies";

    private String getBaseUrl() {
        return Config.getProperty("api.base.url") + COMPANIES_URL;
    }

    // Dobijanje svih kompanija
    public List<CompanyResponseDTO> getAll(String jwtToken) throws Exception {
    	String url = getBaseUrl();
    	
//    	System.out.println(">>> SALJEM ZAHTJEV NA URL: " + url);
//        System.out.println(">>> TOKEN: " + jwtToken);
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
//        System.out.println(">>> STATUS KOD: " + response.statusCode());
//        System.out.println(">>> ODGOVOR SA BACKENDA: " + response.body());

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), new TypeReference<List<CompanyResponseDTO>>() {});
        } else {
            throw new RuntimeException("Error retrieving companies: HTTP " + response.statusCode());
        }
    }

    // Kreiranje nove kompanije
    public CompanyResponseDTO createCompany(CompanyRequestDTO dto, String jwtToken) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(dto);
        String url = getBaseUrl();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return objectMapper.readValue(response.body(), CompanyResponseDTO.class);
        } else {
            throw new RuntimeException("Error creating company: HTTP " + response.statusCode());
        }
    }

    // Azuriranje statusa kompanije
    public CompanyResponseDTO updateStatus(Integer id, CompanyStatusUpdateDTO statusDto, String jwtToken) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(statusDto);
        String url = getBaseUrl();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/" + id + "/status"))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), CompanyResponseDTO.class);
        } else {
            throw new RuntimeException("Error updating status: HTTP " + response.statusCode());
        }
    }
}
