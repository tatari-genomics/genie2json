package com.tatari.magiclamp.domain;

import org.springframework.data.annotation.Id;

/**
 * This class maps to a document in the clinical collection within the GENIE database
 */
public class Clinical {

	@Id
	private String id;
	
	private String patient;
	private String sample;
	private String age;
	private String oncotreeCode;
	private String cancerType;
	private String cancerTypeDetailed;
	private String sampleType;
	private String assayId;
	private String cancerTypeHighLevel;

	
	
	public Clinical() {
		super();
	}


	public Clinical(String sample, String age, String oncotreeCode, String sampleType, String assayId) {
		super();
		this.sample = sample;
		this.age = age;
		this.oncotreeCode = oncotreeCode;
		this.sampleType = sampleType;
		this.assayId = assayId;
	}


	public String getCancerTypeHighLevel() {
		return cancerTypeHighLevel;
	}


	public void setCancerTypeHighLevel(String cancerTypeHighLevel) {
		this.cancerTypeHighLevel = cancerTypeHighLevel;
	}


	public String getSample() {
		return sample;
	}


	public void setSample(String sample) {
		this.sample = sample;
	}


	public String getAge() {
		return age;
	}


	public void setAge(String age) {
		this.age = age;
	}


	public String getOncotreeCode() {
		return oncotreeCode;
	}


	public void setOncotreeCode(String oncotreeCode) {
		this.oncotreeCode = oncotreeCode;
	}


	public String getSampleType() {
		return sampleType;
	}


	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}


	public String getAssayId() {
		return assayId;
	}


	public void setAssayId(String assayId) {
		this.assayId = assayId;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPatient() {
		return patient;
	}


	public void setPatient(String patient) {
		this.patient = patient;
	}


	public String getCancerType() {
		return cancerType;
	}


	public void setCancerType(String cancerType) {
		this.cancerType = cancerType;
	}


	public String getCancerTypeDetailed() {
		return cancerTypeDetailed;
	}


	public void setCancerTypeDetailed(String cancerTypeDetailed) {
		this.cancerTypeDetailed = cancerTypeDetailed;
	}


	@Override
	public String toString() {
		return "Clinical [age=" + age + ", oncotreeCode=" + oncotreeCode + ", sampleType=" + sampleType + ", assayId="
				+ assayId + "]";
	}
	
	
}
