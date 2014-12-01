package com.winterdev.librarycloud.domain;

public class LibraryItemLocation {
	
	private String locationCode;
	private String locationName;
	private String callNumber;

	public String toString() {
		return this.locationCode + " - " + this.locationName + " - " + this.callNumber;
	}
	
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getCallNumber() {
		return callNumber;
	}
	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}
}
