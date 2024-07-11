package ru.clevertec.check.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Purchase {

    private final Integer discountAmount;
    private final BigDecimal balanceDebitCard;

    public Purchase(Integer discountAmount, BigDecimal balanceDebitCard) {
        this.discountAmount = discountAmount;
        this.balanceDebitCard = balanceDebitCard;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getBalanceDebitCard() {
        return balanceDebitCard;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "discountAmount=" + discountAmount +
                ", balanceDebitCard=" + balanceDebitCard +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(discountAmount, purchase.discountAmount)
                && Objects.equals(balanceDebitCard, purchase.balanceDebitCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountAmount, balanceDebitCard);
    }
}