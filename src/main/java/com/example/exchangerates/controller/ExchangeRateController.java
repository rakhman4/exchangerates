package com.example.exchangerates.controller;

import com.example.exchangerates.client.BankExchangeRate;
import com.example.exchangerates.service.ExchangeRateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
public class ExchangeRateController {
    private final ExchangeRateService service;

    public ExchangeRateController(ExchangeRateService service) {
        this.service = service;
    }

    @GetMapping("/api/rates")
    public List<BankExchangeRate> getRates(
        @RequestParam("usedb") String useDb
    ) {
        if (!"true".equals(useDb) && !"false".equals(useDb)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "usedb must be true or false"
            );
        }

        return service.getRates(Boolean.parseBoolean(useDb));
    }
}