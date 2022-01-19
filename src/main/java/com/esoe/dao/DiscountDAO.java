package com.esoe.dao;

import com.esoe.enums.DayOfWeek;
import com.esoe.enums.DiscountType;
import com.esoe.model.Discount;
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
public class DiscountDAO extends DAO<Discount> {

    RowMapper<Discount> rowMapper = (rs, rowNum) -> {
        Discount discount = new Discount();
        discount.setId(rs.getInt("id"));
        discount.setTrainID(rs.getShort("train_id"));
        discount.setDiscountType(DiscountType.valueOf(rs.getString("discount_type")));
        discount.setUpdateDate(rs.getDate("update_date"));
        discount.setEffectiveDate(rs.getDate("effective_date"));
        discount.setExpireDate(rs.getDate("expire_date"));
        discount.setWeekday(DayOfWeek.valueOf(rs.getString("weekday")));
        discount.setPercentage(rs.getShort("percentage"));
        discount.setQuantity(rs.getShort("quantity"));
        return discount;
    };

    @Override
    public List<Discount> list() {
        String sql = "SELECT * FROM Discount WHERE quantity > 0";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Discount> list(DiscountType discountType) {
        String sql = "SELECT * FROM Discount WHERE discount_type = ? AND quantity > 0";
        return jdbcTemplate.query(sql, rowMapper, discountType.toString());
    }

    public List<Discount> list(DiscountType discountType, int trainID) {
        String sql = "SELECT * FROM Discount WHERE discount_type = ? AND quantity > 0 AND train_id = ?";
        return jdbcTemplate.query(sql, rowMapper, discountType.toString(), trainID);
    }

    public List<Discount> list(DiscountType discountType, Date date) {
        String serveDay = Objects.requireNonNull(Util.getDayOfWeek(date)).getAbbreviation();
        String sql = "SELECT * FROM Discount WHERE discount_type = ? AND quantity > 0 AND weekday = ? AND effective_date <= ? AND expire_date >= ?";
        return jdbcTemplate.query(sql, rowMapper, discountType.toString(), serveDay, date, date);
    }

    public List<Discount> list(DiscountType discountType, int trainID, Date date) {
        String serveDay = Objects.requireNonNull(Util.getDayOfWeek(date)).getAbbreviation();
        String sql = "SELECT * FROM Discount WHERE discount_type = ? AND quantity > 0 AND weekday = ? AND effective_date <= ? AND expire_date >= ? AND train_id = ?";
        return jdbcTemplate.query(sql, rowMapper, discountType.toString(), serveDay, date, date, trainID);
    }

    public List<Discount> list(short percentage) {
        String sql = "SELECT * FROM Discount WHERE percentage = ? AND quantity > 0";
        return jdbcTemplate.query(sql, rowMapper, percentage);
    }

    @Override
    public int create(Discount discount) {
        String sql = "INSERT INTO Discount (train_id, discount_type, update_date, effective_date, expire_date, weekday, percentage, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, discount.getTrainID(), discount.getDiscountType().toString(), discount.getUpdateDate(), discount.getEffectiveDate(), discount.getExpireDate(), discount.getWeekday().toString(), discount.getPercentage(), discount.getQuantity());
    }

    @Override
    public Optional<Discount> get(int id) {
        String sql = "SELECT * FROM Discount WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public int update(Discount discount, int id) {
        String sql = "UPDATE Discount SET train_id = ?, discount_type = ?, update_date = ?, effective_date = ?, expire_date = ?, weekday = ?, percentage = ?, quantity = ? WHERE id = ?";
        return jdbcTemplate.update(sql, discount.getTrainID(), discount.getDiscountType().toString(), discount.getUpdateDate(), discount.getEffectiveDate(), discount.getExpireDate(), discount.getWeekday().toString(), discount.getPercentage(), discount.getQuantity(), id);
    }
    public int update(int id, int minusQuantity) {
        String sql = "UPDATE Discount SET quantity = quantity - ? WHERE id = ?";
        return jdbcTemplate.update(sql, minusQuantity, id);
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM Discount WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
