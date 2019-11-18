package com.techelevator.projects;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.projects.model.Campground;
import com.techelevator.projects.model.CampgroundDAO;
import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.ParkDAO;
import com.techelevator.projects.model.Reservation;
import com.techelevator.projects.model.ReservationDAO;
import com.techelevator.projects.model.Site;
import com.techelevator.projects.model.SiteDAO;
import com.techelevator.projects.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.projects.model.jdbc.JDBCParkDAO;
import com.techelevator.projects.model.jdbc.JDBCReservationDAO;
import com.techelevator.projects.model.jdbc.JDBCSiteDAO;
import com.techelevator.projects.view.Menu;

public class CampgroundCLI {

	private Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO;
	private ReservationDAO reservationDAO;
	private static final String regexMMDDYYYY = "^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$";

	private static final String SPECIFIC_PARK_MENU_OPTION_CAMPGROUNDS = "View Campgrounds";
	private static final String SPECIFIC_PARK_MENU_OPTION_RESERVATIONS = "Search for Reservation";
	private static final String SPECIFIC_PARK_MENU_OPTION_PREVIOUS_SCREEN = "Return to Previous Screen";
	private static final String[] SPECIFIC_PARK_MENU_OPTIONS = new String[] { SPECIFIC_PARK_MENU_OPTION_CAMPGROUNDS,
			SPECIFIC_PARK_MENU_OPTION_RESERVATIONS, SPECIFIC_PARK_MENU_OPTION_PREVIOUS_SCREEN };

