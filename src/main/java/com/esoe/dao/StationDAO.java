package com.esoe.dao;

import com.esoe.model.Station;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class StationDAO extends DAO<Station> {

    RowMapper<Station> rowMapper = (rs, rowNum) -> {
        Station station = new Station();
        station.setId(rs.getInt("id"));
        station.setNameEn(rs.getString("name_En"));
        station.setNameZh_tw(rs.getString("name_Zh_tw"));
        station.setAddress(rs.getString("address"));
        return station;
    };

    @Override
    public List<Station> list() {
        String sql = "SELECT id, name_En, name_Zh_tw, address FROM Station;";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int create(Station station) {
        String sql = "INSERT INTO Station (id ,name_En, name_Zh_tw, address) VALUES (?, ?, ?, ?);";
        return jdbcTemplate.update(sql, station.getId(), station.getNameEn(), station.getNameZh_tw(), station.getAddress());
    }

    @Override
    public Optional<Station> get(int id) {
        String sql = "SELECT id, name_En, name_Zh_tw, address FROM Station WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public Optional<Station> getByNameZh_tw(String nameZh_tw) {
        String sql = "SELECT id, name_En, name_Zh_tw, address FROM Station WHERE name_Zh_tw = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, nameZh_tw));
    }

    @Override
    public int update(Station station, int id) {
        String sql = "UPDATE Station SET name_En = ?, name_Zh_tw = ?, address = ? WHERE id = ?;";
        return jdbcTemplate.update(sql, station.getNameEn(), station.getNameZh_tw(), station.getAddress(), id);
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM Station WHERE id = ?;";
        return jdbcTemplate.update(sql, id);
    }
}
