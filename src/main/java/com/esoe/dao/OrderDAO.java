package com.esoe.dao;

import com.esoe.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class OrderDAO extends DAO<Order> {

    RowMapper<Order> rowMapper = (rs, rowNum) -> {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserID(rs.getString("user_id"));
        order.setPayDeadline(rs.getDate("pay_deadline"));
        order.setPayment(rs.getInt("payment"));
        return order;
    };
    
    @Override
    public List<Order> list() {
        String sql = "SELECT * FROM BookingOrder";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int create(Order order) {
        String sql = "INSERT INTO BookingOrder (user_id, pay_deadline, payment) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, order.getUserID(), order.getPayDeadline(), order.getPayment());
    }

    @Override
    public Optional get(int id) {
        String sql = "SELECT * FROM BookingOrder WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public Optional<Order> get(int orderID, String userID) {
        String sql = "SELECT * FROM BookingOrder WHERE id = ? AND user_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, orderID, userID));
    }

    @Override
    public int update(Order order, int id) {
        String sql = "UPDATE BookingOrder SET user_id = ?, pay_deadline = ?, payment = ? WHERE id = ?";
        return jdbcTemplate.update(sql, order.getUserID(), order.getPayDeadline(), order.getPayment(), id);
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM BookingOrder WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
