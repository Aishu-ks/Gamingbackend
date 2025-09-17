package com.asphalt.gamingsystem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CollectionsRepository extends MongoRepository<Collections, String> {
	
}