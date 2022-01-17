package com.esoe.dao;

import com.esoe.enums.DiscountType;
import com.esoe.enums.SeatType;
import com.esoe.model.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TicketDAO extends DAO<Ticket> {

    RowMapper<Ticket> rowMapper = (rs, rowNum) -> {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("id"));
        ticket.setOrderID(rs.getInt("order_id"));
        ticket.setDiscountType(DiscountType.valueOf(rs.getString("discount_type")));
        ticket.setDate(rs.getDate("date"));
        ticket.setStartStationID(rs.getShort("start_station_id"));
        ticket.setDestStationID(rs.getShort("dest_station_id"));
        ticket.setRoundTrip(rs.getBoolean("round_trip"));
        ticket.setSeatType(SeatType.valueOf(rs.getString("seat_type")));
        ticket.setSeatCode(rs.getString("seat_code"));
        return ticket;
    };


    @Override
    public List<Ticket> list() {
        String sql = "SELECT * FROM ticket";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int create(Ticket ticket) {
        String sql = "INSERT INTO ticket (order_id, discount_type, date, start_station_id, dest_station_id, round_trip, seat_type, seat_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, ticket.getOrderID(), ticket.getDiscountType().toString(), ticket.getDate(), ticket.getStartStationID(), ticket.getDestStationID(), ticket.getRoundTrip(), ticket.getSeatType().toString(), ticket.getSeatCode());
    }

    @Override
    public Optional<Ticket> get(int id) {
        String sql = "SELECT * FROM ticket WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public Optional<Ticket> getByOrderID(int orderID) {
        String sql = "SELECT * FROM ticket WHERE order_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, orderID));
    }

    @Override
    public int update(Ticket ticket, int id) {
        String sql = "UPDATE ticket SET order_id = ?, discount_type = ?, date = ?, start_station_id = ?, dest_station_id = ?, round_trip = ?, seat_type = ?, seat_code = ? WHERE id = ?";
        return jdbcTemplate.update(sql, ticket.getOrderID(), ticket.getDiscountType().toString(), ticket.getDate(), ticket.getStartStationID(), ticket.getDestStationID(), ticket.getRoundTrip(), ticket.getSeatType().toString(), ticket.getSeatCode(), id);
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM ticket WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
