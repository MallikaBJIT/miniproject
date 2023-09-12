package com.example.miniproject.exception;

import com.example.miniproject.response.ResponseHandler;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<?> handleBadCredentialException(BadCredentialsException ex, WebRequest webRequest) {
        return ResponseHandler.generateResponse("Check your mail or password", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest webRequest) {
        return ResponseHandler.generateResponse("Http message not readable", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SignatureException.class})
    public ResponseEntity<?> handleJwtException(SignatureException ex) {
        return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessage = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String message = error.getField().toUpperCase()
                    + ": " + error.getDefaultMessage();
            errorMessage.add(message);
        });
        return ResponseHandler.generateResponse("Request Failed", HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<?> handleProductNotExistException(CustomException ex) {
        return ResponseHandler.generateResponse(ex.getMessage(), ex.getStatus());
    }
}
