CREATE DATABASE IF NOT EXISTS HSR;
use HSR;

CREATE TABLE Station
(
    id         DECIMAL(4)  NOT NULL,
    name_En    VARCHAR(10) NOT NULL,
    name_Zh_tw CHAR(2)     NOT NULL,
    address    VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Price
(
    id               INT AUTO_INCREMENT                                                      NOT NULL,
    start_station_id DECIMAL(4)                                                              NOT NULL,
    dest_station_id  DECIMAL(4)                                                              NOT NULL,
    direction        BOOLEAN                                                                 NOT NULL DEFAULT 0,
    ticket_type      ENUM ('STANDARD', 'BUSINESS', 'P90', 'P88', 'P80', 'P75', 'P65', 'P50') NOT NULL,
    price            INT UNSIGNED check (price > 0),
    PRIMARY KEY (id),
    FOREIGN KEY (start_station_id) REFERENCES Station (id),
    FOREIGN KEY (dest_station_id) REFERENCES Station (id)
);

CREATE TABLE Discount
(
    id             INT AUTO_INCREMENT UNIQUE                        NOT NULL,
    train_id       DECIMAL(4)                                       NOT NULL,
    discount_type  ENUM ('UNIVERSITY','EARLY')                      NOT NULL,
    update_date    Date                                             NOT NULL,
    effective_date Date                                             NOT NULL,
    expire_date    Date                                             NOT NULL,
    weekday        ENUM ('MON','TUE','WED','THU','FRI','SAT','SUN') NOT NULL,
    percentage     DECIMAL(2)                                       NOT NULL,
    quantity       SMALLINT UNSIGNED                                NOT NULL,
    KEY (id, discount_type)
);

CREATE TABLE BookingOrder
(
    id           INT AUTO_INCREMENT NOT NULL,
    user_id      CHAR(10)           NOT NULL,
    pay_deadline DateTime           NOT NULL,
    payment      MEDIUMINT UNSIGNED NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Ticket
(
    id               INT AUTO_INCREMENT                   NOT NULL,
    order_id         INT                                  NOT NULL,
    discount_type    ENUM ('NONE', 'UNIVERSITY', 'EARLY') NOT NULL DEFAULT 'NONE',
    date             Date                                 NOT NULL,
    start_station_id DECIMAL(4) UNSIGNED                  NOT NULL,
    dest_station_id  DECIMAL(4) UNSIGNED                  NOT NULL,
    round_trip       BOOLEAN                              NOT NULL,
    seat_type        ENUM ('STANDARD', 'BUSINESS')        NOT NULL DEFAULT 'STANDARD',
    seat_code        CHAR(5)                              NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES BookingOrder (id)
);

CREATE TABLE ReservedSeat
(
    id         INT AUTO_INCREMENT            NOT NULL,
    ticket_id  INT                           NOT NULL,
    station_id DECIMAL(4)                    NOT NULL,
    date       Date                          NOT NULL,
    car        DECIMAL(2)                    NOT NULL,
    row        DECIMAL(2)                    NOT NULL,
    col        char(1)                       NOT NULL,
    seat_type  ENUM ('STANDARD', 'BUSINESS') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (ticket_id) REFERENCES Ticket (id),
    FOREIGN KEY (station_id) REFERENCES Station (id)
);


CREATE TABLE Trip
(
    id               INT UNSIGNED NOT NULL,
    train_id         INT UNSIGNED NOT NULL,
    start_station_id DECIMAL(4)   NOT NULL,
    dest_station_id  DECIMAL(4)   NOT NULL,
    direction        BOOLEAN      NOT NULL DEFAULT 0,
    update_date      Date         NOT NULL,
    effective_date   Date         NOT NULL,
    serve_mon        BOOLEAN      NOT NULL,
    serve_tue        BOOLEAN      NOT NULL,
    serve_wed        BOOLEAN      NOT NULL,
    serve_thu        BOOLEAN      NOT NULL,
    serve_fri        BOOLEAN      NOT NULL,
    serve_sat        BOOLEAN      NOT NULL,
    serve_sun        BOOLEAN      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (start_station_id) REFERENCES Station (id),
    FOREIGN KEY (dest_station_id) REFERENCES Station (id)
);

CREATE TABLE TripSchedule
(
    id                   INT UNSIGNED NOT NULL,
    trip_id              INT UNSIGNED NOT NULL,
    depart_time_Nangang  Time,
    depart_time_Taipei   Time,
    depart_time_Banciao  Time,
    depart_time_Taoyuan  Time,
    depart_time_Hsinchu  Time,
    depart_time_Miaoli   Time,
    depart_time_Taichung Time,
    depart_time_Changhua Time,
    depart_time_Yunlin   Time,
    depart_time_Chiayi   Time,
    depart_time_Tainan   Time,
    depart_time_Zuoying  Time,
    PRIMARY KEY (id)
);