package com.example.alumni.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // This annotation is optional but recommended
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}