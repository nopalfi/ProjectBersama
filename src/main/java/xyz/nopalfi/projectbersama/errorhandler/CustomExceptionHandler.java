package xyz.nopalfi.projectbersama.errorhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UuidNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCustomException(UuidNotFoundException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorResponse(ex.getMessage(), ex.getHttpStatus()));
    }
}
