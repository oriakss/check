package ru.clevertec.check.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Purchase {

    private final DiscountCard discountCard;
    private final BigDecimal balanceDebitCard;

    public Purchase(DiscountCard discountCard, BigDecimal balanceDebitCard) {
        this.discountCard = discountCard;
        this.balanceDebitCard = balanceDebitCard;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public BigDecimal getBalanceDebitCard() {
        return balanceDebitCard;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "discountCard=" + discountCard +
                ", balanceDebitCard=" + balanceDebitCard +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(discountCard, purchase.discountCard) && Objects.equals(balanceDebitCard, purchase.balanceDebitCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountCard, balanceDebitCard);
    }
}