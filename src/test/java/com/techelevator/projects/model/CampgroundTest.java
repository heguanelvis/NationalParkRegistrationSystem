package com.techelevator.projects.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class CampgroundTest {

	private Campground emptyCampground = new Campground();

	@Test
	public void campgroundParamConstructorShouldConstructNewCampground() {
		Campground filledCampground = new Campground(1, 2, "Test Campground", "01", "09", BigDecimal.valueOf(100));

		// TestAllGetters:
		assertEquals(1, filledCampground.getCampgroundId());
		assertEquals(2, filledCampground.getCampgroundParkId());
		assertEquals("Test Campground", filledCampground.getCampgroundName());
		assertEquals("01", filledCampground.getCampgroundOpenFromMm());
		assertEquals("09", filledCampground.getCampgroundOpenToMm());
		assertEquals(BigDecimal.valueOf(100), filledCampground.getCampgroundDailyFee());
	}

	@Test
	public void settersShouldsSetValuesOfInstanceCorrectly() {
		emptyCampground.setCampgroundId(1);
		emptyCampground.setCampgroundParkId(2);
		emptyCampground.setCampgroundName("Test Campground");
		emptyCampground.setCampgroundOpenFromMm("01");
		emptyCampground.setCampgroundOpenToMm("09");
		emptyCampground.setCampgroundDailyFee(BigDecimal.valueOf(100));

		// TestAllGetters:
		assertEquals(1, emptyCampground.getCampgroundId());
		assertEquals(2, emptyCampground.getCampgroundParkId());
		assertEquals("Test Campground", emptyCampground.getCampgroundName());
		assertEquals("01", emptyCampground.getCampgroundOpenFromMm());
		assertEquals("09", emptyCampground.getCampgroundOpenToMm());
		assertEquals(BigDecimal.valueOf(100), emptyCampground.getCampgroundDailyFee());
	}

}
