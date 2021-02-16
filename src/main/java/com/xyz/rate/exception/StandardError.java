package com.xyz.rate.exception;


import com.xyz.rate.dto.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

public interface StandardError {
    String getErrorCode();
    String getMessage();

    default ErrorMessage getErrorMessage() {
        List<String> errorList=new ArrayList<>();
        errorList.add(getMessage());
        return new ErrorMessage(getErrorCode(), errorList);
    }
}
