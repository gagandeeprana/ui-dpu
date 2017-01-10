package com.dpu.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Truck {

	private String usage;
	
	private String owner;
	
	private String division;
	
	private String oOName;
	
	private String terminal;
	
	private String category;
	
	private String truckType;
	
	private String finance;
	
	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getoOName() {
		return oOName;
	}

	public void setoOName(String oOName) {
		this.oOName = oOName;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTruckType() {
		return truckType;
	}

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}

	public String getFinance() {
		return finance;
	}

	public void setFinance(String finance) {
		this.finance = finance;
	}

	//@JsonProperty(value="truck_id")
	private Long truckId;
	
	//@JsonProperty(value="unit_no")
	private Integer unitNo;
	
	//@JsonProperty(value = "status")
	private String status;
	
	//@JsonProperty(value = "truck_class")
	private String truckClass;
	
	//@JsonProperty(value = "owner_id")
	private Integer ownerId;
	
	//@JsonProperty(value = "VIN")
	private String vin;
	
	//@JsonProperty(value = "make")
	private String make;
	
	//@JsonProperty(value = "model")
	private String model;
	
	//@JsonProperty(value = "truck_year")
	private Integer truckYear;
	
	//@JsonProperty(value = "plate_no")
	private String plateNo;
	
	//@JsonProperty(value = "jurisdiction")
	private String jurisdiction;
	
	//@JsonProperty(value = "tare_weight")
	private Integer tareWeight;
	
	//@JsonProperty(value = "rgw")
	private String rgw;
	 
	//@JsonProperty(value = "current_odometer")
	private String currentOdometer;
	
	//@JsonProperty(value = "equipment_type")
	private String equipmentType;
	
	//@JsonProperty(value = "created_by")
	private String createdBy;
	
	//@JsonProperty(value = "created_on")
	private Date createdOn;
	
	//@JsonProperty(value = "modified_by")
	private String modifiedBy;
	
	//@JsonProperty(value = "modified_on")
	private Date modifiedOn;

	public Long getTruckId() {
		return truckId;
	}

	public void setTruckId(Long truckId) {
		this.truckId = truckId;
	}

	public Integer getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(Integer unitNo) {
		this.unitNo = unitNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTruckClass() {
		return truckClass;
	}

	public void setTruckClass(String truckClass) {
		this.truckClass = truckClass;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
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

	public Integer getTruckYear() {
		return truckYear;
	}

	public void setTruckYear(Integer truckYear) {
		this.truckYear = truckYear;
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

	public Integer getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Integer tareWeight) {
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

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
}	 
	 
