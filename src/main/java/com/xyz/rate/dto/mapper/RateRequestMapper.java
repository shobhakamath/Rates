package com.xyz.rate.dto.mapper;

import com.xyz.rate.dto.RateDTO;
import com.xyz.rate.entity.Rate;
import org.springframework.stereotype.Component;

@Component
public class RateRequestMapper implements RequestMapper<RateDTO, Rate> {
    @Override public Rate mapFrom(RateDTO request) {
        return Rate.builder()
            .amount(request.getAmount())
            .rateDescription(request.getRateDescription())
            .rateEffectiveDate(request.getRateEffectiveDate())
            .rateExpirationDate(request.getRateExpirationDate())
            .build();
    }
}
