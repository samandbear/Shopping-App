package com.example.transactionmanagementdemo.exception;

public class NoRightException extends Exception {
    public NoRightException(String message) {
        super(String.format(message));

    }
}
