package com.esoe.dao;

import com.esoe.model.Station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class StationDAO extends DAO<Station>  {

    RowMapper<Station> rowMapper = (resultSet, rowNum) -> {
        Station station = new Station();
        station.setId(resultSet.getInt("id"));
        station.setNameEn(resultSet.getString("name_En"));
        station.setNameZh_tw(resultSet.getString("name_Zh_tw"));
        station.setAddress(resultSet.getString("address"));
        return station;
    };

    @Override
    public List<Station> list() {
        String sql = "SELECT id, name_En, name_Zh_tw, address FROM Station;";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(Station station) {

    }

    @Override
    public Optional<Station> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Station station, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
