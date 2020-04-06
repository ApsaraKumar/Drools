package com.vm.drools;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface RrpRepository extends MongoRepository<Rrp, String> {

}
