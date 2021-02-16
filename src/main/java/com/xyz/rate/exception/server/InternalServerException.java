package com.xyz.rate.exception.server;

import com.xyz.rate.exception.StandardError;

public class InternalServerException extends RuntimeException implements StandardError {

    public InternalServerException(final String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "SERVER_ERROR";
    }
}