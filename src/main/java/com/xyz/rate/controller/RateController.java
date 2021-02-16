package com.xyz.rate.controller;

import com.xyz.rate.dto.RateDTO;
import com.xyz.rate.dto.RatesSurchargeDTO;
import com.xyz.rate.service.RatesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Validated
@Tag(name = "rates", description = "The Rates API")
@RequestMapping("/rates")
public class RateController {
    @Autowired
    private RatesService ratesService;

    @Operation(summary = "Creates a new rate")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created a new rate in the db"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public RateDTO createRate(@Valid @RequestBody RateDTO rate) {
        log.debug("Creating rate entry in db");
        return ratesService.createRate(rate);
    }

    @PutMapping("/{rateId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RateDTO updateRate(@Valid @Min(value = 1000001) @PathVariable long rateId, @RequestBody RateDTO rate) {
        log.debug("Updating rate entry in db");
        rate.setRateId(rateId);
        return ratesService.updateRate(rate);
    }

    @DeleteMapping("/{rateId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteRate(@Valid @Min(value = 1000001) @PathVariable long rateId) {
        log.debug("Deleting rate entry from db");
        return ratesService.deleteRate(rateId);
    }

    @GetMapping("/{rateId}")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public RatesSurchargeDTO searchRate(@Valid @Min(value = 1000001) @PathVariable long rateId) {
        log.debug("Searching rate entry from db");
        return ratesService.getRatesSurcharge(rateId);
    }
}
