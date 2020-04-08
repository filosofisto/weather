package com.epam.weather.exception;

import com.epam.weather.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class WeatherExceptionHandler extends ResponseEntityExceptionHandler {

    private final Messages messages;

    @Autowired
    public WeatherExceptionHandler(Messages messages) {
        this.messages = messages;
    }

    @RequestMapping(produces = "application/json")
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<?> handleException(HttpClientErrorException.NotFound e) {
        return new ResponseEntity<>(new ErrorMessage(messages.get("ERR_404"), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(produces = "application/json")
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<?> handleException(HttpClientErrorException.Unauthorized e) {
        return new ResponseEntity<>(new ErrorMessage(messages.get("ERR_401"), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(produces = "application/json")
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleException(Throwable e) {
        return new ResponseEntity<>(new ErrorMessage(messages.get("ERR_UNEXPECTED"), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
