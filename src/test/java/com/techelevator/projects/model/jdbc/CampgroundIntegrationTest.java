package com.techelevator.projects.model.jdbc;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.techelevator.projects.model.Campground;

public class CampgroundIntegrationTest extends DAOIntegrationTest {
	private JDBCCampgroundDAO cdao;

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
		cdao = new JDBCCampgroundDAO(getDataSource());
	}

	@After
	public void tearDown() throws Exception {
		rollback();
	}

	@Test
	public void getAllCampgroundsByParkIdShouldReturnAListWithRightLength() {
		// You will need to change the testParkId to a park_id in your database
		int testParkId = 1;
		List<Campground> actualResults = cdao.getAllCampgroundsByParkId(testParkId);

		// Note you need to set the expectedResultLength to the number of Campgrounds by
		// the testParkId
		int expectedResultsLength = 3;
		assertEquals(expectedResultsLength, actualResults.size());

		// Test the open_from_mm & open_to_mm & daily_fee not null
		assertNotNull(actualResults.get(0).getCampgroundOpenFromMm());
		assertNotNull(actualResults.get(0).getCampgroundOpenToMm());
		assertNotNull(actualResults.get(0).getCampgroundDailyFee());

		// Test a specific case from your own database:
		assertEquals("01", actualResults.get(0).getCampgroundOpenFromMm());
		assertEquals("12", actualResults.get(0).getCampgroundOpenToMm());
		assertEquals(BigDecimal.valueOf(35).setScale(2), actualResults.get(0).getCampgroundDailyFee());
	}

	@Test
	public void getCampgroundByIdShouldReturnSelectedCampground() {
		// You will need to change the testCampgroundId to a campground_id in your
		// database
		int testCampgroundId = 1;
		Campground actualResults = cdao.getCampgroundById(testCampgroundId);
		assertEquals("Blackwoods", actualResults.getCampgroundName());
	}

}
