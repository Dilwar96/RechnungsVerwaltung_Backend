package com.invoiceprocessing.server.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceprocessing.server.exception.AuthenticationException;
import com.invoiceprocessing.server.model.User;
import com.invoiceprocessing.server.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registrierung Endpoint
     * POST /register
     * Body: { "username": "...", "password": "..." }
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = userService.register(user);
            
            // Gebe keine Passwörter/Tokens zurück
            Map<String, Object> response = new HashMap<>();
            response.put("id", registeredUser.getId());
            response.put("username", registeredUser.getUsername());
            response.put("message", "Benutzer erfolgreich registriert");
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(createErrorResponse(ex));
        }
    }

    /**
     * Login Endpoint
     * POST /login
     * Body: { "username": "...", "password": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User loggedIn = userService.login(user.getUsername(), user.getPassword());
            String token = loggedIn.getPassword(); // Token ist im Passwort-Feld
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", loggedIn.getId());
            response.put("username", loggedIn.getUsername());
            response.put("token", token);
            response.put("message", "Erfolgreich angemeldet");
            
            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(createErrorResponse(ex));
        }
    }

    /**
     * Logout Endpoint
     * POST /logout
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Erfolgreich abgemeldet");
        return ResponseEntity.ok(response);
    }

    /**
     * Hilfsfunktion: Error Response erstellen
     */
    private Map<String, Object> createErrorResponse(AuthenticationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ex.getStatusCode());
        response.put("error", ex.getErrorCode());
        response.put("message", ex.getMessage());
        return response;
    }
}

