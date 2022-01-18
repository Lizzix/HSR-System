package com.esoe.dao;

import com.esoe.enums.StationName;
import com.esoe.model.Trip;
import com.esoe.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    public List<Trip> list() {
        String sql = "SELECT * FROM trip";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Trip> list(String date) {
        String sql = "SELECT * FROM trip WHERE effective_date <= ?";
        return jdbcTemplate.query(sql, rowMapper, date);
    }

    public List<Trip> list(StationName start, StationName dest, String date, short train_id) {
        String serveDay = "serve_" + Util.getDayOfWeek(Util.stringToDate(date)).getAbbreviation();
        String time = Util.getCurrentTimeStr();
        String departTime = "depart_time_" + start.getName_En();
        String sql;
        if (Util.goSouth(start, dest)) {
            sql = "SELECT * FROM Trip INNER JOIN TripSchedule ON Trip.id = TripSchedule.trip_id WHERE " + serveDay + " = TRUE AND start_station_id <= ? AND dest_station_id >= ? AND effective_date <= ? AND train_id = ? " + departTime + " >= ?";
        } else {
            sql = "SELECT * FROM Trip INNER JOIN TripSchedule ON Trip.id = TripSchedule.trip_id WHERE " + serveDay + " = TRUE AND start_station_id >= ? AND dest_station_id <= ? AND effective_date <= ? AND train_id = ? " + departTime + " >= ?";
        }
        return jdbcTemplate.query(sql, rowMapper, start.getCode(), dest.getCode(), date, train_id, time);
    }

    public List<Trip> list(StationName start, StationName dest, String date, String time) {
        String serveDay = "serve_" + Objects.requireNonNull(Util.getDayOfWeek(Util.stringToDate(date)).getAbbreviation());
        String departTime = "depart_time_" + start.getName_En();
        String sql;
        if (Util.goSouth(start, dest)) {
            sql = "SELECT * FROM Trip INNER JOIN TripSchedule ON Trip.id = TripSchedule.trip_id WHERE " + serveDay + " = TRUE AND start_station_id <= ? AND dest_station_id >= ? AND effective_date <= ? AND " + departTime + " >= ?";
        } else {
            sql = "SELECT * FROM Trip INNER JOIN TripSchedule ON Trip.id = TripSchedule.trip_id WHERE " + serveDay + " = TRUE AND start_station_id >= ? AND dest_station_id <= ? AND effective_date <= ? AND " + departTime + " >= ?";
        }
        return jdbcTemplate.query(sql, rowMapper, start.getCode(), dest.getCode(), date, time);
    }

    public List<Trip> list(String trainIDs, StationName start, StationName dest, Date date) {
        String serveDay = "serve_" + Util.getDayOfWeek(date).getAbbreviation();
        String sql;
        if (Util.goSouth(start, dest)) {
            sql = "SELECT * FROM trip WHERE " + serveDay + " = TRUE AND start_station_id <= ? AND dest_station_id >= ? AND effective_date <= ? AND train_id IN " + trainIDs;
        } else {
            sql = "SELECT * FROM trip WHERE " + serveDay + " = TRUE AND start_station_id >= ? AND dest_station_id <= ? AND effective_date <= ? AND train_id IN " + trainIDs;
        }
        return jdbcTemplate.query(sql, rowMapper, start, dest, date);
    }

    @Override
    public int create(Trip trip) {
        String sql = "INSERT INTO trip (train_id, start_station_id, dest_station_id, direction, update_date, effective_date, serve_mon, serve_tue, serve_wed, serve_thu, serve_fri, serve_sat, serve_sun) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, trip.getTrainID(), trip.getStartStationID(), trip.getDestStationID(), trip.getDirection(), trip.getUpdateDate(), trip.getEffectiveDate(), trip.getServeMonday(), trip.getServeTuesday(), trip.getServeWednesday(), trip.getServeThursday(), trip.getServeFriday(), trip.getServeSaturday(), trip.getServeSunday());
    }

    @Override
    public Optional<Trip> get(int id) {
        String sql = "SELECT * FROM trip WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public int update(Trip trip, int id) {
        String sql = "UPDATE trip SET train_id = ?, start_station_id = ?, dest_station_id = ?, direction = ?, update_date = ?, effective_date = ?, serve_mon = ?, serve_tue = ?, serve_wed = ?, serve_thu = ?, serve_fri = ?, serve_sat = ?, serve_sun = ? WHERE id = ?";
        return jdbcTemplate.update(sql, trip.getTrainID(), trip.getStartStationID(), trip.getDestStationID(), trip.getDirection(), trip.getUpdateDate(), trip.getEffectiveDate(), trip.getServeMonday(), trip.getServeTuesday(), trip.getServeWednesday(), trip.getServeThursday(), trip.getServeFriday(), trip.getServeSaturday(), trip.getServeSunday(), id);
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM trip WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
