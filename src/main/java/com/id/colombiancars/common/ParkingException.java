package com.id.colombiancars.common;

import org.springframework.http.HttpStatus;

public class ParkingException  extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;
    private String message;

    public ParkingException(HttpStatus httpStatus, String message) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ParkingException(HttpStatus httpStatus, String message, String message1) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
        this.message = message1;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
