package com.asphalt.gamingsystem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MembersRepository extends MongoRepository<Members, String> {
	
}