package com.esoe.dao;

import com.esoe.enums.DayOfWeek;
import com.esoe.model.Trip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TripDAO extends DAO<Trip> {
    RowMapper<Trip> rowMapper = (rs, rowNum) -> {
        Trip trip = new Trip();
        trip.setId(rs.getInt("id"));
        trip.setTrainID(rs.getInt("train_id"));
        trip.setStartStationID(rs.getShort("start_station_id"));
        trip.setDestStationID(rs.getShort("dest_station_id"));
        trip.setDirection(rs.getByte("direction"));
        trip.setUpdateDate(rs.getDate("update_date"));
        trip.setEffectiveDate(rs.getDate("effective_date"));
        trip.setServeMonday(rs.getBoolean("serve_mon"));
        trip.setServeTuesday(rs.getBoolean("serve_tue"));
        trip.setServeWednesday(rs.getBoolean("serve_wed"));
        trip.setServeThursday(rs.getBoolean("serve_thu"));
        trip.setServeFriday(rs.getBoolean("serve_fri"));
        trip.setServeSaturday(rs.getBoolean("serve_sat"));
        trip.setServeSunday(rs.getBoolean("serve_sun"));
        return trip;
    };

    @Override
    List<Trip> list() {
        String sql = "SELECT * FROM trip";
        return jdbcTemplate.query(sql, rowMapper);
    }

    List<Trip> listByServeDay(DayOfWeek dayOfWeek) {
        String serveDay = "serve_" + dayOfWeek.getAbbreviation();
        String sql = "SELECT * FROM trip WHERE " + serveDay + " = true";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    int create(Trip trip) {
        String sql = "INSERT INTO trip (train_id, start_station_id, dest_station_id, direction, update_date, effective_date, serve_mon, serve_tue, serve_wed, serve_thu, serve_fri, serve_sat, serve_sun) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, trip.getTrainID(), trip.getStartStationID(), trip.getDestStationID(), trip.getDirection(), trip.getUpdateDate(), trip.getEffectiveDate(), trip.getServeMonday(), trip.getServeTuesday(), trip.getServeWednesday(), trip.getServeThursday(), trip.getServeFriday(), trip.getServeSaturday(), trip.getServeSunday());
    }

    @Override
    Optional<Trip> get(int id) {
        String sql = "SELECT * FROM trip WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    int update(Trip trip, int id) {
        String sql = "UPDATE trip SET train_id = ?, start_station_id = ?, dest_station_id = ?, direction = ?, update_date = ?, effective_date = ?, serve_mon = ?, serve_tue = ?, serve_wed = ?, serve_thu = ?, serve_fri = ?, serve_sat = ?, serve_sun = ? WHERE id = ?";
        return jdbcTemplate.update(sql, trip.getTrainID(), trip.getStartStationID(), trip.getDestStationID(), trip.getDirection(), trip.getUpdateDate(), trip.getEffectiveDate(), trip.getServeMonday(), trip.getServeTuesday(), trip.getServeWednesday(), trip.getServeThursday(), trip.getServeFriday(), trip.getServeSaturday(), trip.getServeSunday(), id);
    }

    @Override
    int delete(int id) {
        String sql = "DELETE FROM trip WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
