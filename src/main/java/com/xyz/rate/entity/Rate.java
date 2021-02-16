package com.xyz.rate.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Rates")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "rateId" })
@SequenceGenerator(name = "rate_number_seq", initialValue = 1000001, allocationSize = 100)
public class Rate {
    @Column(name = "id", unique = true, nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rate_number_seq")
    private long       rateId;
    private String     rateDescription;
    private LocalDate  rateEffectiveDate;
    private LocalDate  rateExpirationDate;
    private BigDecimal amount;

    public Rate(String rateDescription, @NotNull LocalDate rateEffectiveDate,
        LocalDate rateExpirationDate,
        BigDecimal amount) {
        this.rateDescription = rateDescription;
        this.rateEffectiveDate = rateEffectiveDate;
        this.rateExpirationDate = rateExpirationDate;
        this.amount = amount;
    }
}
