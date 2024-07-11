package ru.clevertec.check.models;

import java.util.Objects;

public class DiscountCard {

    private Integer id;
    private Integer number;
    private final Integer discountAmount;

    public DiscountCard(Integer id, Integer number, Integer discountAmount) {
        this.id = id;
        this.number = number;
        this.discountAmount = discountAmount;
    }

    public DiscountCard(Integer number, Integer discountAmount) {
        this.number = number;
        this.discountAmount = discountAmount;
    }

    public DiscountCard(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "id=" + id +
                ", number=" + number +
                ", discountAmount=" + discountAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard that = (DiscountCard) o;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number)
                && Objects.equals(discountAmount, that.discountAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, discountAmount);
    }
}