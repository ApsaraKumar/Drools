package com.vm.readTextDrools;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class VrpDaoImpl implements VrpDao {

	@Autowired
	public VrpRepository vrpRepository;

	@Override
	public void createtest(Vrp vrpobj) {
		vrpRepository.insert(vrpobj);

	}

	@Override
	public List<Vrp> getAll() {

		return vrpRepository.findAll();
	}

}
