package com.xyz.rate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class RatesSurchargeDTO {
    private RateDTO      ratesDTO;
    private SurchargeDTO surchargeDTO;
}
