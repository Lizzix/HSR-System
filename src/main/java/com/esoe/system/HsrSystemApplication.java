package com.esoe.system;

import com.esoe.dao.*;
import com.esoe.enums.DiscountType;
import com.esoe.enums.SeatType;
import com.esoe.enums.StationName;
import com.esoe.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;
import static java.lang.System.exit;

@SpringBootApplication
public class HsrSystemApplication {

    private static OrderDAO orderDAO = new OrderDAO();
    private static ReservedSeatDAO reservedSeatDAO = new ReservedSeatDAO();
    private static TicketDAO ticketDAO = new TicketDAO();

    public static void main(String[] args) {
        SpringApplication.run(HsrSystemApplication.class, args);

        // OrderDAOtest
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

        exit(0);
    }

}
