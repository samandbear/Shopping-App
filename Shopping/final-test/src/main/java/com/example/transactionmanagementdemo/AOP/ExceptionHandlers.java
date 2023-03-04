package com.example.transactionmanagementdemo.AOP;

import com.example.transactionmanagementdemo.domain.response.ErrorResponse;
import com.example.transactionmanagementdemo.exception.NoRightException;
import com.example.transactionmanagementdemo.exception.NotEnoughInventoryException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(value = {NoRightException.class})
    public ResponseEntity<ErrorResponse> handleEmailExistedException(NoRightException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {NotEnoughInventoryException.class})
    public ResponseEntity<ErrorResponse> handleUsernameExistedException(NoRightException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {SignatureException.class})
    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }
}
