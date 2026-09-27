package ru.itmo.infosec.dto;

import org.springframework.web.util.HtmlUtils;
import ru.itmo.infosec.models.User;

public record UserResponse(Long id, String username) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), HtmlUtils.htmlEscape(user.getUsername()));
    }
}
