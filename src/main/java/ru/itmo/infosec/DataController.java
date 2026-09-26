package ru.itmo.infosec;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private final AppUserRepository users;

    public DataController(AppUserRepository users) {
        this.users = users;
    }

    @GetMapping
    public List<UserData> list() {
        return users.findAll().stream()
                .map(user -> new UserData(user.getId(), HtmlUtils.htmlEscape(user.getUsername())))
                .toList();
    }

    public record UserData(Long id, String username) {
    }
}
