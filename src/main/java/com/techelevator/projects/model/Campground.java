package com.techelevator.projects.model;

import java.math.BigDecimal;

public class Campground {

	private int campgroundId;
	private int campgroundParkId;
	private String campgroundName;
	private String campgroundOpenFromMm;
	private String campgroundOpenToMm;
	private BigDecimal campgroundDailyFee;
	
	public Campground() {
		
	}
	
	public Campground(int campgroundId, int campgroundParkId, String campgroundName, String campgroundOpenFromMm,
			String campgroundOpenToMm, BigDecimal campgroundDailyFee) {
		this.campgroundId = campgroundId;
		this.campgroundParkId = campgroundParkId;
		this.campgroundName = campgroundName;
		this.campgroundOpenFromMm = campgroundOpenFromMm;
		this.campgroundOpenToMm = campgroundOpenToMm;
		this.campgroundDailyFee = campgroundDailyFee;
	}

	public int getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}

	public int getCampgroundParkId() {
		return campgroundParkId;
	}

	public void setCampgroundParkId(int campgroundParkId) {
		this.campgroundParkId = campgroundParkId;
	}

	public String getCampgroundName() {
		return campgroundName;
	}

	public void setCampgroundName(String campgroundName) {
		this.campgroundName = campgroundName;
	}

	public String getCampgroundOpenFromMm() {
		return campgroundOpenFromMm;
	}

	public void setCampgroundOpenFromMm(String campgroundOpenFromMm) {
		this.campgroundOpenFromMm = campgroundOpenFromMm;
	}

	public String getCampgroundOpenToMm() {
		return campgroundOpenToMm;
	}

	public void setCampgroundOpenToMm(String campgroundOpenToMm) {
		this.campgroundOpenToMm = campgroundOpenToMm;
	}

	public BigDecimal getCampgroundDailyFee() {
		return campgroundDailyFee;
	}

	public void setCampgroundDailyFee(BigDecimal campgroundDailyFee) {
		this.campgroundDailyFee = campgroundDailyFee;
	}
	
}
