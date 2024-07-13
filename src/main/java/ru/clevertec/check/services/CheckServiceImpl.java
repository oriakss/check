package ru.clevertec.check.services;

import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;
import ru.clevertec.check.models.*;
import ru.clevertec.check.repositories.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.regex.Pattern.matches;
import static ru.clevertec.check.utils.Constants.*;

public class CheckServiceImpl implements CheckService {

    private static CheckService checkService;
    private static final ProductRepository productRepository = ProductRepositoryImpl.getInstance();
    private static final DiscountCardRepository discountCardRepository = DiscountCardRepositoryImpl.getInstance();
    private static final CheckRepository checkRepository = CheckRepositoryImpl.getInstance();
    private final List<Product> productList = productRepository.readProducts();
    private final List<DiscountCard> discountCardList = discountCardRepository.readDiscountCards();
    private final List<Product> productToPurchaseList = new ArrayList<>();

    @Override
    public void printCheck(String[] parameters) {
        Purchase purchase = readPurchaseParameters(parameters);
        String check = createCheck(purchase);
        System.out.println(check);
        checkRepository.printCheck(check);
    }

    private String createCheck(Purchase purchase) {
        BigDecimal balanceDebitCard = purchase.getBalanceDebitCard();
        BigDecimal totalProductsPrice = new BigDecimal(0);
        BigDecimal totalDiscount = new BigDecimal(0);
        BigDecimal totalProductsPriceWithDiscount;
        DiscountCard discountCard = purchase.getDiscountCard();
        LocalDateTime dateTime = LocalDateTime.now();

        StringBuilder checkDateTime = new StringBuilder(DATE_TIME_STR);
        StringBuilder checkProductsParams = new StringBuilder(PRODUCTS_PARAMS_STR);
        StringBuilder checkTotalParams = new StringBuilder(TOTAL_PARAMS_STR);
        String checkDiscountCard = DISCOUNT_CARD_PARAMS_STR + discountCard.getNumber() + SEMICOLON_STR
                + discountCard.getDiscountAmount() + "%\n";

        checkDateTime.append(dateTime.toLocalDate().format(DateTimeFormatter.ofPattern(DATE_PATTERN)))
                .append(SEMICOLON_STR)
                .append(dateTime.toLocalTime().format(DateTimeFormatter.ofPattern(TIME_PATTERN)))
                .append("\n\n");

        for (Product product : productToPurchaseList) {
            Integer quantity = product.getQuantityToPurchase();
            BigDecimal price = product.getPrice();
            BigDecimal totalPrice = price.multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.CEILING);
            String description = product.getDescription();
            BigDecimal discount;

            if (product.isWholesale() && quantity >= WHOLESALE_QUANTITY) {
                discount = price.subtract(price.multiply(BigDecimal.valueOf((double) WHOLESALE_DISCOUNT / 100)));
            } else if (discountCard.getDiscountAmount() != NO_DISCOUNT) {
                discount = price.subtract(price.multiply(BigDecimal.valueOf((double) discountCard.getDiscountAmount() / 100)));
            } else {
                discount = BigDecimal.valueOf(NO_DISCOUNT);
            }

            totalProductsPrice = totalProductsPrice.add(totalPrice);
            totalDiscount = totalDiscount.add(discount);

            checkProductsParams.append(quantity).append(SEMICOLON_STR)
                    .append(description).append(SEMICOLON_STR)
                    .append(price).append(DOLLAR_SYMBOL).append(SEMICOLON_STR)
                    .append(discount.setScale(2, RoundingMode.CEILING)).append(DOLLAR_SYMBOL).append(SEMICOLON_STR)
                    .append(totalPrice).append(DOLLAR_SYMBOL).append('\n');
        }

        totalProductsPriceWithDiscount = totalProductsPrice.subtract(totalDiscount);

