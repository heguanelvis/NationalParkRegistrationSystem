package com.techelevator.projects.model.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Reservation;
import com.techelevator.projects.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Reservation> getAllReservationsBySiteId(int siteId) {
		String sqlQuery = "SELECT * FROM reservation WHERE site_id = ?";

		return jdbcTemplate.query(sqlQuery,
				(rs, rowNum) -> new Reservation(rs.getInt("reservation_id"), rs.getInt("site_id"), rs.getString("name"),
						rs.getDate("from_date").toLocalDate(), rs.getDate("to_date").toLocalDate(),
						rs.getDate("create_date").toLocalDate()),
				siteId);
	}

	@Override
	public Reservation saveReservation(Reservation reservation) {
		String sqlQuery = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) "
				+ "VALUES (?, ?, ?, ?, ?) RETURNING reservation_id";

		int reservationId = jdbcTemplate.queryForObject(sqlQuery, Integer.class, reservation.getReservationSiteId(),
				reservation.getReservationName(), reservation.getReservationFromDate(),
				reservation.getReservationToDate(), reservation.getReservationCreateDate());

		reservation.setReservationId(reservationId);

		return reservation;
	}

}