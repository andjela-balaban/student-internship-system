package org.unibl.etf.faculty_app.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.unibl.etf.faculty_app.model.UserBean;
import org.unibl.etf.faculty_app.model.dto.LoginRequestDTO;
import org.unibl.etf.faculty_app.model.dto.LoginResponseDTO;
import org.unibl.etf.faculty_app.util.Config;

public class UserService {
	
	private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String AUTH_URL = "/auth/login";

    public UserBean login(String username, String password) {
        try {
            String url = Config.getProperty("api.base.url") + AUTH_URL;
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
                
                UserBean userBean = new UserBean();
                userBean.setUserId(responseDTO.getUserId());
                userBean.setUsername(responseDTO.getUsername());
                userBean.setJwtToken(responseDTO.getToken());
                userBean.setRole(responseDTO.getRole());
                
                return userBean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
