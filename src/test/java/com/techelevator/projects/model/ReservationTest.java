package com.techelevator.projects.model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class ReservationTest {

	private Reservation emptyReservation = new Reservation();

	@Test
	public void campgroundParamConstructorShouldConstructNewCampground() {
		Reservation filledReservation = new Reservation(1, 2, "Test Reservation", LocalDate.of(2010, 1, 2),
				LocalDate.of(2010, 1, 20), LocalDate.of(2009, 12, 02));

		// TestAllGetters:
		assertEquals(1, filledReservation.getReservationId());
		assertEquals(2, filledReservation.getReservationSiteId());
		assertEquals("Test Reservation", filledReservation.getReservationName());
		assertEquals(LocalDate.of(2010, 1, 2), filledReservation.getReservationFromDate());
		assertEquals(LocalDate.of(2010, 1, 20), filledReservation.getReservationToDate());
		assertEquals(LocalDate.of(2009, 12, 02), filledReservation.getReservationCreateDate());
	}

	@Test
	public void settersShouldsSetValuesOfInstanceCorrectly() {
		emptyReservation.setReservationId(1);
		emptyReservation.setReservationSiteId(2);
		emptyReservation.setReservationName("Test Reservation");
		emptyReservation.setReservationFromDate(LocalDate.of(2010, 1, 2));
		emptyReservation.setReservationToDate(LocalDate.of(2010, 1, 20));
		emptyReservation.setReservationCreateDate(LocalDate.of(2009, 12, 02));

		// TestAllGetters:
		assertEquals(1, emptyReservation.getReservationId());
		assertEquals(2, emptyReservation.getReservationSiteId());
		assertEquals("Test Reservation", emptyReservation.getReservationName());
		assertEquals(LocalDate.of(2010, 1, 2), emptyReservation.getReservationFromDate());
		assertEquals(LocalDate.of(2010, 1, 20), emptyReservation.getReservationToDate());
		assertEquals(LocalDate.of(2009, 12, 02), emptyReservation.getReservationCreateDate());
	}

}
