package com.techelevator.projects.model;

import java.sql.Date;
import java.util.List;

public interface SiteDAO {
	
	public List<Site> getAllSitesByCampgroundId(int campgroundId);
	public List<Site> searchAvailableSitesByCampgroundId(int campgroundId, Date sqlArrivalDate, Date sqlDepartureDate);
	public List<Site> searchAvailableSitesByParkId(int parkId, Date sqlArrivalDate, Date sqlDepartureDate);
	
}
