package com.esoe.exception;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BLException extends Exception {

    private final String errMessage;

    public BLException(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

}
