package ru.clevertec.check.services;

import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.models.*;
import ru.clevertec.check.repositories.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private final List<ProductToPurchase> productToPurchaseList = new ArrayList<>();

    @Override
    public void printCheck(String[] parameters) {
        Purchase purchase = readPurchaseParameters(parameters);
        String check = createCheck(purchase);
        System.out.println(check);
        checkRepository.printCheck(check);
    }

    private String createCheck(Purchase purchase) {
        BigDecimal totalProductsPrice = new BigDecimal(0);
        BigDecimal totalDiscount = new BigDecimal(0);
        BigDecimal totalProductsPriceWithDiscount;
        Integer discountAmount = purchase.getDiscountAmount();
        LocalDateTime dateTime = LocalDateTime.now();

        StringBuilder checkDateTime = new StringBuilder(DATE_TIME_STR);
        StringBuilder checkProductsParams = new StringBuilder(PRODUCTS_PARAMS_STR);
        StringBuilder checkTotalParams = new StringBuilder(TOTAL_PARAMS_STR);

        checkDateTime.append(dateTime.toLocalDate()).append(SEMICOLON_STR).append(dateTime.toLocalTime()).append("\n\n");

        for (ProductToPurchase product : productToPurchaseList) {
            Integer quantity = product.getQuantity();
            BigDecimal price = product.getPrice();
            BigDecimal totalPrice = price.multiply(new BigDecimal(quantity));
            String description = product.getDescription();
            BigDecimal discount;

            if (product.isWholesale() && quantity >= WHOLESALE_QUANTITY) {
                discount = price.subtract(price.multiply(BigDecimal.valueOf((double) WHOLESALE_DISCOUNT / 100)));
            } else if (discountAmount != 0) {
                discount = price.subtract(price.multiply(BigDecimal.valueOf((double) discountAmount / 100)));
            } else {
                discount = BigDecimal.valueOf(0);
            }

            totalProductsPrice = totalProductsPrice.add(totalPrice);
            totalDiscount = totalDiscount.add(discount);

            checkProductsParams.append(quantity).append(SEMICOLON_STR)
                    .append(description).append(SEMICOLON_STR)
                    .append(price).append(SEMICOLON_STR)
                    .append(discount).append(SEMICOLON_STR)
                    .append(totalPrice).append('\n');
        }
        totalProductsPriceWithDiscount = totalProductsPrice.subtract(totalDiscount);
        checkTotalParams.append(totalProductsPrice).append(SEMICOLON_STR)
                .append(totalDiscount).append(SEMICOLON_STR)
                .append(totalProductsPriceWithDiscount).append('\n');

        return checkDateTime.append(checkProductsParams).append(checkTotalParams).toString();
    }

    private Purchase readPurchaseParameters(String[] parameters) {
        DiscountCard discountCard;
        BigDecimal balanceDebitCard;
        int i;

        for (i = 0; i < parameters.length; i++) {
            if (matches(PRODUCT_ID_AND_QUANTITY_REGEX, parameters[i])) {

                String[] productIdAndQuantity = parameters[i].split("-");
                if (productIdAndQuantity.length != 2) {
                    checkRepository.printCheck(BAD_REQUEST_STR);
                    throw  new BadRequestException(INCORRECT_INPUT_PRODUCT_ID_AND_QUANTITY_MESSAGE);
                }
                if (productIdAndQuantity[1].equals("0")) {
                    checkRepository.printCheck(BAD_REQUEST_STR);
                    throw  new BadRequestException("Product amount with id " + productIdAndQuantity[0] + " should be more, then 0!");
                }

                Integer productId = Integer.valueOf(productIdAndQuantity[0]);
                Integer productQuantity = Integer.valueOf(productIdAndQuantity[1]);

                Product product = productList.stream()
                        .filter(someProduct -> Objects.equals(someProduct.getId(), productId))
                        .findFirst()
                        .orElseThrow(() -> {
                            checkRepository.printCheck(BAD_REQUEST_STR);
                            return new BadRequestException(INCORRECT_INPUT_PRODUCT_ID_AND_QUANTITY_MESSAGE);
                        });

                Optional<ProductToPurchase> optionalProductToPurchase = productToPurchaseList.stream()
                        .filter(someProductToPurchase -> Objects.equals(someProductToPurchase.getId(), product.getId()))
                        .findFirst();

                if (optionalProductToPurchase.isPresent()) {
                    ProductToPurchase productToPurchase = optionalProductToPurchase.get();
                    productToPurchase.setQuantity(productToPurchase.getQuantity() + productQuantity);
                } else {
                    ProductToPurchase productToPurchase = new ProductToPurchase();
                    productToPurchase.setId(product.getId());
                    productToPurchase.setDescription(product.getDescription());
                    productToPurchase.setPrice(product.getPrice());
                    productToPurchase.setQuantity(productQuantity);
                    productToPurchase.setWholesale(product.isWholesale());
                    productToPurchaseList.add(productToPurchase);
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
            discountCard = optionalDiscountCard.orElseGet(() -> new DiscountCard(discountCardNumber, 2));
            i++;
        } else if (matches(NONE_DISCOUNT_CARD_REGEX, parameters[i])) {
            discountCard = new DiscountCard(0);
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

        return new Purchase(discountCard.getDiscountAmount(), balanceDebitCard);
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