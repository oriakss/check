package ru.clevertec.check.repositories;

import ru.clevertec.check.models.DiscountCard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;

public class DiscountCardRepositoryImpl implements DiscountCardRepository {

    private static DiscountCardRepository discountCardRepository;
    private final File discountCardsFile = Path.of("./src/main/resources/discountCards.csv").toFile();
    private final List<DiscountCard> discountCardList = new ArrayList<>();

    @Override
    public List<DiscountCard> readDiscountCards() {
        try (BufferedReader reader = new BufferedReader(new FileReader(discountCardsFile))) {
            reader.lines()
                    .skip(1)
                    .forEach((discountCard) -> {
                        String[] discountCardProperties = discountCard.split(";");
                        discountCardList.add(new DiscountCard(
                                valueOf(discountCardProperties[0]),
                                valueOf(discountCardProperties[1]),
                                valueOf(discountCardProperties[2])));
                    });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return discountCardList;
    }

    private DiscountCardRepositoryImpl() {
    }

    public static DiscountCardRepository getInstance() {
        if (discountCardRepository == null) {
            discountCardRepository = new DiscountCardRepositoryImpl();
        }
        return discountCardRepository;
    }
}