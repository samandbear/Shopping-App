package com.example.transactionmanagementdemo.exception;

public class NotEnoughInventoryException extends Exception {

    public NotEnoughInventoryException(String message) {
        super(String.format(message));

    }
}