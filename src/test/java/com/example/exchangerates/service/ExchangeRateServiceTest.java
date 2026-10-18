package com.example.exchangerates.service;

import com.example.exchangerates.client.BankExchangeRate;
import com.example.exchangerates.client.BankRatesClient;
import com.example.exchangerates.data.ExchangeRateEntity;
import com.example.exchangerates.data.ExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {
    @Mock
    private BankRatesClient bankRatesClient;

    @Mock
    private ExchangeRateRepository repository;

    @Captor
    private ArgumentCaptor<List<ExchangeRateEntity>> savedRates;

    private ExchangeRateService service;

    @BeforeEach
    void setUp() {
        service = new ExchangeRateService(bankRatesClient, repository);
    }

    @Test
    void readsStoredRatesWithoutCallingBank() {
        when(repository.findAll()).thenReturn(List.of(storedRate()));

        List<BankExchangeRate> result = service.getRates(true);

        assertThat(result).containsExactly(bankRate());
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(bankRatesClient);
    }

    @Test
    void fetchesSavesAndReturnsBankRates() {
        List<BankExchangeRate> bankRates = List.of(bankRate());
        when(bankRatesClient.fetchRates()).thenReturn(bankRates);

        List<BankExchangeRate> result = service.getRates(false);

        assertThat(result).isEqualTo(bankRates);
        verify(bankRatesClient).fetchRates();
        verify(repository).replaceAll(savedRates.capture());

        assertThat(savedRates.getValue()).hasSize(1);
        assertThat(savedRates.getValue().getFirst())
            .usingRecursiveComparison()
            .isEqualTo(storedRate());

        verifyNoMoreInteractions(bankRatesClient, repository);
    }

    private BankExchangeRate bankRate() {
        return new BankExchangeRate(
            "AUD",
            LocalDateTime.of(2022, 4, 26, 0, 0),
            "Dollar",
            "Australia",
            new BigDecimal("-0.1"),
            1,
            new BigDecimal("1.1"),
            new BigDecimal("1.2"),
            new BigDecimal("1.3"),
            new BigDecimal("2.1"),
            new BigDecimal("2.2"),
            new BigDecimal("2.3"),
            2,
            new BigDecimal("3.1"),
            new BigDecimal("3.2")
        );
    }

    private ExchangeRateEntity storedRate() {
        ExchangeRateEntity entity = new ExchangeRateEntity();
        entity.setShortName("AUD");
        entity.setValidFrom(LocalDateTime.of(2022, 4, 26, 0, 0));
        entity.setName("Dollar");
        entity.setCountry("Australia");
        entity.setMove(new BigDecimal("-0.1"));
        entity.setAmount(1);
        entity.setValBuy(new BigDecimal("1.1"));
        entity.setValSell(new BigDecimal("1.2"));
        entity.setValMid(new BigDecimal("1.3"));
        entity.setCurrBuy(new BigDecimal("2.1"));
        entity.setCurrSell(new BigDecimal("2.2"));
        entity.setCurrMid(new BigDecimal("2.3"));
        entity.setVersion(2);
        entity.setCnbMid(new BigDecimal("3.1"));
        entity.setEcbMid(new BigDecimal("3.2"));
        return entity;
    }
}