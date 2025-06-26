package com.doanjava.gradehub.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.doanjava.gradehub.dto.ApiResponse;
import com.doanjava.gradehub.dto.LoginRequest;
import com.doanjava.gradehub.dto.LoginResponse;
import com.doanjava.gradehub.dto.RegisterRequest;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class HttpClientService {

    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }

    // Lưu token sau khi đăng nhập thành công
    private static String jwtToken;
    public static void setJwtToken(String token) {
        jwtToken = token;
    }
    public static String getJwtToken() {
        return jwtToken;
    }

    // Thêm header Authorization nếu có token
    private HttpRequest.Builder withAuthHeaders(HttpRequest.Builder builder) {
        if (jwtToken != null && !jwtToken.isEmpty()) {
            System.out.println("Adding Authorization header: Bearer " + jwtToken);
            builder.header("Authorization", "Bearer " + jwtToken);
        } else {
            System.out.println("No token found in HttpClientService!");
        }
        return builder;
    }

    // Generic GET method
    public <T> ApiResponse<T> get(String url, Class<T> responseType) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET();
        withAuthHeaders(builder);
        HttpRequest request = builder.build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 401 || response.statusCode() == 403) {
            throw new RuntimeException("Bạn chưa đăng nhập hoặc token không hợp lệ!");
        }
        if (response.body() == null || response.body().isBlank()) {
            throw new RuntimeException("Server trả về rỗng hoặc lỗi hệ thống.");
        }
        if (response.statusCode() != 200) {
            throw new RuntimeException("Lỗi server: " + response.statusCode() + " - " + response.body());
        }
        return objectMapper.readValue(response.body(),
                objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, responseType));
    }

    // Generic GET method for List responses
    public <T> ApiResponse<List<T>> getList(String url, Class<T> responseType) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET();
        withAuthHeaders(builder);
        HttpRequest request = builder.build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 401 || response.statusCode() == 403) {
            throw new RuntimeException("Bạn chưa đăng nhập hoặc token không hợp lệ!");
        }
        if (response.body() == null || response.body().isBlank()) {
            throw new RuntimeException("Server trả về rỗng hoặc lỗi hệ thống.");
        }
        if (response.statusCode() != 200) {
            throw new RuntimeException("Lỗi server: " + response.statusCode() + " - " + response.body());
        }
        return objectMapper.readValue(response.body(),
                objectMapper.getTypeFactory().constructParametricType(
                        ApiResponse.class,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, responseType)
                ));
    }

    // Generic POST method
    public <T> ApiResponse<T> post(String url, Object requestBody, Class<T> responseType) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(requestBody);
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));
        // Không thêm token cho login/register
        if (!url.contains("/api/auth/")) {
            withAuthHeaders(builder);
        }
        HttpRequest request = builder.build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 401 || response.statusCode() == 403) {
            throw new RuntimeException("Bạn chưa đăng nhập hoặc token không hợp lệ!");
        }
        if (response.body() == null || response.body().isBlank()) {
            throw new RuntimeException("Server trả về rỗng hoặc lỗi hệ thống.");
        }
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Lỗi server: " + response.statusCode() + " - " + response.body());
        }
        return objectMapper.readValue(response.body(),
                objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, responseType));
    }

    // Generic PUT method
    public <T> ApiResponse<T> put(String url, Object requestBody, Class<T> responseType) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(requestBody);
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody));
        withAuthHeaders(builder);
        HttpRequest request = builder.build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 401 || response.statusCode() == 403) {
            throw new RuntimeException("Bạn chưa đăng nhập hoặc token không hợp lệ!");
        }
        if (response.body() == null || response.body().isBlank()) {
            throw new RuntimeException("Server trả về rỗng hoặc lỗi hệ thống.");
        }
        if (response.statusCode() != 200) {
            throw new RuntimeException("Lỗi server: " + response.statusCode() + " - " + response.body());
        }
        return objectMapper.readValue(response.body(),
                objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, responseType));
    }

    // Generic DELETE method
    public ApiResponse<Void> delete(String url) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .DELETE();
        withAuthHeaders(builder);
        HttpRequest request = builder.build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 401 || response.statusCode() == 403) {
            throw new RuntimeException("Bạn chưa đăng nhập hoặc token không hợp lệ!");
        }
        if (response.body() == null || response.body().isBlank()) {
            throw new RuntimeException("Server trả về rỗng hoặc lỗi hệ thống.");
        }
        if (response.statusCode() != 200) {
            throw new RuntimeException("Lỗi server: " + response.statusCode() + " - " + response.body());
        }
        return objectMapper.readValue(response.body(),
                objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, Void.class));
    }

    public static class LoginService extends Service<ApiResponse<LoginResponse>> {
        private LoginRequest loginRequest;

        public void setLoginRequest(LoginRequest loginRequest) {
            this.loginRequest = loginRequest;
        }

        @Override
        protected Task<ApiResponse<LoginResponse>> createTask() {
            return new Task<>() {
                @Override
                protected ApiResponse<LoginResponse> call() throws Exception {
                    String requestBody = objectMapper.writeValueAsString(loginRequest);

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(BASE_URL + "/api/auth/login"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();

                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                    if (response.statusCode() == 200) {
                        if (response.body() != null && response.body().contains("token")) {
                            HttpClientService.setJwtToken(response.body().substring(response.body().indexOf("token") + 6, response.body().indexOf("}")).trim());
                        }
                        return objectMapper.readValue(response.body(),
                                objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, LoginResponse.class));
                    } else {
                        ApiResponse<LoginResponse> errorResponse = objectMapper.readValue(response.body(),
                                objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, LoginResponse.class));
                        throw new RuntimeException(errorResponse.getMessage());
                    }
                }
            };
        }
    }

    public static class RegisterService extends Service<ApiResponse<LoginResponse>> {
        private RegisterRequest registerRequest;

        public void setRegisterRequest(RegisterRequest registerRequest) {
            this.registerRequest = registerRequest;
        }

        @Override
        protected Task<ApiResponse<LoginResponse>> createTask() {
            return new Task<>() {
                @Override
                protected ApiResponse<LoginResponse> call() throws Exception {
                    String requestBody = objectMapper.writeValueAsString(registerRequest);

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(BASE_URL + "/api/auth/register"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();

                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                    if (response.statusCode() == 200) {
                        return objectMapper.readValue(response.body(),
                                objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, LoginResponse.class));
                    } else {
                        ApiResponse<LoginResponse> errorResponse = objectMapper.readValue(response.body(),
                                objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, LoginResponse.class));
                        throw new RuntimeException(errorResponse.getMessage());
                    }
                }
            };
        }
    }
}
