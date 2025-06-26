package com.doanjava.gradehub;

import com.doanjava.gradehub.dto.LoginRequest;
import com.doanjava.gradehub.service.HttpClientService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HttpClientServiceTest {

    @Test
    public void testLoginRequestCreation() {
        LoginRequest request = new LoginRequest("test@example.com", "password123");
        assertEquals("test@example.com", request.email());
        assertEquals("password123", request.password());
    }

    @Test
    public void testLoginRequestValidation() {
        // Test null email
        assertThrows(IllegalArgumentException.class, () -> {
            new LoginRequest(null, "password123");
        });

        // Test null password
        assertThrows(IllegalArgumentException.class, () -> {
            new LoginRequest("test@example.com", null);
        });

        // Test empty email
        assertThrows(IllegalArgumentException.class, () -> {
            new LoginRequest("", "password123");
        });

        // Test empty password
        assertThrows(IllegalArgumentException.class, () -> {
            new LoginRequest("test@example.com", "");
        });
    }
}
