package com.techelevator.projects.model;

import java.util.List;

public interface ParkDAO {
	
	public List<Park> getAllParks();
	public Park searchParkByName(String parkName);
	public Park getParkById(int parkId);
	
}