	private static final String CAMPGROUND_MENU_OPTION_RESERVATIONS = "Search for Available Reservations";
	private static final String CAMPGROUND_MENU_OPTION_PREVIOUS_SCREEN = "Return to Previous Screen";
	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] { CAMPGROUND_MENU_OPTION_RESERVATIONS,
			CAMPGROUND_MENU_OPTION_PREVIOUS_SCREEN };

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource dataSource) {
		this.menu = new Menu(System.in, System.out);

		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
	}

	public void run() {
		displayApplicationBanner();

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(handleGetAllParkNames());

			if (choice.equals("Quit")) {
				System.out.println();
				displayFarewellBanner();
				System.exit(0);
			} else {
				int selectedParkId = handlePrintParkInfo(choice);
				handlePrintSpecificParkMenu(selectedParkId);
			}
		}
	}

	public String[] handleGetAllParkNames() {
		printHeading("View Parks Interface");

		List<Park> parks = parkDAO.getAllParks();
		List<String> parkNames = new ArrayList<>();

		for (Park park : parks) {
			parkNames.add(park.getParkName());
		}
		parkNames.add("Quit");

		return parkNames.toArray(new String[parks.size()]);
	}

	public int handlePrintParkInfo(String choice) {
		int sentenceLength = 17;
		Park selectedPark = parkDAO.searchParkByName(choice);

		printHeading("Park Information Screen");
		System.out.println(selectedPark.getParkName() + " National Park");
		System.out.println("Location:" + generateSpace("Location:", sentenceLength) + selectedPark.getParkLocation());
		System.out.println("Established:" + generateSpace("Established:", sentenceLength)
				+ formatDate(selectedPark.getParkEstablishDate()));
		System.out.println(
				"Area:" + generateSpace("Area:", sentenceLength) + formatInt(selectedPark.getParkArea()) + " sq km");
		System.out.println("Annual Visitors:" + generateSpace("Annual Visitors:", sentenceLength)
				+ formatInt(selectedPark.getParkNumberOfVisitors()));
		System.out.println();
		wordWrap(selectedPark.getParkDescription(), 60);
		System.out.println();

		return selectedPark.getParkId();
	}

	public void handlePrintParkSiteHeading(int parkId) {
		Park selectedPark = parkDAO.getParkById(parkId);
		printHeading("Search for Sites in " + selectedPark.getParkName() + " National Park");
	}

	public void handlePrintSpecificParkMenu(int selectedParkId) {
		while (true) {
			printHeading("Select a Command");
			String choice = (String) menu.getChoiceFromOptions(SPECIFIC_PARK_MENU_OPTIONS);

			if (choice.equals(SPECIFIC_PARK_MENU_OPTION_CAMPGROUNDS)) {
				handlePrintAllCampgroundsByParkId(selectedParkId);
				handlePrintCampgroundMenu(selectedParkId);
			} else if (choice.equals(SPECIFIC_PARK_MENU_OPTION_RESERVATIONS)) {
				handlePrintParkSiteHeading(selectedParkId);
				handlePrintAllSitesByParkId(selectedParkId);
			} else if (choice.equals(SPECIFIC_PARK_MENU_OPTION_PREVIOUS_SCREEN)) {
				return;
			}
		}
	}

	public List<Campground> handlePrintAllCampgroundsByParkId(int selectedParkId) {
		printHeading("Park Campgrounds");
		String parkName = parkDAO.getParkById(selectedParkId).getParkName();

		System.out.println(parkName + " National Park Campgrounds");
		System.out.println();

		List<Campground> campgrounds = campgroundDAO.getAllCampgroundsByParkId(selectedParkId);

		int blockLength = 20;
		System.out.print(generateSpace("", 6));
		System.out.print("Name" + generateSpace("Name", 35));
		System.out.print("Open" + generateSpace("Open", blockLength));
		System.out.print("Close" + generateSpace("Close", blockLength));
		System.out.print("Daily Fee" + generateSpace("DailyFee", blockLength));

		for (int i = 0; i < campgrounds.size(); i++) {
			int lineNum = i + 1;
			String campgroundName = campgrounds.get(i).getCampgroundName();
			String campgroundOpen = getMonth(Integer.parseInt(campgrounds.get(i).getCampgroundOpenFromMm()));
			String campgroundClose = getMonth(Integer.parseInt(campgrounds.get(i).getCampgroundOpenToMm()));
			String campgroundDailyFee = "$" + campgrounds.get(i).getCampgroundDailyFee();
			System.out.println();
			System.out.print(
					"#" + lineNum + generateSpace(String.valueOf(lineNum), 6 - String.valueOf(lineNum).length()));
			System.out.print(campgroundName + generateSpace(campgroundName, 35));
			System.out.print(campgroundOpen + generateSpace(campgroundOpen, blockLength));
			System.out.print(campgroundClose + generateSpace(campgroundClose, blockLength));
			System.out.print(campgroundDailyFee + generateSpace(campgroundDailyFee, blockLength));
		}

		System.out.println();
		return campgrounds;
	}

	public void handlePrintCampgroundMenu(int selectedParkId) {
		while (true) {
			printHeading("Select a Command");
			String choice = (String) menu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS);

			if (choice.equals(CAMPGROUND_MENU_OPTION_RESERVATIONS)) {
				handleSearchForCampgroundReservations(selectedParkId);
			} else if (choice.equals(CAMPGROUND_MENU_OPTION_PREVIOUS_SCREEN)) {
				return;
			}
		}
	}

	// search parkwide available sites
	public void handlePrintAllSitesByParkId(int selectedParkId) {
		Park selectedPark = parkDAO.getParkById(selectedParkId);

		try {
			System.out.println();
			System.out.println("What is the arrival date (mm/dd/yyyy)?");
			String sqlArrivalDate = (String) menu.getUserInput();

			if (!sqlArrivalDate.matches(regexMMDDYYYY)) {
				throw new ParseException("Please enter a valid date.", 0);
			}

			System.out.println();
			System.out.println("What is the departure date (mm/dd/yyyy)?");
			String sqlDepartureDate = (String) menu.getUserInput();
			if (!sqlDepartureDate.matches(regexMMDDYYYY)) {
				throw new ParseException("Please enter a valid date.", 0);
			}

			Date arrivalDate = convertStringToDate(sqlArrivalDate);
			Date departureDate = convertStringToDate(sqlDepartureDate);

			if (arrivalDate.compareTo(departureDate) > 0) {
				throw new DateTimeException("Date information entered is incorrect.");
			}

			handlePrintAvailableSites(selectedPark, arrivalDate, departureDate);
		} catch (ParseException pe) {
			System.out.println();
			System.out.println("Please enter a valid date.");
		} catch (DateTimeException de) {
			System.out.println();
			System.out.println("Note arrival date cannot be after departure date.");
		}
	}

	// search campgroundwide available sites
	public void handleSearchForCampgroundReservations(int selectedParkId) {
		List<Campground> campgrounds = handlePrintAllCampgroundsByParkId(selectedParkId);

		System.out.println();
		System.out.println("Which campground (enter 0 to cancel)?");

		try {
			int choice = Integer.parseInt((String) menu.getUserInput());
			if (choice > campgrounds.size() || choice < 0) {
				throw new NumberFormatException();
			}
			if (choice == 0) {
				return;
			}

			Campground selectedCampground = campgrounds.get(choice - 1);
			int selectedOpenDate = Integer.parseInt(selectedCampground.getCampgroundOpenFromMm());
			int selectedCloseDate = Integer.parseInt(selectedCampground.getCampgroundOpenToMm());

			System.out.println();
			System.out.println("What is the arrival date (mm/dd/yyyy)?");
			String sqlArrivalDate = (String) menu.getUserInput();
			if (!sqlArrivalDate.matches(regexMMDDYYYY)) {
				throw new ParseException("Please enter a valid date.", 0);
			}

			System.out.println();
			System.out.println("What is the departure date (mm/dd/yyyy)?");
			String sqlDepartureDate = (String) menu.getUserInput();
			if (!sqlDepartureDate.matches(regexMMDDYYYY)) {
				throw new ParseException("Please enter a valid date.", 0);
			}

			Date arrivalDate = convertStringToDate(sqlArrivalDate);
			Date departureDate = convertStringToDate(sqlDepartureDate);
			if (arrivalDate.compareTo(departureDate) > 0 || getMonthIndexFromDate(arrivalDate) < selectedOpenDate - 1
					|| getMonthIndexFromDate(departureDate) > selectedCloseDate - 1) {
				throw new DateTimeException("Date information entered is incorrect.");
			}

			handlePrintAvailableSites(selectedCampground, arrivalDate, departureDate);
			handleSearchForCampgroundReservations(selectedParkId);
		} catch (NumberFormatException ne) {
			System.out.println();
			System.out.println("Please enter a number that is listed.");
			handleSearchForCampgroundReservations(selectedParkId);
		} catch (ParseException pe) {
			System.out.println();
			System.out.println("Please enter a valid date.");
			handleSearchForCampgroundReservations(selectedParkId);
		} catch (DateTimeException de) {
			System.out.println();
			System.out.println(
					"Note arrival date cannot be after departure date or campground is not open during that time.");
			handleSearchForCampgroundReservations(selectedParkId);
		}
	}

	// parkwide available sites
	public void handlePrintAvailableSites(Park selectedPark, Date sqlArrivalDate, Date sqlDepartureDate) {
		List<Site> sites = siteDAO.searchAvailableSitesByParkId(selectedPark.getParkId(), sqlArrivalDate,
				sqlDepartureDate);

		int blockLength = 15;
		System.out.println();

		if (sites.isEmpty()) {
			System.out.println("It looks like there are no sites available for reservation.");
			System.out.println("Sorry, please try another campground or camping time.");
			return;
		}

		System.out.println("Results Matching Your Search Criteria");
		System.out.print("Campground" + generateSpace("Campground", 40) + "Site No."
				+ generateSpace("Site No.", blockLength) + "Max Occup." + generateSpace("Max Occup.", blockLength)
				+ "Accessible?" + generateSpace("Accessible?", blockLength) + "Max RV Length"
				+ generateSpace("Max RV Length", blockLength) + "Utility" + generateSpace("Utility", blockLength)
				+ "Cost" + generateSpace("Cost", blockLength));

		List<Integer> allSiteIds = new ArrayList<>();
		for (Site site : sites) {
			System.out.println();
			Campground siteCampground = campgroundDAO.getCampgroundById(site.getSiteCampgroundId());
			int siteId = site.getSiteId();
			allSiteIds.add(siteId);
			int siteMaxOccup = site.getSiteMaxOccupancy();
			String siteCampgroundName = siteCampground.getCampgroundName();
			String siteAccessible = site.isSiteAccessible() ? "Yes" : "No";
			String siteMaxRVLength = site.getSiteMaxRVLength() == 0 ? "N/A" : "" + site.getSiteMaxRVLength();
			String siteUtilities = site.isSiteUtilities() ? "Yes" : "N/A";
			Period period = Period.between(sqlDepartureDate.toLocalDate(), sqlArrivalDate.toLocalDate());
			BigDecimal daysDiff = BigDecimal.valueOf(Math.abs(period.getDays())).add(BigDecimal.valueOf(1));
			String siteCost = "$" + siteCampground.getCampgroundDailyFee().multiply(daysDiff);

			System.out.print(siteCampgroundName + generateSpace(siteCampgroundName, 40));
			System.out.print(siteId + generateSpace(String.valueOf(siteId), blockLength));
			System.out.print(siteMaxOccup + generateSpace(String.valueOf(siteMaxOccup), blockLength));
			System.out.print(siteAccessible + generateSpace(siteAccessible, blockLength));
			System.out.print(siteMaxRVLength + generateSpace(siteMaxRVLength, blockLength));
			System.out.print(siteUtilities + generateSpace(siteUtilities, blockLength));
			System.out.print(siteCost + generateSpace(siteCost, blockLength));
		}

		System.out.println();
		handlePrintReservationMenu(allSiteIds, selectedPark, sqlArrivalDate, sqlDepartureDate);
	}

	// campgroundwide available sites
	public void handlePrintAvailableSites(Campground campground, Date sqlArrivalDate, Date sqlDepartureDate) {
		List<Site> sites = siteDAO.searchAvailableSitesByCampgroundId(campground.getCampgroundId(), sqlArrivalDate,
				sqlDepartureDate);

		int blockLength = 15;
		System.out.println();

		if (sites.isEmpty()) {
			System.out.println("It looks like there are no sites available for reservation.");
			System.out.println("Sorry, please try another campground or camping time.");
			return;
		}

		System.out.println("Results Matching Your Search Criteria");
		System.out.print("Site No." + generateSpace("Site No.", blockLength) + "Max Occup."
				+ generateSpace("Max Occup.", blockLength) + "Accessible?" + generateSpace("Accessible?", blockLength)
				+ "Max RV Length" + generateSpace("Max RV Length", blockLength) + "Utility"
				+ generateSpace("Utility", blockLength) + "Cost" + generateSpace("Cost", blockLength));

		List<Integer> allSiteIds = new ArrayList<>();
		for (Site site : sites) {
			System.out.println();
			int siteId = site.getSiteId();
			allSiteIds.add(siteId);
			int siteMaxOccup = site.getSiteMaxOccupancy();
			String siteAccessible = site.isSiteAccessible() ? "Yes" : "No";
			String siteMaxRVLength = site.getSiteMaxRVLength() == 0 ? "N/A" : "" + site.getSiteMaxRVLength();
			String siteUtilities = site.isSiteUtilities() ? "Yes" : "N/A";
			Period period = Period.between(sqlDepartureDate.toLocalDate(), sqlArrivalDate.toLocalDate());
			BigDecimal daysDiff = BigDecimal.valueOf(Math.abs(period.getDays())).add(BigDecimal.valueOf(1));
			String siteCost = "$" + campground.getCampgroundDailyFee().multiply(daysDiff);

			System.out.print(siteId + generateSpace(String.valueOf(siteId), blockLength));
			System.out.print(siteMaxOccup + generateSpace(String.valueOf(siteMaxOccup), blockLength));
			System.out.print(siteAccessible + generateSpace(siteAccessible, blockLength));
			System.out.print(siteMaxRVLength + generateSpace(siteMaxRVLength, blockLength));
			System.out.print(siteUtilities + generateSpace(siteUtilities, blockLength));
			System.out.print(siteCost + generateSpace(siteCost, blockLength));
		}

		System.out.println();
		handlePrintReservationMenu(allSiteIds, campground, sqlArrivalDate, sqlDepartureDate);
	}

	// parkwide reservation
	public void handlePrintReservationMenu(List<Integer> allSiteIds, Park park, Date sqlArrivalDate,
			Date sqlDepartureDate) {
		System.out.println();
		System.out.println("Which site should be reserved (enter 0 to cancel)?");

		try {
			int choice = Integer.parseInt((String) menu.getUserInput());
			if (choice == 0) {
				return;
			}
			if (!allSiteIds.contains(choice)) {
				throw new NumberFormatException();
			}
			int selectedSiteId = choice;

			System.out.println();
			System.out.println("What name should the reservation be made under?");
			String reservationName = (String) menu.getUserInput();
			if (reservationName.isEmpty()) {
				throw new NullPointerException("Reservation name cannot be empty.");
			}

			Reservation reservation = new Reservation();
			reservation.setReservationName(reservationName);
			reservation.setReservationSiteId(selectedSiteId);
			reservation.setReservationFromDate(sqlArrivalDate.toLocalDate());
			reservation.setReservationToDate(sqlDepartureDate.toLocalDate());
			reservation.setReservationCreateDate(LocalDate.now());
			Reservation confirmedReservation = reservationDAO.saveReservation(reservation);

			if (confirmedReservation.getReservationId() > 0) {
				System.out.println();
				System.out.println("The reservation has been made and the confirmation id is {"
						+ confirmedReservation.getReservationId() + "}");
				return;
			}

			handlePrintAvailableSites(park, sqlArrivalDate, sqlDepartureDate);
		} catch (NumberFormatException ne1) {
			System.out.println();
			System.out.println("Please enter a Site No. that is listed.");
			handlePrintAvailableSites(park, sqlArrivalDate, sqlDepartureDate);
		} catch (NullPointerException ne2) {
			System.out.println();
			System.out.println("Reservation name cannot be empty.");
			handlePrintAvailableSites(park, sqlArrivalDate, sqlDepartureDate);
		}
	}

	// campgroundwide reservation
	public void handlePrintReservationMenu(List<Integer> allSiteIds, Campground campground, Date sqlArrivalDate,
			Date sqlDepartureDate) {
		System.out.println();
		System.out.println("Which site should be reserved (enter 0 to cancel)?");

		try {
			int choice = Integer.parseInt((String) menu.getUserInput());
			if (choice == 0) {
				return;
			}
			if (!allSiteIds.contains(choice)) {
				throw new NumberFormatException();
			}
			int selectedSiteId = choice;

			System.out.println();
			System.out.println("What name should the reservation be made under?");
			String reservationName = (String) menu.getUserInput();
			if (reservationName.isEmpty()) {
				throw new NullPointerException("Reservation name cannot be empty.");
			}

			Reservation reservation = new Reservation();
			reservation.setReservationName(reservationName);
			reservation.setReservationSiteId(selectedSiteId);
			reservation.setReservationFromDate(sqlArrivalDate.toLocalDate());
			reservation.setReservationToDate(sqlDepartureDate.toLocalDate());
			reservation.setReservationCreateDate(LocalDate.now());
			Reservation confirmedReservation = reservationDAO.saveReservation(reservation);

			if (confirmedReservation.getReservationId() > 0) {
				System.out.println();
				System.out.println("The reservation has been made and the confirmation id is {"
						+ confirmedReservation.getReservationId() + "}");
				return;
			}

			handlePrintAvailableSites(campground, sqlArrivalDate, sqlDepartureDate);
		} catch (NumberFormatException ne1) {
			System.out.println();
			System.out.println("Please enter a Site No. that is listed.");
			handlePrintAvailableSites(campground, sqlArrivalDate, sqlDepartureDate);
		} catch (NullPointerException ne2) {
			System.out.println();
			System.out.println("Reservation name cannot be empty.");
			handlePrintAvailableSites(campground, sqlArrivalDate, sqlDepartureDate);
		}
	}

	/* All helper methods: */
	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);

		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}

		System.out.println();
	}

	private static String generateSpace(String str, int totalSentenceLength) {
		int spaceLength = totalSentenceLength - str.length();
		String space = "";

		for (int i = 1; i <= spaceLength; i++) {
			space += " ";
		}

		return space;
	}

	private static String formatDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
		return formatter.format(date);
	}

	private static String formatInt(int num) {
		return String.format("%,d", num);
	}

	private static String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month - 1];
	}

	private static Date convertStringToDate(String str) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDateObj = LocalDate.parse(str, formatter);
		return Date.valueOf(localDateObj);
	}

	public static void wordWrap(String str, int endPoint) {
		String[] sentenceParts = str.split(" ");
		int lengthTracker = 0;
		for (String sentencePart : sentenceParts) {
			System.out.print(sentencePart + " ");
			lengthTracker += sentencePart.length() + 1;
			if (lengthTracker >= endPoint) {
				System.out.println();
				lengthTracker = 0;
			}
		}
	}

	private static int getMonthIndexFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int monthI = cal.get(Calendar.MONTH);
		return monthI;
	}

	private void displayApplicationBanner() {
		System.out.println(
				"     c  c            \\\\\\    ///    ))          \\/       ))         .-.      wWw  wWw   \\\\\\  ///       _  \r\n"
						+ "     (OO)      /)    ((O)  (O))   (o0)-.      (OO)     (Oo)-.    c(O_O)c    (O)  (O)   ((O)(O))     _||\\ \r\n"
						+ "   ,'.--.)   (o)(O)   | \\  / |     | (_))   ,'.--.)     | (_))  ,'.---.`,   / )  ( \\    | \\ ||     (_'\\  \r\n"
						+ "  / //_|_\\    //\\\\    ||\\\\//||     | .-'   / /|_|_\\     |  .'  / /|_|_|\\ \\ / /    \\ \\   ||\\\\||     .'  | \r\n"
						+ "  | \\___     |(__)|   || \\/ ||     |(      | \\_.--.     )|\\\\   | \\_____/ | | \\____/ |   || \\ |    ((_) | \r\n"
						+ "  '.    )    /,-. |   ||    ||      \\)     '.   \\) \\   (/  \\)  '. `---' .` '. `--' .`   ||  ||     `-`.) \r\n"
						+ "    `-.'    -'   ''  (_/    \\_)     (        `-.(_.'    )        `-...-'     `-..-'    (_/  \\_)       (  ");
	}

	private void displayFarewellBanner() {
		System.out.println(",-----.                     \r\n" + "|  |) /_  ,--. ,--.  ,---.  \r\n"
				+ "|  .-.  \\  \\  '  /  | .-. : \r\n" + "|  '--' /   \\   '   \\   --. \r\n"
				+ "`------'  .-'  /     `----' \r\n" + "          `---'             ");
	}

}
