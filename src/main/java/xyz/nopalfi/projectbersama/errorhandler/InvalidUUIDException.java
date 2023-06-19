package xyz.nopalfi.projectbersama.errorhandler;

import org.springframework.http.HttpStatus;

public class InvalidUUIDException extends RuntimeException{

    private final HttpStatus httpStatus;

    public InvalidUUIDException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
