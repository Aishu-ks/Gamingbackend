package com.asphalt.gamingsystem;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface admin_usersRepository extends MongoRepository<admin_users, String> {
    Optional<admin_users> findByUsername(String username);
}