package com.money.api.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.jws.WebResult;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by vic on 14/08/17.
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                   WebRequest request){

        String userMessage = messageSource.getMessage("invalid.message", null, LocaleContextHolder.getLocale());
        String devMessage = ex.getCause().toString();
        List<Error> errors = Arrays.asList(new Error(userMessage, devMessage));
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Error> errors = createErrorList(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<Error> createErrorList(BindingResult result){
        List<Error> errors = new ArrayList<>();

        for (FieldError fieldError : result.getFieldErrors()){
            String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String devMessage = fieldError.toString();
            errors.add(new Error(userMessage, devMessage));
        }

        return errors;
    }

    public static class Error {
        private String userMessage;
        private String devMessage;

        public Error(String userMessage, String devMessage){
            this.userMessage = userMessage;
            this.devMessage = devMessage;

        }
        public String getUserMessage() {
            return userMessage;
        }
        public String getDevMessage() {
            return devMessage;
        }
    }
}
