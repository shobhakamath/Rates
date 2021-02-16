package com.xyz.rate.dto.mapper;

import com.xyz.rate.dto.RateDTO;
import com.xyz.rate.entity.Rate;
import com.xyz.rate.exception.server.InternalServerException;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class RateResponseMapper
    implements RequestMapper<Rate, RateDTO> {
    @Override public RateDTO mapFrom(Rate rateRequest) {
        return Optional.of(rateRequest).map(request ->
            RateDTO.builder()
                .rateId(request.getRateId())
                .amount(request.getAmount())
                .rateDescription(request.getRateDescription())
                .rateEffectiveDate(request.getRateEffectiveDate())
                .rateExpirationDate(request.getRateExpirationDate())
                .build())
            .orElseThrow(() -> new InternalServerException("Internal Server Error.Please contact admin"));
    }
}