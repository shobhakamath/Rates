package com.xyz.rate.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class SurchargeDTO {
    private BigDecimal surchargeRate;
    private String surchargeDescription;
}
