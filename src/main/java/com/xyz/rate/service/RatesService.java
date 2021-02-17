package com.xyz.rate.service;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import com.xyz.rate.configuration.RateConfiguration;
import com.xyz.rate.dto.RateDTO;
import com.xyz.rate.dto.RatesSurchargeDTO;
import com.xyz.rate.dto.SurchargeDTO;
import com.xyz.rate.dto.mapper.RateRequestMapper;
import com.xyz.rate.dto.mapper.RateResponseMapper;
import com.xyz.rate.entity.Rate;
import com.xyz.rate.exception.client.RateNotFoundException;
import com.xyz.rate.exception.server.InternalServerException;
import com.xyz.rate.repository.RatesRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatesService {

    private final RatesRepository ratesRepository;

    private final RestTemplate restTemplate;

    private final RateConfiguration  rateConfiguration;
    private final RateRequestMapper  rateRequestMapper;
    private final RateResponseMapper rateResponseMapper;

    public static final Predicate<Object> IS_NULL = Objects::isNull;

    @Transactional
    public RateDTO createRate(final RateDTO rate) {
        return Optional.of(rate)
            .map(rateRequestMapper::mapFrom)
            .map(ratesRepository::save)
            .map(rateResponseMapper::mapFrom)
            .orElseThrow(() -> new InternalServerException("Internal Server Error.Please contact admin"));
    }

    @Transactional
    public RateDTO updateRate(final RateDTO rateDTO) {
        Rate rate = Optional.of(rateDTO.getRateId())
            .map(ratesRepository::findByRateId)
            .orElseThrow(() -> new RateNotFoundException("RateId not found in RMS"));
        if (!IS_NULL.test(rateDTO.getAmount())) {
            rate.setAmount(rateDTO.getAmount());
        }
        if (!IS_NULL.test(rateDTO.getRateEffectiveDate())) {
            rate.setRateEffectiveDate(rateDTO.getRateEffectiveDate());
        }
        if (!IS_NULL.test(rateDTO.getRateExpirationDate())) {
            rate.setRateExpirationDate(rateDTO.getRateExpirationDate());
        }
        if (!IS_NULL.test(rateDTO.getRateDescription())) {
            rate.setRateDescription(rateDTO.getRateDescription());
        }

        return Optional.of(rate)
            .map(ratesRepository::saveAndFlush)
            .map(rateResponseMapper::mapFrom)
            .orElseThrow(() -> new InternalServerException("Internal Server Error.Please contact admin"));
    }

    @Transactional
    public boolean deleteRate(final long rateId) {
        int value = ratesRepository.deleteByRateId(rateId);
        if (value == 0) {
            throw new RateNotFoundException("RateId not found in RMS");
        }
        return true;
    }
    @Transactional(propagation = REQUIRES_NEW,
        isolation = SERIALIZABLE,
        timeoutString = "${service.transaction.timeout:3}")
    public RatesSurchargeDTO getRatesSurcharge(final long rateId) {
        return RatesSurchargeDTO.builder()
            .ratesDTO(getRates(rateId))
            .surchargeDTO(getSurcharge())
            .build();
    }


    @CircuitBreaker(name = "rates", fallbackMethod = "fallBackMethod")
    private RateDTO getRates(final long rateId) {
        return Optional.of(rateId)
            .map(ratesRepository::findByRateId)
            .map(rateResponseMapper::mapFrom)
            .orElseThrow(() -> new RateNotFoundException("RateId not found in RMS"));
    }

    @CircuitBreaker(name = "surcharges", fallbackMethod = "fallBackMethodSurcharge")
    private SurchargeDTO getSurcharge() {
        return restTemplate.
            getForObject(rateConfiguration.getRateUrl(), SurchargeDTO.class);
    }

    public RateDTO fallBackMethod(final long rateId, final Exception throwable) {
        log.error("Inside circuit breaker fallBackMethod");
        throw new RateNotFoundException("No rates found");
    }

    public SurchargeDTO fallBackMethodSurcharge(final Exception throwable) {
        log.error("Inside circuit breaker fallBackMethodSurcharge");
        throw new RateNotFoundException("No surcharges found");
    }
}
