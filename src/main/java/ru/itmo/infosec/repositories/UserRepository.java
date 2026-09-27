package ru.itmo.infosec.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.infosec.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
