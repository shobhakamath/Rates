package com.xyz.rate.exception.client;

import com.xyz.rate.exception.StandardError;

public class ClientException extends RuntimeException implements StandardError {

    public ClientException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "CLIENT_ERROR";
    }
}
