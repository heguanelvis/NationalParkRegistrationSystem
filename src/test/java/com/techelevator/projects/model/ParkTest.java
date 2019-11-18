package com.techelevator.projects.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;

public class ParkTest {

	private Park emptyPark = new Park();

	@Test
	public void parkParamConstructorShouldConstructNewPark() {
		Park filledPark = new Park(1, "Test Park", "Test Location", LocalDate.of(2013, 1, 1), 2, 345, "Fun Park");

		// TestAllGetters:
		assertEquals(1, filledPark.getParkId());
		assertEquals("Test Park", filledPark.getParkName());
		assertEquals("Test Location", filledPark.getParkLocation());
		assertEquals(LocalDate.of(2013, 1, 1), filledPark.getParkEstablishDate());
		assertEquals(2, filledPark.getParkArea());
		assertEquals(345, filledPark.getParkNumberOfVisitors());
		assertEquals("Fun Park", filledPark.getParkDescription());

	}

	@Test
	public void settersShouldsSetValuesOfInstanceCorrectly() {
		emptyPark.setParkId(1);
		emptyPark.setParkName("Test Park");
		emptyPark.setParkLocation("Test Location");
		emptyPark.setParkEstablishDate(LocalDate.of(2013, 1, 1));
		emptyPark.setParkArea(2);
		emptyPark.setParkNumberOfVisitors(345);
		emptyPark.setParkDescription("Fun Park");

		// TestAllGetters:
		assertEquals(1, emptyPark.getParkId());
		assertEquals("Test Park", emptyPark.getParkName());
		assertEquals("Test Location", emptyPark.getParkLocation());
		assertEquals(LocalDate.of(2013, 1, 1), emptyPark.getParkEstablishDate());
		assertEquals(2, emptyPark.getParkArea());
		assertEquals(345, emptyPark.getParkNumberOfVisitors());
		assertEquals("Fun Park", emptyPark.getParkDescription());
	}

}
