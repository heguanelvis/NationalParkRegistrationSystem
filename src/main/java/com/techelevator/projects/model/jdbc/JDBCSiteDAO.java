package com.techelevator.projects.model.jdbc;

import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Site;
import com.techelevator.projects.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getAllSitesByCampgroundId(int campgroundId) {
		String sqlQuery = "SELECT * FROM site WHERE campground_id = ?";

		return jdbcTemplate.query(sqlQuery,
				(rs, rowNum) -> new Site(rs.getInt("site_id"), rs.getInt("campground_id"), rs.getInt("site_number"),
						rs.getInt("max_occupancy"), rs.getBoolean("accessible"), rs.getInt("max_rv_length"),
						rs.getBoolean("utilities")),
				campgroundId);
	}

	@Override
	public List<Site> searchAvailableSitesByCampgroundId(int campgroundId, Date sqlArrivalDate, Date sqlDepartureDate) {
		String sqlQuery = "SELECT avail_sites.* FROM (SELECT * FROM site\r\n" + "WHERE campground_id = ? \r\n"
				+ "AND site_id NOT IN (SELECT DISTINCT site.site_id\r\n" + "FROM site LEFT JOIN reservation\r\n"
				+ "ON site.site_id = reservation.site_id \r\n"
				+ "WHERE (reservation.from_date <= ? AND reservation.to_date >= ?) \r\n"
				+ "AND site.campground_id = ?)) avail_sites \r\n" + "INNER JOIN (SELECT site.site_id, \r\n"
				+ "COUNT(reservation.reservation_id) popularity FROM site\r\n" + "LEFT JOIN reservation \r\n"
				+ "ON site.site_id = reservation.site_id \r\n" + "GROUP BY site.site_id\r\n"
				+ "ORDER BY popularity DESC) pop_sites\r\n" + "ON avail_sites.site_id = pop_sites.site_id\r\n"
				+ "ORDER BY popularity DESC, avail_sites.site_id\r\n" + "LIMIT 5";

		return jdbcTemplate.query(sqlQuery,
				(rs, rowNum) -> new Site(rs.getInt("site_id"), rs.getInt("campground_id"), rs.getInt("site_number"),
						rs.getInt("max_occupancy"), rs.getBoolean("accessible"), rs.getInt("max_rv_length"),
						rs.getBoolean("utilities")),
				campgroundId, sqlDepartureDate, sqlArrivalDate, campgroundId);
	}

	@Override
	public List<Site> searchAvailableSitesByParkId(int parkId, Date sqlArrivalDate, Date sqlDepartureDate) {
		String sqlQuery = "SELECT park_avail.* FROM (SELECT avail_sites.*, popularity FROM (SELECT site.* FROM site \r\n"
				+ "INNER JOIN campground \r\n" + "ON campground.campground_id = site.campground_id \r\n"
				+ "WHERE campground.park_id = ? AND \r\n" + "site.site_id NOT IN (SELECT DISTINCT site.site_id\r\n"
				+ "FROM site LEFT JOIN reservation\r\n" + "ON site.site_id = reservation.site_id \r\n"
				+ "WHERE (reservation.from_date <= ? \r\n" + "AND reservation.to_date >= ?))) avail_sites \r\n"
				+ "INNER JOIN (SELECT site.site_id, \r\n" + "COUNT(reservation.reservation_id) popularity FROM site\r\n"
				+ "LEFT JOIN reservation \r\n" + "ON site.site_id = reservation.site_id \r\n"
				+ "GROUP BY site.site_id\r\n" + "ORDER BY popularity DESC) pop_sites\r\n"
				+ "ON avail_sites.site_id = pop_sites.site_id) park_avail\r\n" + "INNER JOIN campground\r\n"
				+ "ON campground.campground_id = park_avail.campground_id\r\n"
				+ "WHERE open_from_mm::INTEGER <= ? AND open_to_mm::INTEGER >= ?\r\n"
				+ "ORDER BY popularity DESC, park_avail.site_id\r\n" + "LIMIT 5;";

		return jdbcTemplate.query(sqlQuery,
				(rs, rowNum) -> new Site(rs.getInt("site_id"), rs.getInt("campground_id"), rs.getInt("site_number"),
						rs.getInt("max_occupancy"), rs.getBoolean("accessible"), rs.getInt("max_rv_length"),
						rs.getBoolean("utilities")),
				parkId, sqlDepartureDate, sqlArrivalDate, sqlArrivalDate.toLocalDate().getMonthValue(),
				sqlDepartureDate.toLocalDate().getMonthValue());
	}

}
