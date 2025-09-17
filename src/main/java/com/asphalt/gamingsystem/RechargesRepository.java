package com.asphalt.gamingsystem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RechargesRepository extends MongoRepository<Recharges, String> {
	
}