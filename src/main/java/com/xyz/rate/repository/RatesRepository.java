package com.xyz.rate.repository;

import com.xyz.rate.entity.Rate;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatesRepository extends JpaRepository<Rate, Long> {
    Rate findByRateId(final long rateId);


    int deleteByRateId(final long rateId);

}
