package com.example.exchangerates.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRateEntity {
    @Id
    private String shortName;

    private LocalDateTime validFrom;
    private String name;
    private String country;

    @Column(columnDefinition = "numeric")
    private BigDecimal move;

    @Column(nullable = false)
    private Integer amount;

    @Column(columnDefinition = "numeric")
    private BigDecimal valBuy;

    @Column(columnDefinition = "numeric")
    private BigDecimal valSell;

    @Column(columnDefinition = "numeric")
    private BigDecimal valMid;

    @Column(columnDefinition = "numeric")
    private BigDecimal currBuy;

    @Column(columnDefinition = "numeric")
    private BigDecimal currSell;

    @Column(columnDefinition = "numeric")
    private BigDecimal currMid;

    private Integer version;

    @Column(columnDefinition = "numeric")
    private BigDecimal cnbMid;

    @Column(columnDefinition = "numeric")
    private BigDecimal ecbMid;

    public BigDecimal getCurrSell() {
        return currSell;
    }

    public void setCurrSell(BigDecimal currSell) {
        this.currSell = currSell;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getMove() {
        return move;
    }

    public void setMove(BigDecimal move) {
        this.move = move;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getValBuy() {
        return valBuy;
    }

    public void setValBuy(BigDecimal valBuy) {
        this.valBuy = valBuy;
    }

    public BigDecimal getValSell() {
        return valSell;
    }

    public void setValSell(BigDecimal valSell) {
        this.valSell = valSell;
    }

    public BigDecimal getValMid() {
        return valMid;
    }

    public void setValMid(BigDecimal valMid) {
        this.valMid = valMid;
    }

    public BigDecimal getCurrBuy() {
        return currBuy;
    }

    public void setCurrBuy(BigDecimal currBuy) {
        this.currBuy = currBuy;
    }

    public BigDecimal getCurrMid() {
        return currMid;
    }

    public void setCurrMid(BigDecimal currMid) {
        this.currMid = currMid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public BigDecimal getCnbMid() {
        return cnbMid;
    }

    public void setCnbMid(BigDecimal cnbMid) {
        this.cnbMid = cnbMid;
    }

    public BigDecimal getEcbMid() {
        return ecbMid;
    }

    public void setEcbMid(BigDecimal ecbMid) {
        this.ecbMid = ecbMid;
    }
}