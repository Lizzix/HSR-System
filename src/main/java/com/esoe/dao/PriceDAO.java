package com.esoe.dao;

import com.esoe.enums.StationName;
import com.esoe.enums.TicketType;
import com.esoe.model.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class PriceDAO extends DAO<Price> {

    RowMapper<Price> rowMapper = (rs, rowNum) -> {
        Price price = new Price();
        price.setId(rs.getInt("id"));
        price.setStartStationID(rs.getShort("start_station_id"));
        price.setDestStationID(rs.getShort("dest_station_id"));
        price.setTicketType(TicketType.valueOf(rs.getString("ticket_type")));
        price.setPrice(rs.getShort("price"));
        return price;
    };


    @Override
    public List<Price> list() {
        String sql = "SELECT * FROM price";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int create(Price price) {
        String sql = "INSERT INTO price (start_station_id, dest_station_id, ticket_type, price) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, price.getStartStationID(), price.getDestStationID(), price.getTicketType().toString(), price.getPrice());
    }

    @Override
    public Optional<Price> get(int id) {
        String sql = "SELECT * FROM price WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public Optional<Price> get(TicketType ticketType) {
        String sql = "SELECT * FROM price WHERE ticket_type = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, ticketType.toString()));
    }

    public Optional<Price> get(StationName start, StationName dest, TicketType ticketType) {
        String sql = "SELECT * FROM price WHERE start_station_id = ? AND dest_station_id = ? AND ticket_type = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, start.getCode(), dest.getCode(), ticketType.toString()));
    }
    public Optional<Price> get(StationName start, StationName dest, String ticketType) {
        String sql = "SELECT * FROM price WHERE start_station_id = ? AND dest_station_id = ? AND ticket_type = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, start.getCode(), dest.getCode(), ticketType));
    }

    @Override
    public int update(Price price, int id) {
        String sql = "UPDATE price SET start_station_id = ?, dest_station_id = ?, ticket_type = ?, price = ? WHERE id = ?";
        return jdbcTemplate.update(sql, price.getStartStationID(), price.getDestStationID(), price.getTicketType().toString(), price.getPrice(), id);
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM price WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
