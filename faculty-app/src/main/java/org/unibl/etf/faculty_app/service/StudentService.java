package org.unibl.etf.faculty_app.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import org.unibl.etf.faculty_app.model.dto.StudentRequestDTO;
import org.unibl.etf.faculty_app.model.dto.StudentResponseDTO;
import org.unibl.etf.faculty_app.util.Config;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class StudentService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;
    private static final String STUDENTS_URL = "/students";

    public StudentService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    private String getBaseUrl() {
        return Config.getProperty("api.base.url") + STUDENTS_URL;
    }

    public List<StudentResponseDTO> getAll(String jwtToken) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getBaseUrl()))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), new TypeReference<List<StudentResponseDTO>>() {});
        } else {
            throw new RuntimeException("Error fetching students: HTTP " + response.statusCode());
        }
    }

    public StudentResponseDTO getById(Integer id, String jwtToken) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getBaseUrl() + "/" + id))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), StudentResponseDTO.class);
        } else {
            throw new RuntimeException("Error fetching student: HTTP " + response.statusCode());
        }
    }

    public StudentResponseDTO createStudent(StudentRequestDTO dto, String jwtToken) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(dto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getBaseUrl()))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 201) {
            return objectMapper.readValue(response.body(), StudentResponseDTO.class);
        } else {
            throw new RuntimeException("Error creating student: HTTP " + response.statusCode());
        }
    }

    public StudentResponseDTO updateStudent(Integer id, StudentRequestDTO dto, String jwtToken) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(dto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getBaseUrl() + "/" + id))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), StudentResponseDTO.class);
        } else {
            throw new RuntimeException("Error updating student: HTTP " + response.statusCode());
        }
    }

    public void deleteStudent(Integer id, String jwtToken) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getBaseUrl() + "/" + id))
                .header("Authorization", "Bearer " + jwtToken)
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Error deleting student: HTTP " + response.statusCode());
        }
    }
    
    public void importCsv(InputStream inputStream, String fileName, Integer facultyId, String jwtToken) throws Exception {
        String boundary = "---Boundary" + System.currentTimeMillis();
        byte[] fileBytes = inputStream.readAllBytes();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(("--" + boundary + "\r\n").getBytes());
        baos.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n").getBytes());
        baos.write("Content-Type: text/csv\r\n\r\n".getBytes());
        baos.write(fileBytes);
        baos.write("\r\n".getBytes());
        baos.write(("--" + boundary + "--\r\n").getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getBaseUrl() + "/import?facultyId=" + facultyId))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(baos.toByteArray()))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201 && response.statusCode() != 200) {
            throw new RuntimeException("Error importing CSV: HTTP " + response.statusCode());
        }
    }
}