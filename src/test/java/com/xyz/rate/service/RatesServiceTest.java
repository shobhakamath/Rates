package com.xyz.rate.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.xyz.rate.configuration.RateConfiguration;
import com.xyz.rate.dto.RateDTO;
import com.xyz.rate.dto.mapper.RateRequestMapper;
import com.xyz.rate.dto.mapper.RateResponseMapper;
import com.xyz.rate.entity.Rate;
import com.xyz.rate.exception.client.RateNotFoundException;
import com.xyz.rate.exception.server.InternalServerException;
import com.xyz.rate.repository.RatesRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class RatesServiceTest {
    @InjectMocks
    RatesService ratesService;

    @Mock
    RatesRepository ratesRepository;
    @Mock
    private RestTemplate       restTemplate;
    @Mock
    private RateConfiguration  rateConfiguration;
    @Mock
    private RateRequestMapper  rateRequestMapper;
    @Mock
    private RateResponseMapper rateResponseMapper;

    public final static RateDTO
                             rateDTO = RateDTO.builder()
        .amount(new BigDecimal(500))
        .rateEffectiveDate(LocalDate.now())
        .rateExpirationDate(LocalDate.now())
        .rateId(1L)
        .build();
    public final        Rate rate    = Rate.builder()
        .amount(new BigDecimal(500))
        .rateEffectiveDate(LocalDate.now())
        .rateExpirationDate(LocalDate.now())
        .rateId(1)
        .build();

    @BeforeEach
    void setUp() {

    }

    @Test
    public void testCreateRate() {
        when(rateRequestMapper.mapFrom(any(RateDTO.class))).thenReturn(rate);
        when(rateResponseMapper.mapFrom(any(Rate.class))).thenReturn(rateDTO);
        Mockito.doReturn(rate).when(ratesRepository).save(any(Rate.class));
        RateDTO rateDTOResponse = ratesService.createRate(rateDTO);
        assertEquals(1L, rateDTO.getRateId());
    }

    @Test
    public void testCreateRate_negativecase() {
        when(rateRequestMapper.mapFrom(any(RateDTO.class))).thenReturn(rate);
        Mockito.doReturn(null).when(ratesRepository).save(any(Rate.class));
        assertThrows(InternalServerException.class, () -> ratesService.createRate(rateDTO));
    }

    @Test
    public void testUpdateRate() {
        Rate rate = Rate.builder()
            .amount(new BigDecimal(500))
            .rateEffectiveDate(LocalDate.now())
            .rateId(1)
            .build();
        when(rateRequestMapper.mapFrom(any(RateDTO.class))).thenReturn(rate);
        when(rateResponseMapper.mapFrom(any(Rate.class))).thenReturn(rateDTO);
        Mockito.doReturn(rate).when(ratesRepository).save(any());
        MatcherAssert.assertThat(ratesService.createRate(rateDTO).getRateId(), is(rateDTO.getRateId()));
    }

    @Test
    public void testUpdateRate_negativecase() {
        Mockito.doReturn(null).when(ratesRepository).findByRateId(anyLong());
        assertThrows(RateNotFoundException.class, () -> ratesService.updateRate(RateDTO.builder()
            .amount(new BigDecimal(500))
            .rateEffectiveDate(LocalDate.now())
            .rateId(1L)
            .build()));
    }

    @Test
    public void testUpdateUpdateRate_negativecase2() {
        Rate rate = Rate.builder()
            .amount(new BigDecimal(500))
            .rateEffectiveDate(LocalDate.now())
            .rateId(1L)
            .build();
        Mockito.doReturn(rate).when(ratesRepository).findByRateId(anyLong());
        Mockito.doReturn(null).when(ratesRepository).saveAndFlush(any(Rate.class));

        assertThrows(InternalServerException.class, () -> ratesService.updateRate(rateDTO));
    }

    @Test
    public void testDeleteRate() {
        Mockito.doReturn(1).when(ratesRepository).deleteByRateId(anyLong());
        assertTrue(ratesService.deleteRate(1L));

    }

    @Test
    public void testDeleteRate_negativecase() {
        Mockito.doReturn(0).when(ratesRepository).deleteByRateId(anyLong());
        assertThrows(RateNotFoundException.class, () -> ratesService.deleteRate(1L));
    }

    @Test
    void test_retrieve_negative_case() {
        Mockito.doReturn(null).when(ratesRepository).findByRateId(anyLong());
        assertThrows(RateNotFoundException.class, () -> ratesService.getRatesSurcharge(1));
    }

}
