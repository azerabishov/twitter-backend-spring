package org.azerabshv.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.azerabshv.constants.HttpResponseConstants.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");


    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<?> handle(EmailNotVerifiedException ex, WebRequest request, Locale locale) {
        return buildException(request, HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handle(AlreadyExistsException ex, WebRequest request, Locale locale) {
        return buildException(request, HttpStatus.CONFLICT, ex.getMessage());
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handle(NotFoundException ex, WebRequest request, Locale locale) {
        return buildException(request, HttpStatus.NOT_FOUND, ex.getMessage());
    }


    @ExceptionHandler(InvalidStateException.class)
    public ResponseEntity<?> handle(InvalidStateException ex, WebRequest request, Locale locale) {
        return buildException(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }



    private ResponseEntity<?> buildException(WebRequest request, HttpStatus status, String message) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put(TIMESTAMP, LocalDateTime.now().format(FORMATTER));
        attributes.put(STATUS, status.value());
        attributes.put(ERROR, status.getReasonPhrase());
        attributes.put(MESSAGE, message);
        attributes.put(PATH, ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(attributes, status);

    }

}

