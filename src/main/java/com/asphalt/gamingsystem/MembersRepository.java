package com.asphalt.gamingsystem;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MembersRepository extends MongoRepository<Members, String> {
    Optional<Members> findByPhone(String phone);
}
