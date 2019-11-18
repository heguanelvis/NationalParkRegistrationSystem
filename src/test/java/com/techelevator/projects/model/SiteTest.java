package com.techelevator.projects.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SiteTest {

	private Site emptySite = new Site();

	@Test
	public void siteParamConstructorShouldConstructNewSite() {
		Site filledSite = new Site(1, 2, 3, 500, true, 100, true);

		// TestAllGetters:
		assertEquals(1, filledSite.getSiteId());
		assertEquals(2, filledSite.getSiteCampgroundId());
		assertEquals(3, filledSite.getSiteNumber());
		assertEquals(500, filledSite.getSiteMaxOccupancy());
		assertEquals(true, filledSite.isSiteAccessible());
		assertEquals(100, filledSite.getSiteMaxRVLength());
		assertEquals(true, filledSite.isSiteUtilities());
	}

	@Test
	public void settersShouldsSetValuesOfInstanceCorrectly() {
		emptySite.setSiteId(1);
		emptySite.setSiteCampgroundId(2);
		emptySite.setSiteNumber(3);
		emptySite.setSiteMaxOccupancy(500);
		emptySite.setSiteAccessible(true);
		emptySite.setSiteMaxRVLength(100);
		emptySite.setSiteUtilities(true);

		// TestAllGetters:
		assertEquals(1, emptySite.getSiteId());
		assertEquals(2, emptySite.getSiteCampgroundId());
		assertEquals(3, emptySite.getSiteNumber());
		assertEquals(500, emptySite.getSiteMaxOccupancy());
		assertEquals(true, emptySite.isSiteAccessible());
		assertEquals(100, emptySite.getSiteMaxRVLength());
		assertEquals(true, emptySite.isSiteUtilities());
	}

}
