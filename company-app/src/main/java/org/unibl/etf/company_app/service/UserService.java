package org.unibl.etf.company_app.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.unibl.etf.company_app.model.UserBean;
import org.unibl.etf.company_app.model.dto.ChangePasswordRequestDTO;
import org.unibl.etf.company_app.model.dto.CompanyResponseDTO;
import org.unibl.etf.company_app.model.dto.LoginRequestDTO;
import org.unibl.etf.company_app.model.dto.LoginResponseDTO;
import org.unibl.etf.company_app.util.Config;

public class UserService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;
    private static final String AUTH_URL = "/auth/login";
    private static final String USERS_URL = "/users";

    public UserService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private String getBaseUrl() {
        String baseUrl = Config.getProperty("api.base.url");
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            baseUrl = "http://localhost:8080/api/v1";
        }
        return baseUrl;
    }

    public CompanyResponseDTO getCompanyById(Integer companyId, String jwtToken) {
        try {
            String url = getBaseUrl() + "/companies/" + companyId;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + jwtToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), CompanyResponseDTO.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserBean login(String username, String password) throws Exception {
        String url = getBaseUrl() + AUTH_URL;
        LoginRequestDTO requestDTO = new LoginRequestDTO(username, password);
        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            LoginResponseDTO responseDTO = objectMapper.readValue(response.body(), LoginResponseDTO.class);
            
            if ("ROLE_COMPANY".equals(responseDTO.getRole())) {
                // Check company active status
                CompanyResponseDTO companyInfo = getCompanyById(responseDTO.getUserId(), responseDTO.getToken());
                if (companyInfo != null && Boolean.FALSE.equals(companyInfo.getIsActive())) {
                    throw new RuntimeException("Your company account is currently inactive. Please contact administration to activate your account.");
                }

                UserBean userBean = new UserBean();
                userBean.setUserId(responseDTO.getUserId());
                userBean.setUsername(responseDTO.getUsername());
                userBean.setJwtToken(responseDTO.getToken());
                userBean.setRole(responseDTO.getRole());
                return userBean;
            } else {
                throw new RuntimeException("User is not registered as a company.");
            }
        } else if (response.statusCode() == 401) {
            throw new RuntimeException("Incorrect username or password.");
        } else {
            throw new RuntimeException("Login failed (HTTP " + response.statusCode() + ").");
        }
    }

    public boolean changePassword(Integer userId, ChangePasswordRequestDTO dto, String jwtToken) throws Exception {
        String url = getBaseUrl() + USERS_URL + "/" + userId + "/change-password";
        String jsonRequestBody = objectMapper.writeValueAsString(dto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return true;
        } else {
            String errorMessage = "Failed to change password (HTTP " + response.statusCode() + ")";
            if (response.body() != null && !response.body().trim().isEmpty()) {
                if (response.body().contains("message")) {
                    try {
                        var node = objectMapper.readTree(response.body());
                        if (node.has("message")) {
                            errorMessage = node.get("message").asText();
                        }
                    } catch (Exception ignored) {
                        errorMessage = response.body();
                    }
                } else {
                    errorMessage = response.body();
                }
            }
            throw new RuntimeException(errorMessage);
        }
    }
}
