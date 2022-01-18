package com.esoe.dao;

import com.esoe.enums.StationName;
import com.esoe.model.TripSchedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TripScheduleDAO extends DAO<TripSchedule> {

    RowMapper<TripSchedule> rowMapper = (rs, rowNum) -> {
        TripSchedule tripSchedule = new TripSchedule();
        tripSchedule.setId(rs.getInt("id"));
        tripSchedule.setDepartNangang(rs.getTime("depart_time_Nangang"));
        tripSchedule.setDepartTaipei(rs.getTime("depart_time_Taipei"));
        tripSchedule.setDepartBanciao(rs.getTime("depart_time_Banciao"));
        tripSchedule.setDepartTaoyuan(rs.getTime("depart_time_Taoyuan"));
        tripSchedule.setDepartHsinchu(rs.getTime("depart_time_Hsinchu"));
        tripSchedule.setDepartMiaoli(rs.getTime("depart_time_Miaoli"));
        tripSchedule.setDepartTaichung(rs.getTime("depart_time_Taichung"));
        tripSchedule.setDepartChanghua(rs.getTime("depart_time_Changhua"));
        tripSchedule.setDepartYunlin(rs.getTime("depart_time_Yunlin"));
        tripSchedule.setDepartChiayi(rs.getTime("depart_time_Chiayi"));
        tripSchedule.setDepartTainan(rs.getTime("depart_time_Tainan"));
        tripSchedule.setDepartZuoying(rs.getTime("depart_time_Zuoying"));
        return tripSchedule;
    };

    @Override
    public List<TripSchedule> list() {
        String sql = "SELECT * FROM TripSchedule";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<TripSchedule> list(StationName stationName) {
        String departStationTime = "depart_time_" + stationName;
        String sql = "SELECT * FROM TripSchedule WHERE " + departStationTime + " IS NOT NULL";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<TripSchedule> list(StationName stationName, Time departAfterTime) {
        String departStationTime = "depart_time_" + stationName;
        String sql = "SELECT * FROM TripSchedule WHERE " + departStationTime + ">= '" + departAfterTime + "'";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int create(TripSchedule tripSchedule) {
        String sql = "INSERT INTO TripSchedule (depart_time_Nangang, depart_time_Taipei, depart_time_Banciao, depart_time_Taoyuan, depart_time_Hsinchu, depart_time_Miaoli, depart_time_Taichung, depart_time_Changhua, depart_time_Yunlin, depart_time_Chiayi, depart_time_Tainan, depart_time_Zuoying) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, tripSchedule.getDepartNangang(), tripSchedule.getDepartTaipei(), tripSchedule.getDepartBanciao(), tripSchedule.getDepartTaoyuan(), tripSchedule.getDepartHsinchu(), tripSchedule.getDepartMiaoli(), tripSchedule.getDepartTaichung(), tripSchedule.getDepartChanghua(), tripSchedule.getDepartYunlin(), tripSchedule.getDepartChiayi(), tripSchedule.getDepartTainan(), tripSchedule.getDepartZuoying());
    }

    @Override
    public Optional<TripSchedule> get(int id) {
        String sql = "SELECT * FROM TripSchedule WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public int update(TripSchedule tripSchedule, int id) {
        String sql = "UPDATE TripSchedule SET depart_time_Nangang = ?, depart_time_Taipei = ?, depart_time_Banciao = ?, depart_time_Taoyuan = ?, depart_time_Hsinchu = ?, depart_time_Miaoli = ?, depart_time_Taichung = ?, depart_time_Changhua = ?, depart_time_Yunlin = ?, depart_time_Chiayi = ?, depart_time_Tainan = ?, depart_time_Zuoying = ? WHERE id = ?";
        return jdbcTemplate.update(sql, tripSchedule.getDepartNangang(), tripSchedule.getDepartTaipei(), tripSchedule.getDepartBanciao(), tripSchedule.getDepartTaoyuan(), tripSchedule.getDepartHsinchu(), tripSchedule.getDepartMiaoli(), tripSchedule.getDepartTaichung(), tripSchedule.getDepartChanghua(), tripSchedule.getDepartYunlin(), tripSchedule.getDepartChiayi(), tripSchedule.getDepartTainan(), tripSchedule.getDepartZuoying(), id);
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM TripSchedule WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
