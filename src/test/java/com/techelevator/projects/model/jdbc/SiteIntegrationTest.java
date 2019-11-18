package com.techelevator.projects.model.jdbc;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.techelevator.projects.model.Site;

public class SiteIntegrationTest extends DAOIntegrationTest {

	private JDBCSiteDAO sdao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setupDataSource();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		closeDataSource();
	}

	@Before
	public void setUp() throws Exception {
		sdao = new JDBCSiteDAO(getDataSource());
	}

	@After
	public void tearDown() throws Exception {
		rollback();
	}

	@Test
	public void getAllSitesByCampgroundIdShouldReturnAListWithRightLength() {
		// You will need to change the testCampgroundId to a campground id in database
		int testCampgroundId = 5;
		List<Site> actualResults = sdao.getAllSitesByCampgroundId(testCampgroundId);

		// You will need to set the expectedResultLength to the number of sites by the
		// testCampgroundId
		int expectedResultsLength = 1;
		assertEquals(expectedResultsLength, actualResults.size());
	}

	// There are of course situations where we cannot find matching records
	// please change the results length based on your own database
	@Test
	public void searchAvailableSitesByCampgroundIdShouldReturn5AvailablePopularCampsites1() {
		// You will need to change the testCampgroundId, testSqlArrivalDate and
		// testSqlDepartureDate
		int testCampgroundId = 1;
		Date testSqlArrivalDate = Date.valueOf(LocalDate.of(2019, 10, 27));
		Date testSqlDepartureDate = Date.valueOf(LocalDate.of(2019, 10, 30));
		List<Site> actualResults = sdao.searchAvailableSitesByCampgroundId(testCampgroundId, testSqlArrivalDate,
				testSqlDepartureDate);

		// Note you need to change the expectedSiteId to a siteId in the database
		int expectedSiteId = 32;
		assertEquals(testCampgroundId, actualResults.get(0).getSiteCampgroundId());
		assertEquals(expectedSiteId, actualResults.get(0).getSiteId());
		assertEquals(5, actualResults.size());
	}

	@Test
	public void searchAvailableSitesByCampgroundIdShouldReturn5AvailablePopularCampsites2() {
		// You will need to change the testCampgroundId, testSqlArrivalDate and
		// testSqlDepartureDate
		int testCampgroundId = 1;
		Date testSqlArrivalDate = Date.valueOf(LocalDate.of(2019, 10, 28));
		Date testSqlDepartureDate = Date.valueOf(LocalDate.of(2019, 10, 30));
		List<Site> actualResults = sdao.searchAvailableSitesByCampgroundId(testCampgroundId, testSqlArrivalDate,
				testSqlDepartureDate);

		// Note you need to change the expectedSiteId to a siteId in the database
		int expectedSiteId = 4;
		assertEquals(testCampgroundId, actualResults.get(1).getSiteCampgroundId());
		assertEquals(expectedSiteId, actualResults.get(3).getSiteId());
		assertEquals(5, actualResults.size());
	}

	@Test
	public void searchAvailableSitesByParkIdShouldReturn5AvailablePopularSites() {
		int testParkId = 1;
		Date testSqlArrivalDate = Date.valueOf(LocalDate.of(2019, 10, 28));
		Date testSqlDepartureDate = Date.valueOf(LocalDate.of(2019, 10, 30));
		List<Site> actualResults = sdao.searchAvailableSitesByParkId(testParkId, testSqlArrivalDate,
				testSqlDepartureDate);

		// Note you need to change the expectedSiteId to a siteId in the database
		int expectedSiteId = 24;
		assertEquals(testParkId, actualResults.get(1).getSiteCampgroundId());
		assertEquals(expectedSiteId, actualResults.get(0).getSiteId());
		assertEquals(5, actualResults.size());
	}
	
}
