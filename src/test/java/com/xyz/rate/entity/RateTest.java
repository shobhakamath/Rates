package com.xyz.rate.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

import java.math.BigDecimal;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

public class RateTest {

    @Test
    public void testConstructorAndGetters() throws Exception {
        Rate rate = new Rate(1, "rate", null, null, new BigDecimal(500));
        assertThat(rate.getRateId(), is(1L));
        assertThat(rate.getRateEffectiveDate(), is(nullValue()));
        assertThat(rate.getAmount(), is(new BigDecimal(500)));
    }

    @Test
    public void equalsHashcodeVerify() {
        Rate rate1 = new Rate(1L, "rate", null, null, new BigDecimal(500));
        Rate rate2 = new Rate(1L, "rate", null, null, new BigDecimal(500));

        assertThat(rate1, Is.is(rate2));
        assertThat(rate1.hashCode(), is(rate2.hashCode()));
    }
}
