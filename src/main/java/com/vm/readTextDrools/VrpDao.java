package com.vm.readTextDrools;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VrpDao{

	public void createtest(Vrp vrpObj);

	public List<Vrp> getAll();
}
