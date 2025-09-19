package com.asphalt.gamingsystem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // DTO for login request
    public static class LoginRequest {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }

    // GET /auth - simple test endpoint for browser
    @GetMapping
    public String test() {
        return "AuthController is running!";
    }

    // POST /auth/login - login endpoint
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        if ("admin".equals(request.getUsername()) && "admin".equals(request.getPassword())) {
            // Normally you would generate a JWT token here
            return "fake-jwt-token";
        }
        // Return 401 Unauthorized if login fails
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }
}
