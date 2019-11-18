package com.techelevator.projects.model;

import java.util.List;

public interface CampgroundDAO {
	
	public List<Campground> getAllCampgroundsByParkId(int parkId);
	public Campground getCampgroundById(int campgroundId);
	
}
