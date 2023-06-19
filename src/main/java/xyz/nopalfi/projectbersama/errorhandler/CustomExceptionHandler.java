package xyz.nopalfi.projectbersama.errorhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UuidNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCustomException(UuidNotFoundException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorResponse(ex.getMessage(), ex.getHttpStatus(), Instant.now().getEpochSecond()));
    }

    @ExceptionHandler(InvalidUUIDException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCustomException(InvalidUUIDException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorResponse(ex.getMessage(), ex.getHttpStatus(), Instant.now().getEpochSecond()));
    }

}
