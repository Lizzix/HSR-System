package com.esoe.model;

import com.esoe.enums.TicketType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Price {
    private int id;
    private short startStationID;
    private short destStationID;
    private TicketType ticketType;
    private short price;
}
