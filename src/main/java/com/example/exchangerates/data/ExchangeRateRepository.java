package com.example.exchangerates.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class ExchangeRateRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<ExchangeRateEntity> findAll() {
        return entityManager.createQuery(
            "select rate from ExchangeRateEntity rate",
            ExchangeRateEntity.class
        ).getResultList();
    }

    @Transactional
    public void replaceAll(List<ExchangeRateEntity> rates) {
        if (rates == null || rates.isEmpty()) {
            throw new IllegalArgumentException("Rates must not be empty");
        }

        entityManager.createQuery(
            "delete from ExchangeRateEntity"
        ).executeUpdate();

        entityManager.clear();

        for (ExchangeRateEntity rate : rates) {
            entityManager.persist(rate);
        }
    }
}