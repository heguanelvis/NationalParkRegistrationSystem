package com.techelevator.projects.model;

import java.time.LocalDate;

public class Reservation {
	
	private int reservationId;
	private int reservationSiteId;
	private String reservationName;
	private LocalDate reservationFromDate;
	private LocalDate reservationToDate;
	private LocalDate reservationCreateDate;
	
	public Reservation() {
		
	}
	
	public Reservation(int reservationId, int reservationSiteId, String reservationName, LocalDate reservationFromDate,
			LocalDate reservationToDate, LocalDate reservationCreateDate) {
		this.reservationId = reservationId;
		this.reservationSiteId = reservationSiteId;
		this.reservationName = reservationName;
		this.reservationFromDate = reservationFromDate;
		this.reservationToDate = reservationToDate;
		this.reservationCreateDate = reservationCreateDate;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getReservationSiteId() {
		return reservationSiteId;
	}

	public void setReservationSiteId(int reservationSiteId) {
		this.reservationSiteId = reservationSiteId;
	}

	public String getReservationName() {
		return reservationName;
	}

	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}

	public LocalDate getReservationFromDate() {
		return reservationFromDate;
	}

	public void setReservationFromDate(LocalDate reservationFromDate) {
		this.reservationFromDate = reservationFromDate;
	}

	public LocalDate getReservationToDate() {
		return reservationToDate;
	}

	public void setReservationToDate(LocalDate reservationToDate) {
		this.reservationToDate = reservationToDate;
	}

	public LocalDate getReservationCreateDate() {
		return reservationCreateDate;
	}

	public void setReservationCreateDate(LocalDate reservationCreateDate) {
		this.reservationCreateDate = reservationCreateDate;
	}
	
}
