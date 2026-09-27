package ru.itmo.infosec.controllers;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.itmo.infosec.dto.Credentials;
import ru.itmo.infosec.dto.TokenResponse;
import ru.itmo.infosec.dto.UserResponse;
import ru.itmo.infosec.models.User;
import ru.itmo.infosec.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody Credentials credentials) {
        try {
            User user = userService.register(credentials.username(), credentials.password());
            return UserResponse.from(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
        }
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody Credentials credentials) {
        String token = userService.login(credentials.username(), credentials.password())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        return new TokenResponse(token);
    }
}
