package com.example.exchangerates.client;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BankExchangeRate(
    String shortName,
    LocalDateTime validFrom,
    String name,
    String country,
    BigDecimal move,
    Integer amount,

    BigDecimal valBuy,
    BigDecimal valSell,
    BigDecimal valMid,

    BigDecimal currBuy,
    BigDecimal currSell,
    BigDecimal currMid,

    Integer version,
    BigDecimal cnbMid,
    BigDecimal ecbMid
) {}