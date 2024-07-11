package ru.clevertec.check.repositories;

import ru.clevertec.check.models.DiscountCard;

import java.util.List;

public interface DiscountCardRepository {

    List<DiscountCard> readDiscountCards();
}