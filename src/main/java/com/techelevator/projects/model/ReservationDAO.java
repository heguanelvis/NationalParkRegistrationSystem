package com.techelevator.projects.model;

import java.util.List;

public interface ReservationDAO {
	
	public List<Reservation> getAllReservationsBySiteId(int siteId);
	public Reservation saveReservation(Reservation reservation);
	
}
