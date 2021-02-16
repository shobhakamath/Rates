package com.xyz.rate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateDTO {
    private long       rateId;
    private String     rateDescription;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate  rateEffectiveDate;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate  rateExpirationDate;
    @NotNull
    @DecimalMin("1.00")
    private BigDecimal amount;

    public RateDTO(String rateDescription, @NotNull LocalDate rateEffectiveDate,
        @NotNull LocalDate rateExpirationDate,
        @NotNull @DecimalMin("1.00") BigDecimal amount) {
        this.rateDescription = rateDescription;
        this.rateEffectiveDate = rateEffectiveDate;
        this.rateExpirationDate = rateExpirationDate;
        this.amount = amount;
    }
}