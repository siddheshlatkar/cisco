package com.cisco.crudapis.repository;

import com.cisco.crudapis.model.ArbitraryObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Arbitrary object CRUD operations.
 */
@Repository
public interface ArbitraryObjectRepository extends MongoRepository<ArbitraryObject, String> {

}
