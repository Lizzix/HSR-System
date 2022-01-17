package com.esoe.system;

import com.esoe.dao.DAO;
import com.esoe.dao.OrderDAO;
import com.esoe.dao.ReservedSeatDAO;
import com.esoe.dao.TicketDAO;
import com.esoe.enums.DiscountType;
import com.esoe.enums.SeatType;
import com.esoe.enums.StationName;
import com.esoe.model.Order;
import com.esoe.model.ReservedSeat;
import com.esoe.model.Station;
import com.esoe.model.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class HsrSystemApplicationTests {

    private static OrderDAO orderDAO = new OrderDAO();
    private static ReservedSeatDAO reservedSeatDAO = new ReservedSeatDAO();
    private static TicketDAO ticketDAO = new TicketDAO();

    @Test
    void contextLoads() {
        Date date = new Date(System.currentTimeMillis());
        Order o = new Order(1, "A123456789", date, 520);
        orderDAO.create(o);
        List<Order> orders = orderDAO.list();
        orders.forEach(System.out::println);

        // TicketDAOtest
        Ticket t = new Ticket(1, 1, DiscountType.EARLY, date, StationName.Banciao.getCode(), StationName.Changhua.getCode(), false, SeatType.STANDARD, "0101A");
        ticketDAO.create(t);
        List<Ticket> tickets = ticketDAO.list();
        tickets.forEach(System.out::println);

        // ReservedSeatDAOtest
        ReservedSeat rs = new ReservedSeat(1, StationName.Taichung.getCode(), date, 1, 1, "A", SeatType.STANDARD);
        reservedSeatDAO.create(rs);
        List<ReservedSeat> reservedSeats = reservedSeatDAO.list();
        reservedSeats.forEach(System.out::println);
    }

}
