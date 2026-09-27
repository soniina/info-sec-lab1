package ru.itmo.infosec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Credentials(
        @NotBlank @Pattern(regexp = "[A-Za-z0-9_]{3,32}") String username,
        @NotBlank @Size(min = 8, max = 72) String password) {
}
