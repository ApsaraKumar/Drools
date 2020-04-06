package com.vm.drools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DroolsDaoImpl implements DroolsDao {

	@Autowired
	public RrpRepository rrpRepository;
	@Autowired
	public VrpRepository vrpRepository;

	@Override
	public void insertRrp(Rrp rrpObj) {
		rrpRepository.insert(rrpObj);

	}

	@Override
	public List<Rrp> getAll() {
		return rrpRepository.findAll();
	}

	@Override
	public void insertVrp(Vrp vrpObj) {
		vrpRepository.insert(vrpObj);

	}

	@Override
	public List<Vrp> getVrp() {
		return vrpRepository.findAll();
	}

}
