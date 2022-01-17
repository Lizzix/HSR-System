package com.esoe.dao;

import com.esoe.enums.SeatType;
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
        reservedSeat.setSeatType(SeatType.valueOf(rs.getString("seat_type")));
        return reservedSeat;
    };

    @Override
    public List<ReservedSeat> list() {
        String sql = "SELECT * FROM ReservedSeat";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int create(ReservedSeat reservedSeat) {
        String sql = "INSERT INTO ReservedSeat (ticket_id, station_id, date, car, row, col, seat_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, reservedSeat.getTicketID(), reservedSeat.getStationID(), reservedSeat.getDate(), reservedSeat.getCar(), reservedSeat.getRow(), reservedSeat.getCol(), reservedSeat.getSeatType().toString());
    }

    @Override
    public Optional<ReservedSeat> get(int id) {
        String sql = "SELECT * FROM ReservedSeat WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public Optional<ReservedSeat> getBySeatCode(int car, int row, char col) {
        String sql = "SELECT * FROM ReservedSeat WHERE car = ? AND row = ? AND col = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, car, row, col));
    }

    @Override
    public int update(ReservedSeat reservedSeat, int id) {
        String sql = "UPDATE ReservedSeat SET ticket_id = ?, station_id = ?, date = ?, car = ?, row = ?, col = ?, seat_type = ? WHERE id = ?";
        return jdbcTemplate.update(sql, reservedSeat.getTicketID(), reservedSeat.getStationID(), reservedSeat.getDate(), reservedSeat.getCar(), reservedSeat.getRow(), reservedSeat.getCol(), reservedSeat.getSeatType().toString(), id);
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM ReservedSeat WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
