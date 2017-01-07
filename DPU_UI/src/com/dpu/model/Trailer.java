package com.dpu.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Trailer {

	@JsonProperty(value = "trailer_id")
	private Integer trailerId;
	
	@JsonProperty(value = "class_id")
	private Integer classId;
	
	@JsonProperty(value = "equipment_id")
	private Integer equipmentId;
	
	@JsonProperty(value = "length")
	private Integer length;
	
	@JsonProperty(value = "vin")
	private String vin;
	
	@JsonProperty(value = "make")
	private String make;
	
	@JsonProperty(value = "model")
	private String model;
	
	@JsonProperty(value = "year")
	private Integer year;
	
	@JsonProperty(value = "plate_no")
	private String plateNo;
	
	@JsonProperty(value = "jurisdiction")
	private String jurisdiction;
	
	@JsonProperty(value = "tare_weight")
	private String tareWeight;
	
	@JsonProperty(value = "rgw")
	private String rgw;
	
	@JsonProperty(value = "current_odometer")
	private String currentOdometer;
	
	@JsonProperty(value = "reading_taken_date")
	private Date readingTakenDate;
	
	@JsonProperty(value = "created_by")
	private Integer createdBy;
	
	@JsonProperty(value = "created_on")
	private Date createdOn;

	public Integer getTrailerId() {
		return trailerId;
	}

	public void setTrailerId(Integer trailerId) {
		this.trailerId = trailerId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public String getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(String tareWeight) {
		this.tareWeight = tareWeight;
	}

	public String getRgw() {
		return rgw;
	}

	public void setRgw(String rgw) {
		this.rgw = rgw;
	}

	public String getCurrentOdometer() {
		return currentOdometer;
	}

	public void setCurrentOdometer(String currentOdometer) {
		this.currentOdometer = currentOdometer;
	}

	public Date getReadingTakenDate() {
		return readingTakenDate;
	}

	public void setReadingTakenDate(Date readingTakenDate) {
		this.readingTakenDate = readingTakenDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}