package com.esoe.controller;

import com.esoe.enums.DiscountType;
import com.esoe.enums.SeatPreference;
import com.esoe.enums.SeatType;
import com.esoe.exception.BLException;
import com.esoe.service.Booking;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")

public class bookingController {
    @PostMapping("/booking")
    public ResponseBody booking(@PathVariable("id") String id, @PathVariable("data") List data) throws BLException {
        ResponseBody response = new ResponseBody();
        String date = data.get(0).toString();
        String start = data.get(1).toString();
        String dest = data.get(2).toString();
        short trainNo = Short.parseShort(data.get(3).toString());
        SeatType seatType = SeatType.valueOf(data.get(4).toString());
        SeatPreference seatPref = SeatPreference.valueOf(data.get(5).toString());
        int genQuentity = Integer.parseInt(data.get(6).toString());
        int uniQuentity = Integer.parseInt(data.get(7).toString());
        DiscountType discountType = DiscountType.valueOf(data.get(8).toString());

        Booking booking = new Booking(id, uniQuentity, genQuentity, date, trainNo, start, dest, seatPref, seatType, discountType);
        try {
            booking.bookTickets();
        }
        catch (BLException e) {
            response.setMessage(e.getErrMessage());
            response.setStatus(0);
            return response;
        }
        response.setStatus(1);
        response.setMessage("success");
        response.data.add(booking.getOrder());
        response.data.add(booking.getTickets());
        return response;
    }
}
