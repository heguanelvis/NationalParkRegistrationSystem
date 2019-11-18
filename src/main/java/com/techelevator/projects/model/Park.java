package com.techelevator.projects.model;

import java.time.LocalDate;

public class Park {
	
	private int parkId;
	private String parkName;
	private String parkLocation;
	private LocalDate parkEstablishDate;
	private int parkArea;
	private int parkNumberOfVisitors;
	private String parkDescription;
	
	public Park() {
		
	}
	
	public Park(int parkId, String parkName, String parkLocation, LocalDate parkEstablishDate, int parkArea,
			int parkNumberOfVisitors, String parkDescription) {
		this.parkId = parkId;
		this.parkName = parkName;
		this.parkLocation = parkLocation;
		this.parkEstablishDate = parkEstablishDate;
		this.parkArea = parkArea;
		this.parkNumberOfVisitors = parkNumberOfVisitors;
		this.parkDescription = parkDescription;
	}

	public int getParkId() {
		return parkId;
	}

	public void setParkId(int parkId) {
		this.parkId = parkId;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getParkLocation() {
		return parkLocation;
	}

	public void setParkLocation(String parkLocation) {
		this.parkLocation = parkLocation;
	}

	public LocalDate getParkEstablishDate() {
		return parkEstablishDate;
	}

	public void setParkEstablishDate(LocalDate parkEstablishDate) {
		this.parkEstablishDate = parkEstablishDate;
	}

	public int getParkArea() {
		return parkArea;
	}

	public void setParkArea(int parkArea) {
		this.parkArea = parkArea;
	}

	public int getParkNumberOfVisitors() {
		return parkNumberOfVisitors;
	}

	public void setParkNumberOfVisitors(int parkNumberOfVisitors) {
		this.parkNumberOfVisitors = parkNumberOfVisitors;
	}

	public String getParkDescription() {
		return parkDescription;
	}

	public void setParkDescription(String parkDescription) {
		this.parkDescription = parkDescription;
	}
	
}
