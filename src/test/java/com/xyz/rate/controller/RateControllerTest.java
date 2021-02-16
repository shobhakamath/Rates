package com.xyz.rate.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.xyz.rate.dto.RateDTO;
import com.xyz.rate.exception.server.InternalServerException;
import com.xyz.rate.service.RatesService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RateControllerTest {
    @InjectMocks
    RateController rateController;

    @Mock
    RatesService rateService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRate() {
        RateDTO rate = RateDTO.builder()
            .amount(new BigDecimal(500))
            .rateId(1L)
            .build();
        Mockito.doReturn(rate).when(rateService).createRate(any(RateDTO.class));
        assertThat(rateController.createRate(rate), is(rate));
    }

    @Test
    public void testCreateRate_negativeCase() {
        RateDTO rate = RateDTO.builder()
            .amount(new BigDecimal(500))
            .rateId(1)
            .build();
        Mockito.doThrow(InternalServerException.class).when(rateService).createRate(any(RateDTO.class));
        assertThrows(InternalServerException.class, () -> rateController.createRate(rate));
    }

    @Test
    public void testUpdateRate() {
        RateDTO rate = RateDTO.builder()
            .amount(new BigDecimal(500))
            .rateId(1)
            .build();
        Mockito.doReturn(rate).when(rateService).updateRate(any(RateDTO.class));
        assertThat(rateController.updateRate(1, rate), is(rate));
    }

    @Test
    public void testUpdateRate_negativeCase() {
        RateDTO rate = RateDTO.builder()
            .amount(new BigDecimal(500))
            .rateId(1)
            .build();
        Mockito.doThrow(InternalServerException.class).when(rateService).updateRate(any(RateDTO.class));
        assertThrows(InternalServerException.class, () -> rateController.updateRate(1L, rate));
    }

    @Test
    public void testDeleteRate() {
        RateDTO rate = RateDTO.builder()
            .amount(new BigDecimal(500))
            .rateId(1)
            .build();
        Mockito.doReturn(true).when(rateService).deleteRate(anyLong());
        assertThat(rateController.deleteRate(1L), is(true));
    }

    @Test
    public void testDeleteRate_negativeCase() {
        RateDTO rate = RateDTO.builder()
            .amount(new BigDecimal(500))
            .rateId(1)
            .build();
        Mockito.doThrow(InternalServerException.class).when(rateService).deleteRate(anyLong());
        assertThrows(InternalServerException.class, () -> rateController.deleteRate(1L));
    }

    @Test
    public void testSearchRate() {
        RateDTO rate = RateDTO.builder()
            .amount(new BigDecimal(500))
            .rateId(1)
            .build();
        Mockito.doReturn(true).when(rateService).deleteRate(anyLong());
        assertThat(rateController.deleteRate(1L), is(true));
    }

    @Test
    public void testSearchRate_negativeCase() {
        RateDTO rate = RateDTO.builder()
            .amount(new BigDecimal(500))
            .rateId(1)
            .build();
        Mockito.doThrow(InternalServerException.class).when(rateService).getRatesSurcharge(anyLong());
        assertThrows(InternalServerException.class, () -> rateController.searchRate(1L));
    }
}
