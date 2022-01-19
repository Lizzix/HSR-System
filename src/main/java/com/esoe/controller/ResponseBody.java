package com.esoe.controller;

import lombok.Setter;
import java.util.List;

@Setter
public class ResponseBody {
    int status;
    String message;
    List<Object> data;
}
