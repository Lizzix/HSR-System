package com.esoe.model;

import com.esoe.enums.DayOfWeek;
import com.esoe.enums.DiscountType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Discount {
    private int id;
    private short trainID;
    private DiscountType discountType;
    private Date updateDate;
    private Date effectiveDate;
    private Date expireDate;
    private DayOfWeek weekday;
    private short percentage;
    private short quantity;
}
