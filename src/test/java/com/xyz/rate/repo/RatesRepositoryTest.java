package com.xyz.rate.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.xyz.rate.entity.Rate;
import com.xyz.rate.repository.RatesRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class RatesRepositoryTest {
    @Autowired
    private RatesRepository repository;

    @Test
    public void testFindByRateId() {
        Rate rate = repository.save(new Rate("description", LocalDate.now(), LocalDate.now(), new BigDecimal(300)));

        Rate fetchrate = repository.findByRateId(1000001);
        assertNotNull(fetchrate.getAmount());
        assertEquals("description", fetchrate.getRateDescription());
        assertEquals(LocalDate.now(), fetchrate.getRateEffectiveDate());
        assertEquals(LocalDate.now(), fetchrate.getRateExpirationDate());

        fetchrate = repository.findByRateId(2L);
        assertNull(fetchrate);
    }

    @Test
    public void testdeleteByRateId() {
        Rate rate = repository.save(new Rate("description", LocalDate.now(), LocalDate.now(), new BigDecimal(300)));

        int done = repository.deleteByRateId(1000001);
        assertEquals(1, done);
        assertEquals(0, repository.deleteByRateId(2L));
    }
}
