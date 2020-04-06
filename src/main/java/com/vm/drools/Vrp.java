package com.vm.drools;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vrp")
public class Vrp {

	private double spend;
	private double basePrice;
	private double customerPrice;
	private double vendorPrice;
	private double test;
	private String onSite;

	public String getOnSite() {
		return onSite;
	}

	public void setOnSite(String onSite) {
		this.onSite = onSite;
	}

	public Vrp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vrp(double test) {
		super();
		this.test = test;

	}

	public double getTest() {
		return test;
	}

	public void setTest(double test) {
		this.test = test;
	}

	public double getSpend() {
		return spend;
	}

	public void setSpend(double spend) {
		this.spend = spend;
	}

	public double getVendorPrice() {
		return vendorPrice;
	}

	public void setVendorPrice(double vendorPrice) {
		this.vendorPrice = vendorPrice;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public double getCustomerPrice() {
		return customerPrice;
	}

	public void setCustomerPrice(double customerPrice) {
		this.customerPrice = customerPrice;
	}

	@Override
	public String toString() {
		return "Vrp [spend=" + spend + ", basePrice=" + basePrice + ", customerPrice=" + customerPrice
				+ ", vendorPrice=" + vendorPrice + ", test=" + test + ", onSite=" + onSite + "]";
	}
}
