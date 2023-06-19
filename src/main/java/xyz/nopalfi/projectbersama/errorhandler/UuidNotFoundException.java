package xyz.nopalfi.projectbersama.errorhandler;

import org.springframework.http.HttpStatus;

public class UuidNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;
    public UuidNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
