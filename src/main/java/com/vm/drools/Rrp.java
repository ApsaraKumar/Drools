package com.vm.drools;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rrp")
public class Rrp {

	private Boolean onSite;
	private String taxId;
	private Boolean q1;
	private Boolean q2;
	private Boolean q3;
	private Boolean q4;
	private Boolean q5;
	private String type;
	private String docVerificationReq;
	private String requiredDocumentBundles;
	private String optionalDocumentBUndles;
	private String comment;
	private String customerLocations;
	private String customerDepartments;
	private Boolean testOnSite;
	private String test;

	public Rrp() {
		super();
	}
	public Rrp(String test) {
		super();
		this.test = test;
	}

	public String getCustomerLocations() {
		return customerLocations;
	}

	public void setCustomerLocations(String customerLocations) {
		this.customerLocations = customerLocations;
	}

	public String getCustomerDepartments() {
		return customerDepartments;
	}

	public void setCustomerDepartments(String customerDepartments) {
		this.customerDepartments = customerDepartments;
	}

	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public Boolean getOnSite() {
		return onSite;
	}

	public void setOnSite(Boolean onSite) {
		this.onSite = onSite;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public Boolean getQ1() {
		return q1;
	}

	public void setQ1(Boolean q1) {
		this.q1 = q1;
	}

	public Boolean getQ2() {
		return q2;
	}

	public void setQ2(Boolean q2) {
		this.q2 = q2;
	}

	public Boolean getQ3() {
		return q3;
	}

	public void setQ3(Boolean q3) {
		this.q3 = q3;
	}

	public Boolean getQ4() {
		return q4;
	}

	public void setQ4(Boolean q4) {
		this.q4 = q4;
	}

	public Boolean getQ5() {
		return q5;
	}

	public void setQ5(Boolean q5) {
		this.q5 = q5;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDocVerificationReq() {
		return docVerificationReq;
	}

	public void setDocVerificationReq(String docVerificationReq) {
		this.docVerificationReq = docVerificationReq;
	}

	public String getRequiredDocumentBundles() {
		return requiredDocumentBundles;
	}

	public void setRequiredDocumentBundles(String requiredDocumentBundles) {
		this.requiredDocumentBundles = requiredDocumentBundles;
	}

	public String getOptionalDocumentBUndles() {
		return optionalDocumentBUndles;
	}

	public void setOptionalDocumentBUndles(String optionalDocumentBUndles) {
		this.optionalDocumentBUndles = optionalDocumentBUndles;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Rrp [custListTest=" + test + ", onSite=" + onSite + ", taxId="
				+ taxId + ", q1=" + q1 + ", q2=" + q2 + ", q3=" + q3 + ", q4=" + q4 + ", q5=" + q5 + ", type=" + type
				+ ", docVerificationReq=" + docVerificationReq + ", requiredDocumentBundles=" + requiredDocumentBundles
				+ ", optionalDocumentBUndles=" + optionalDocumentBUndles + ", comment=" + comment
				+ ", customerLocations=" + customerLocations + ", customerDepartments=" + customerDepartments
				+ ", testOnSite=" + testOnSite + "]";
	}

}
