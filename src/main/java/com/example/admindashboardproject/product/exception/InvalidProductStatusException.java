package com.example.admindashboardproject.product.exception;

public class InvalidProductStatusException extends RuntimeException{
    public InvalidProductStatusException(String message){
        super(message);
    }
}
