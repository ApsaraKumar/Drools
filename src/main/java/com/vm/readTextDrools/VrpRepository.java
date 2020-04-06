package com.vm.readTextDrools;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VrpRepository extends MongoRepository<Vrp, String> {

}
