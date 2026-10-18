package com.example.exchangerates.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import java.util.List;

@Component
public class BankRatesClient {
    private final RestClient restClient;
    private final String apiKey;

    public BankRatesClient(
        RestClient.Builder builder,
        @Value("${bank.api.base-url}") String baseUrl,
        @Value("${bank.api.key}") String apiKey
    ) {
        this.restClient = builder.baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public List<BankExchangeRate> fetchRates() {
        BankExchangeRate[] rates = restClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/rates/exchangerates")
                .queryParam("web-api-key", apiKey)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(BankExchangeRate[].class);

        if (rates == null || rates.length == 0) {
            throw new RestClientException(
                "Bank API returned no exchange rates"
            );
        }

        return List.of(rates);
    }
}