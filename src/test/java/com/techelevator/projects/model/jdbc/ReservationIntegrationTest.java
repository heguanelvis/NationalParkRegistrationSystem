package com.techelevator.projects.model.jdbc;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.techelevator.projects.model.Reservation;

public class ReservationIntegrationTest extends DAOIntegrationTest {

	private JDBCReservationDAO rdao;

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
		rdao = new JDBCReservationDAO(getDataSource());
	}

	@After
	public void tearDown() throws Exception {
		rollback();
	}

	@Test
	public void getAllReservationsBySiteIdReturnsAListWithRightLength() {
		// You will need to change the testSiteId to a site_id in your database
		int testSiteId = 46;
		List<Reservation> actualResults = rdao.getAllReservationsBySiteId(testSiteId);

		// You will need to set the expectedResultLength to the number of reservations
		// by the testSiteId
		int expectedResultsLength = 4;
		assertEquals(expectedResultsLength, actualResults.size());
	}

	@Test
	public void saveReservationsShouldInsertValuesToDatabaseAndReturnInsertedInformation() {
		Reservation testReservation = new Reservation();
		testReservation.setReservationSiteId(265);
		testReservation.setReservationName("Test Family");
		testReservation.setReservationFromDate(LocalDate.of(2019, 12, 4));
		testReservation.setReservationToDate(LocalDate.of(2019, 12, 17));
		testReservation.setReservationCreateDate(LocalDate.now());

		Reservation actualResults = rdao.saveReservation(testReservation);

		assertEquals(testReservation.getReservationName(), actualResults.getReservationName());
		assertEquals(testReservation.getReservationSiteId(), actualResults.getReservationSiteId());
		assertEquals(testReservation.getReservationFromDate(), actualResults.getReservationFromDate());
		assertEquals(testReservation.getReservationToDate(), actualResults.getReservationToDate());
		assertEquals(testReservation.getReservationCreateDate(), actualResults.getReservationCreateDate());
	}

}
