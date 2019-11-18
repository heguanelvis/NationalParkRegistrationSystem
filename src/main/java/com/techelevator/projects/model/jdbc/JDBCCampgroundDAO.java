package com.techelevator.projects.model.jdbc;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Campground;
import com.techelevator.projects.model.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getAllCampgroundsByParkId(int parkId) {
		String sqlQuery = "SELECT * FROM campground WHERE park_id = ?";

		return jdbcTemplate.query(sqlQuery,
				(rs, rowNum) -> new Campground(rs.getInt("campground_id"), rs.getInt("park_id"), rs.getString("name"),
						rs.getString("open_from_mm"), rs.getString("open_to_mm"),
						BigDecimal.valueOf(rs.getDouble("daily_fee")).setScale(2)),
				parkId);
	}

	@Override
	public Campground getCampgroundById(int campgroundId) {
		String sqlQuery = "SELECT * FROM campground WHERE campground_id = ?";
		
		List<Campground> selectedCampgrounds = jdbcTemplate.query(sqlQuery,
				(rs, rowNum) -> new Campground(rs.getInt("campground_id"), rs.getInt("park_id"), rs.getString("name"),
						rs.getString("open_from_mm"), rs.getString("open_to_mm"),
						BigDecimal.valueOf(rs.getDouble("daily_fee")).setScale(2)),
				campgroundId);
		
		return selectedCampgrounds.get(0);
	}

}
