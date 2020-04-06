package com.vm.drools;

import java.util.List;

public interface DroolsDao {

	public void insertRrp(Rrp rrpObj);

	public List<Rrp> getAll();

	public void insertVrp(Vrp vrpObj);

	public List<Vrp> getVrp();
}
