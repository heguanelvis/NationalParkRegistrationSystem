package com.techelevator.projects.model.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		String sqlQuery = "SELECT * FROM park ORDER BY name";

		return jdbcTemplate.query(sqlQuery,
				(rs, rowNum) -> new Park(rs.getInt("park_id"), rs.getString("name"), rs.getString("location"),
						rs.getDate("establish_date").toLocalDate(), rs.getInt("area"), rs.getInt("visitors"),
						rs.getString("description")));
	}

	@Override
	public Park searchParkByName(String parkName) {
		String sqlQuery = "SELECT * FROM park WHERE name = ?";

		List<Park> selectedParks = jdbcTemplate.query(sqlQuery,
				(rs, rowNum) -> new Park(rs.getInt("park_id"), rs.getString("name"), rs.getString("location"),
						rs.getDate("establish_date").toLocalDate(), rs.getInt("area"), rs.getInt("visitors"),
						rs.getString("description")),
				parkName);

		return selectedParks.get(0);
	}

	@Override
	public Park getParkById(int parkId) {
		String sqlQuery = "SELECT * FROM park WHERE park_id = ?";

		List<Park> selectedParks = jdbcTemplate.query(sqlQuery,
				(rs, rowNum) -> new Park(rs.getInt("park_id"), rs.getString("name"), rs.getString("location"),
						rs.getDate("establish_date").toLocalDate(), rs.getInt("area"), rs.getInt("visitors"),
						rs.getString("description")),
				parkId);

		return selectedParks.get(0);
	}

}
