package xyz.nopalfi.projectbersama.errorhandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${api.version}")
    private String apiVersion;

    @ExceptionHandler(UuidNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCustomException(UuidNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorResponse(ex.getMessage(), ex.getHttpStatus(), Instant.now().getEpochSecond(), request.getRequestURI(), apiVersion));
    }

    @ExceptionHandler(InvalidUUIDException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCustomException(InvalidUUIDException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorResponse(ex.getMessage(), ex.getHttpStatus(), Instant.now().getEpochSecond(), request.getRequestURI(), apiVersion));
    }

}