        if (balanceDebitCard.compareTo(totalProductsPriceWithDiscount) < 0) {
            checkRepository.printCheck(NOT_ENOUGH_MONEY_STR);
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_MESSAGE);
        }

        checkTotalParams.append(totalProductsPrice.setScale(2, RoundingMode.CEILING))
                .append(DOLLAR_SYMBOL).append(SEMICOLON_STR)
                .append(totalDiscount.setScale(2, RoundingMode.CEILING))
                .append(DOLLAR_SYMBOL).append(SEMICOLON_STR)
                .append(totalProductsPriceWithDiscount.setScale(2, RoundingMode.CEILING))
                .append(DOLLAR_SYMBOL).append('\n');

        return checkDateTime.append(checkProductsParams).append(checkDiscountCard).append(checkTotalParams).toString();
    }

    private Purchase readPurchaseParameters(String[] parameters) {
        DiscountCard discountCard;
        BigDecimal balanceDebitCard;
        int i;

        for (i = 0; i < parameters.length; i++) {
            if (matches(PRODUCT_ID_AND_QUANTITY_REGEX, parameters[i])) {

                String[] productIdAndQuantity = parameters[i].split("-");
                if (productIdAndQuantity.length != PRODUCT_ID_AND_QUANTITY_ARR_LENGTH) {
                    checkRepository.printCheck(BAD_REQUEST_STR);
                    throw  new BadRequestException(INCORRECT_INPUT_PRODUCT_ID_AND_QUANTITY_MESSAGE);
                }
                if (productIdAndQuantity[PRODUCT_QUANTITY_INDEX].equals(WRONG_QUANTITY_STR)) {
                    checkRepository.printCheck(BAD_REQUEST_STR);
                    throw  new BadRequestException(PRODUCT_QUANTITY_WITH_ID_MESSAGE
                            + productIdAndQuantity[PRODUCT_ID_INDEX] + ZERO_QUANTITY_MESSAGE);
                }

                Integer productId = Integer.valueOf(productIdAndQuantity[PRODUCT_ID_INDEX]);
                Integer productQuantity = Integer.valueOf(productIdAndQuantity[PRODUCT_QUANTITY_INDEX]);

                Product product = productList.stream()
                        .filter(someProduct -> Objects.equals(someProduct.getId(), productId))
                        .findFirst()
                        .orElseThrow(() -> {
                            checkRepository.printCheck(BAD_REQUEST_STR);
                            return new BadRequestException(PRODUCT_ID_DOES_NOT_EXIST_MESSAGE + productId);
                        });

                if (productQuantity > product.getAvailableQuantity()) {
                    checkRepository.printCheck(BAD_REQUEST_STR);
                    throw new BadRequestException(productQuantity + INCORRECT_QUANTITY_MESSAGE + productId
                            + AVAILABLE_QUANTITY_MESSAGE + product.getAvailableQuantity());
                }

                product.setAvailableQuantity(product.getAvailableQuantity() - productQuantity);

                Optional<Product> optionalProductToPurchase = productToPurchaseList.stream()
                        .filter(someProductToPurchase -> Objects.equals(someProductToPurchase.getId(), product.getId()))
                        .findFirst();

                if (optionalProductToPurchase.isPresent()) {
                    Product productToPurchase = optionalProductToPurchase.get();
                    productToPurchase.setQuantityToPurchase(productToPurchase.getQuantityToPurchase() + productQuantity);
                } else {
                    product.setQuantityToPurchase(productQuantity);
                    productToPurchaseList.add(product);
                }
            } else if (matches(SOME_DISCOUNT_CARD_REGEX, parameters[i])) {
                break;
            } else {
                checkRepository.printCheck(BAD_REQUEST_STR);
                throw  new BadRequestException(INCORRECT_INPUT_PRODUCT_ID_AND_QUANTITY_MESSAGE);
            }
        }

        if (matches(CORRECT_DISCOUNT_CARD_REGEX, parameters[i])) {
            Integer discountCardNumber = Integer.valueOf(parameters[i].substring(DISCOUNT_CARD_NUM_BEGIN_INDEX));
            Optional<DiscountCard> optionalDiscountCard = discountCardList.stream()
                    .filter(someDiscountCard -> Objects.equals(someDiscountCard.getNumber(), discountCardNumber))
                    .findFirst();
            discountCard = optionalDiscountCard.orElseGet(() -> new DiscountCard(discountCardNumber, DEFAULT_DISCOUNT));
            i++;
        } else if (matches(NONE_DISCOUNT_CARD_REGEX, parameters[i])) {
            discountCard = new DiscountCard(NO_DISCOUNT);
            i++;
        } else {
            System.out.println();
            checkRepository.printCheck(BAD_REQUEST_STR);
            throw  new BadRequestException(INCORRECT_INPUT_DISCOUNT_CARD_MESSAGE);
        }

        if (i < parameters.length && matches(BALANCE_DEBIT_CARD_REGEX, parameters[i])) {
            balanceDebitCard = new BigDecimal(parameters[i].substring(BALANCE_DEBIT_CARD_BEGIN_INDEX));
            i++;
        } else {
            System.out.println();
            checkRepository.printCheck(BAD_REQUEST_STR);
            throw  new BadRequestException(INCORRECT_INPUT_BALANCE_DEBIT_CARD_MESSAGE);
        }

        if (i < parameters.length) {
            checkRepository.printCheck(BAD_REQUEST_STR);
            throw  new BadRequestException(INCORRECT_INPUT_MESSAGE);
        }

        return new Purchase(discountCard, balanceDebitCard);
    }

    private CheckServiceImpl() {
    }

    public static CheckService getInstance() {
        if (checkService == null) {
            checkService = new CheckServiceImpl();
        }
        return checkService;
    }
}