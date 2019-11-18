package com.techelevator.projects.model.jdbc;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.techelevator.projects.model.Park;

public class ParkIntegrationTest extends DAOIntegrationTest {

	private JDBCParkDAO pdao;

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
		pdao = new JDBCParkDAO(getDataSource());
	}

	@After
	public void tearDown() throws Exception {
		rollback();
	}

	@Test
	public void getAllParksShouldReturnAListWithRightLength() {
		List<Park> actualResults = pdao.getAllParks();

		// Note you need to set the expectedResultsLength to the total number of parks
		// in your database
		int expectedResultsLength = 3;
		assertEquals(expectedResultsLength, actualResults.size());
	}

	@Test
	public void searchParkByNameShouldReturnTheSameParkIfFound() {
		// Note you should change searchParam to a park that is in your database
		String searchParam = "Acadia";
		Park actualResults = pdao.searchParkByName(searchParam);
		assertEquals(searchParam, actualResults.getParkName());
	}

	@Test
	public void getParkByIdShouldReturnTheSameParkIfFound() {
		// Note you should change searchId to a parkId that is in your database,
		// and you need to change expectedParkName to the corresponding park
		int searchId = 1;
		String expectedParkName = "Acadia";
		Park actualResults = pdao.getParkById(searchId);
		assertEquals(expectedParkName, actualResults.getParkName());
	}

}
