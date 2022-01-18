package com.esoe.dao;

import com.esoe.enums.StationName;
import com.esoe.model.ReservedSeat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ReservedSeatDAO extends DAO<ReservedSeat> {

    RowMapper<ReservedSeat> rowMapper = (rs, rowNum) -> {
        ReservedSeat reservedSeat = new ReservedSeat();
        reservedSeat.setId(rs.getInt("id"));
        reservedSeat.setTicketID(rs.getInt("ticket_id"));
        reservedSeat.setStationID(rs.getShort("station_id"));
        reservedSeat.setDate(rs.getDate("date"));
        reservedSeat.setCar(rs.getByte("car"));
        reservedSeat.setRow(rs.getByte("row"));
        return reservedSeat;
    };

    @Override
    public List<ReservedSeat> list() {
        String sql = "SELECT * FROM ReservedSeat";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<ReservedSeat> list(int car, int row, String col, StationName start, StationName dest, String date) {
        String sql;
        if (start.getCode() < dest.getCode()) {
            sql = "SELECT * FROM ReservedSeat WHERE car = ? AND row = ? AND col = ? AND station_id >= ? AND station_id <= ? AND date = ?";
        }
        else {
            sql = "SELECT * FROM ReservedSeat WHERE car = ? AND row = ? AND col = ? AND station_id <= ? AND station_id >= ? AND date = ?";
        }
        return (jdbcTemplate.query(sql, rowMapper, car, row, col, date));
    }

    @Override
    public int create(ReservedSeat reservedSeat) {
        String sql = "INSERT INTO ReservedSeat (ticket_id, station_id, date, car, row, col) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, reservedSeat.getTicketID(), reservedSeat.getStationID(), reservedSeat.getDate(), reservedSeat.getCar(), reservedSeat.getRow(), reservedSeat.getCol());
    }

    @Override
    public Optional<ReservedSeat> get(int id) {
        String sql = "SELECT * FROM ReservedSeat WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public int update(ReservedSeat reservedSeat, int id) {
        String sql = "UPDATE ReservedSeat SET ticket_id = ?, station_id = ?, date = ?, car = ?, row = ?, col = ? WHERE id = ?";
        return jdbcTemplate.update(sql, reservedSeat.getTicketID(), reservedSeat.getStationID(), reservedSeat.getDate(), reservedSeat.getCar(), reservedSeat.getRow(), reservedSeat.getCol(), id);
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM ReservedSeat WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
