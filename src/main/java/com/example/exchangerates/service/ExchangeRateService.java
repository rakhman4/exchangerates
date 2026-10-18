package com.example.exchangerates.service;

import com.example.exchangerates.client.BankExchangeRate;
import com.example.exchangerates.client.BankRatesClient;
import com.example.exchangerates.data.ExchangeRateEntity;
import com.example.exchangerates.data.ExchangeRateRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExchangeRateService {
    private final BankRatesClient bankRatesClient;
    private final ExchangeRateRepository repository;

    public ExchangeRateService(
        BankRatesClient bankRatesClient,
        ExchangeRateRepository repository
    ) {
        this.bankRatesClient = bankRatesClient;
        this.repository = repository;
    }

    public List<BankExchangeRate> getRates(boolean useDb) {
        if (useDb) {
            return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
        }

        List<BankExchangeRate> rates = bankRatesClient.fetchRates();

        List<ExchangeRateEntity> entities = rates.stream()
            .map(this::toEntity)
            .toList();

        repository.replaceAll(entities);

        return rates;
    }

    private ExchangeRateEntity toEntity(BankExchangeRate rate) {
        ExchangeRateEntity entity = new ExchangeRateEntity();

        entity.setShortName(rate.shortName());
        entity.setValidFrom(rate.validFrom());
        entity.setName(rate.name());
        entity.setCountry(rate.country());
        entity.setMove(rate.move());
        entity.setAmount(rate.amount());
        entity.setValBuy(rate.valBuy());
        entity.setValSell(rate.valSell());
        entity.setValMid(rate.valMid());
        entity.setCurrBuy(rate.currBuy());
        entity.setCurrSell(rate.currSell());
        entity.setCurrMid(rate.currMid());
        entity.setVersion(rate.version());
        entity.setCnbMid(rate.cnbMid());
        entity.setEcbMid(rate.ecbMid());

        return entity;
    }

    private BankExchangeRate toResponse(ExchangeRateEntity entity) {
        return new BankExchangeRate(
            entity.getShortName(),
            entity.getValidFrom(),
            entity.getName(),
            entity.getCountry(),
            entity.getMove(),
            entity.getAmount(),
            entity.getValBuy(),
            entity.getValSell(),
            entity.getValMid(),
            entity.getCurrBuy(),
            entity.getCurrSell(),
            entity.getCurrMid(),
            entity.getVersion(),
            entity.getCnbMid(),
            entity.getEcbMid()
        );
    }
}