package com.esoe.service;

import com.esoe.dao.ReservedSeatDAO;
import com.esoe.enums.*;
import com.esoe.exception.BLException;
import com.esoe.model.ReservedSeat;
import com.esoe.util.Util;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    private static final ReservedSeatDAO reservedSeatDAO = new ReservedSeatDAO();
    String date;
    StationName start;
    StationName dest;
    int quantity;
    SeatPreference pref;
    SeatType seatType;

    void updateReservedSeats(List<String> seatList, int ticketID) {
        List<StationName> stationNames = Util.getContinuousStations(start, dest);
        for (String seat : seatList) {
            int car = Integer.parseInt(seat.substring(0, 2));
            int row = Integer.parseInt(seat.substring(2, 4));
            String col = seat.substring(4, 5);
            for (StationName s : stationNames) {
                reservedSeatDAO.create(new ReservedSeat(ticketID, s.getCode(), Util.stringToDate(date), car, row, col));
            }
        }
    }

    List<String> arrangeSeats() throws BLException {
        List<String> seats = new ArrayList<>();
        if (seatType == SeatType.BUSINESS) {
            seats.addAll(arrangeSeatsOfCar(quantity, Car.six, start, dest, pref, seatType));
        } else {
            seats.addAll(arrangeSeatsOfCar(quantity, Car.five, start, dest, pref, seatType));
            if (seats.size() >= quantity) return seats;
            seats.addAll(arrangeSeatsOfCar(quantity, Car.seven, start, dest, pref, seatType));
            if (seats.size() >= quantity) return seats;
            seats.addAll(arrangeSeatsOfCar(quantity, Car.four, start, dest, pref, seatType));
            if (seats.size() >= quantity) return seats;
            seats.addAll(arrangeSeatsOfCar(quantity, Car.eight, start, dest, pref, seatType));
            if (seats.size() >= quantity) return seats;
            seats.addAll(arrangeSeatsOfCar(quantity, Car.three, start, dest, pref, seatType));
            if (seats.size() >= quantity) return seats;
            seats.addAll(arrangeSeatsOfCar(quantity, Car.nine, start, dest, pref, seatType));
            if (seats.size() >= quantity) return seats;
            seats.addAll(arrangeSeatsOfCar(quantity, Car.two, start, dest, pref, seatType));
            if (seats.size() >= quantity) return seats;
            seats.addAll(arrangeSeatsOfCar(quantity, Car.ten, start, dest, pref, seatType));
            if (seats.size() >= quantity) return seats;
            seats.addAll(arrangeSeatsOfCar(quantity, Car.one, start, dest, pref, seatType));
            if (seats.size() >= quantity) return seats;
            seats.addAll(arrangeSeatsOfCar(quantity, Car.eleven, start, dest, pref, seatType));
            if (seats.size() >= quantity) return seats;
            seats.addAll(arrangeSeatsOfCar(quantity, Car.twelve, start, dest, pref, seatType));
        }
        throw new BLException("No seat available");
    }

    private List<String> arrangeSeatsOfCar(int quantity, Car car, StationName start, StationName dest, SeatPreference pref, SeatType seatType) {
        List<String> seatList = new ArrayList<>();
        if (pref == SeatPreference.AISLE || pref == SeatPreference.NONE) {
            seatList.addAll(getAisleSeatList(car, start, dest));
            if (seatList.size() >= quantity) {
                return seatList.subList(0, quantity);
            }
            if (seatType == SeatType.STANDARD) {
                seatList.addAll(getMiddleSeatList(car, start, dest));
                if (seatList.size() >= quantity) {
                    return seatList.subList(0, quantity);
                }
            }
            seatList.addAll(getWindowSeatList(car, start, dest));
            if (seatList.size() >= quantity) {
                return seatList.subList(0, quantity);
            }
        }
        if (pref == SeatPreference.WINDOW) {
            seatList.addAll(getWindowSeatList(car, start, dest));
            if (seatList.size() >= quantity) {
                return seatList.subList(0, quantity);
            }
            if (seatType == SeatType.STANDARD) {
                seatList.addAll(getMiddleSeatList(car, start, dest));
                if (seatList.size() >= quantity) {
                    return seatList.subList(0, quantity);
                }
            }
            seatList.addAll(getAisleSeatList(car, start, dest));
            if (seatList.size() >= quantity) {
                return seatList.subList(0, quantity);
            }
        }
        return seatList;
    }

    private List<String> getAisleSeatList(Car car, StationName start, StationName dest) {
        List<String> seats = new ArrayList<>();
        for (int row = 1; row <= car.getMaxRow(); row++) {
            if (Validate.isSeat(car.getNo(), row, SeatPosition.D_AISLE)) {
                List<ReservedSeat> reserved = reservedSeatDAO.list(car.getNo(), row, SeatPosition.D_AISLE.getCodeStr(), start, dest, date);
                if (reserved.isEmpty()) {
                    seats.add(String.format("%2d%2d%s", car.getNo(), row, SeatPosition.D_AISLE.getCodeStr()));
                }
            }
            if (Validate.isSeat(car.getNo(), row, SeatPosition.C_AISLE)) {
                List<ReservedSeat> reserved = reservedSeatDAO.list(car.getNo(), row, SeatPosition.C_AISLE.getCodeStr(), start, dest, date);
                if (reserved.isEmpty()) {
                    seats.add(String.format("%2d%2d%s", car.getNo(), row, SeatPosition.C_AISLE.getCodeStr()));
                }
            }
        }
        return seats;
    }

    private List<String> getMiddleSeatList(Car car, StationName start, StationName dest) {
        List<String> seats = new ArrayList<>();
        for (int row = 1; row <= car.getMaxRow(); row++) {
            if (Validate.isSeat(car.getNo(), row, SeatPosition.B_MIDDLE)) {
                List<ReservedSeat> reserved = reservedSeatDAO.list(car.getNo(), row, SeatPosition.B_MIDDLE.getCodeStr(), start, dest, date);
                if (reserved.isEmpty()) {
                    seats.add(String.format("%2d%2d%s", car.getNo(), row, SeatPosition.B_MIDDLE.getCodeStr()));
                }
            }
        }
        return seats;
    }

    private List<String> getWindowSeatList(Car car, StationName start, StationName dest) {
        List<String> seats = new ArrayList<>();
        for (int row = 1; row <= car.getMaxRow(); row++) {
            if (Validate.isSeat(car.getNo(), row, SeatPosition.A_WINDOW)) {
                List<ReservedSeat> reserved = reservedSeatDAO.list(car.getNo(), row, SeatPosition.A_WINDOW.getCodeStr(), start, dest, date);
                if (reserved.isEmpty()) {
                    seats.add(String.format("%2d%2d%s", car.getNo(), row, SeatPosition.A_WINDOW.getCodeStr()));
                }
            }
            if (Validate.isSeat(car.getNo(), row, SeatPosition.E_WINDOW)) {
                List<ReservedSeat> reserved = reservedSeatDAO.list(car.getNo(), row, SeatPosition.E_WINDOW.getCodeStr(), start, dest, date);
                if (reserved.isEmpty()) {
                    seats.add(String.format("%2d%2d%s", car.getNo(), row, SeatPosition.E_WINDOW.getCodeStr()));
                }
            }
        }
        return seats;
    }

}
