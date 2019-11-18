package com.techelevator.projects.model;

public class Site {

	private int siteId;
	private int siteCampgroundId;
	private int siteNumber;
	private int siteMaxOccupancy;
	private boolean siteAccessible;
	private int siteMaxRVLength;
	private boolean siteUtilities;
	
	public Site() {
	}
	
	public Site(int siteId, int siteCampgroundId, int siteNumber, int siteMaxOccupancy, boolean siteAccessible,
			int siteMaxRVLength, boolean siteUtilities) {
		
		this.siteId = siteId;
		this.siteCampgroundId = siteCampgroundId;
		this.siteNumber = siteNumber;
		this.siteMaxOccupancy = siteMaxOccupancy;
		this.siteAccessible = siteAccessible;
		this.siteMaxRVLength = siteMaxRVLength;
		this.siteUtilities = siteUtilities;
	}
	
	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getSiteCampgroundId() {
		return siteCampgroundId;
	}

	public void setSiteCampgroundId(int siteCampgroundId) {
		this.siteCampgroundId = siteCampgroundId;
	}

	public int getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}

	public int getSiteMaxOccupancy() {
		return siteMaxOccupancy;
	}

	public void setSiteMaxOccupancy(int siteMaxOccupancy) {
		this.siteMaxOccupancy = siteMaxOccupancy;
	}

	public boolean isSiteAccessible() {
		return siteAccessible;
	}

	public void setSiteAccessible(boolean siteAccessible) {
		this.siteAccessible = siteAccessible;
	}

	public int getSiteMaxRVLength() {
		return siteMaxRVLength;
	}

	public void setSiteMaxRVLength(int siteMaxRVLength) {
		this.siteMaxRVLength = siteMaxRVLength;
	}

	public boolean isSiteUtilities() {
		return siteUtilities;
	}

	public void setSiteUtilities(boolean siteUtilities) {
		this.siteUtilities = siteUtilities;
	}	
	
}
