package com.xyz.rate.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorMessage {
    private final String       errorCode;
    private final List<String> errorMessage;
}
