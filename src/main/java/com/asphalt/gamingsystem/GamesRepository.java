package com.asphalt.gamingsystem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GamesRepository extends MongoRepository<Games, String> {
	
}