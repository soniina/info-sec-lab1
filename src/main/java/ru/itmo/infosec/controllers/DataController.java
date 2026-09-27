package ru.itmo.infosec.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import ru.itmo.infosec.dto.UserResponse;
import ru.itmo.infosec.services.UserService;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private final UserService userService;

    public DataController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> list() {
        return userService.findAll().stream()
                .map(user -> new UserResponse(user.getId(), HtmlUtils.htmlEscape(user.getUsername())))
                .toList();
    }
}
