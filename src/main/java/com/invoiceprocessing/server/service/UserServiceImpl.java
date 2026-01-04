package com.invoiceprocessing.server.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.invoiceprocessing.server.dao.UserRepository;
import com.invoiceprocessing.server.exception.AuthenticationException;
import com.invoiceprocessing.server.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final String jwtSecret = "mySecretKeyForJWTTokenGenerationMustBeLongEnough12345";
    private final SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) {
        // Validierung: Username erforderlich
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new AuthenticationException(
                "Der Benutzername ist erforderlich",
                400,
                "INVALID_USERNAME"
            );
        }

        // Validierung: Username-Länge
        if (user.getUsername().length() < 3) {
            throw new AuthenticationException(
                "Der Benutzername muss mindestens 3 Zeichen lang sein",
                400,
                "INVALID_USERNAME"
            );
        }

        // Prüfe ob Username bereits existiert
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new AuthenticationException(
                "Dieser Benutzername ist bereits vergeben",
                409,
                "USERNAME_ALREADY_EXISTS"
            );
        }

        // Validierung: Passwort erforderlich
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new AuthenticationException(
                "Das Passwort ist erforderlich",
                400,
                "INVALID_PASSWORD"
            );
        }

        // Validierung: Passwort-Länge
        if (user.getPassword().length() < 6) {
            throw new AuthenticationException(
                "Das Passwort muss mindestens 6 Zeichen lang sein",
                400,
                "WEAK_PASSWORD"
            );
        }

        // Passwort hashen und speichern
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        // Validierung: Username erforderlich
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationException(
                "Der Benutzername ist erforderlich",
                400,
                "INVALID_USERNAME"
            );
        }

        // Validierung: Passwort erforderlich
        if (password == null || password.trim().isEmpty()) {
            throw new AuthenticationException(
                "Das Passwort ist erforderlich",
                400,
                "INVALID_PASSWORD"
            );
        }

        // Benutzer suchen
        User user = userRepository.findByUsername(username);
        
        // Prüfe ob Benutzer existiert und Passwort korrekt ist
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException(
                "Der Benutzername oder das Passwort ist falsch",
                401,
                "INVALID_CREDENTIALS"
            );
        }

        // JWT Token generieren
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 Tag
                .signWith(key)
                .compact();
        
        user.setPassword(token); // Missbrauche das Feld, besser wäre ein DTO
        return user;
    }
}

