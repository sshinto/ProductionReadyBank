package com.hollandandbarret.bank.configuration.advice;

import com.hollandandbarret.bank.exception.BankException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class BankExcpetionHandler {
    Logger logger = LoggerFactory.getLogger(BankExcpetionHandler.class);

    @ExceptionHandler(BankException.class)
    protected ResponseEntity<String> handleBankException(BankException ex){
        return new ResponseEntity<>(
                ex.getBankErrors().getErrorMessage(),   ex.getBankErrors().getHttpStatusCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleInternalException(Exception ex){
        return new ResponseEntity<>(
               "There are some issues in the system. Please try again later", HttpStatus.BAD_REQUEST);
    }
}
