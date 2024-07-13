package ru.clevertec.check.utils;

public final class Constants {

    public static final String PRODUCT_ID_AND_QUANTITY_REGEX = "^[0-9]{1,}[-]{1}[0-9]{1,2}$";
    public static final String CORRECT_DISCOUNT_CARD_REGEX = "^discountCard=[0-9]{4}$";
    public static final String SOME_DISCOUNT_CARD_REGEX = "^(discountCard=)[\\w]{0,}$";
    public static final String NONE_DISCOUNT_CARD_REGEX = "^discountCard=(NONE|none)$";
    public static final String BALANCE_DEBIT_CARD_REGEX = "^balanceDebitCard=-?[0-9]+[.]?[0-9]+$";
    public static final String INCORRECT_INPUT_PRODUCT_ID_AND_QUANTITY_MESSAGE = "Incorrect input product id and quantity!";
    public static final String INCORRECT_QUANTITY_MESSAGE = " is greater than the available quantity product with ID ";
    public static final String AVAILABLE_QUANTITY_MESSAGE = ". Available quantity of product - ";
    public static final String PRODUCT_QUANTITY_WITH_ID_MESSAGE = "Product quantity with id ";
    public static final String ZERO_QUANTITY_MESSAGE = " should be more, than 0!";
    public static final String PRODUCT_ID_DOES_NOT_EXIST_MESSAGE = "There is no product with ID ";
    public static final String INCORRECT_INPUT_DISCOUNT_CARD_MESSAGE = "Incorrect input discount card!";
    public static final String INCORRECT_INPUT_BALANCE_DEBIT_CARD_MESSAGE = "Incorrect input balance debit card!";
    public static final String INCORRECT_INPUT_MESSAGE = "Incorrect input!";
    public static final String NOT_ENOUGH_MONEY_MESSAGE = "Not enough money on the debit card!";
    public static final String DATE_TIME_STR = "Date;Time\n";
    public static final String PRODUCTS_PARAMS_STR = "QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL\n";
    public static final String DISCOUNT_CARD_PARAMS_STR = "\nDISCOUNT CARD;DISCOUNT PERCENTAGE\n";
    public static final String TOTAL_PARAMS_STR = "\nTOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n";
    public static final String BAD_REQUEST_STR = "ERROR\nBAD REQUEST\n";
    public static final String NOT_ENOUGH_MONEY_STR = "ERROR\nNOT ENOUGH MONEY\n";
    public static final String SEMICOLON_STR = ";";
    public static final String DOLLAR_SYMBOL = "$";
    public static final String WRONG_QUANTITY_STR = "0";
    public static final String DATE_PATTERN = "dd.MM.yyyy";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final int DISCOUNT_CARD_NUM_BEGIN_INDEX = 13;
    public static final int BALANCE_DEBIT_CARD_BEGIN_INDEX = 17;
    public static final int WHOLESALE_QUANTITY = 5;
    public static final int WHOLESALE_DISCOUNT = 10;
    public static final int DEFAULT_DISCOUNT = 2;
    public static final int NO_DISCOUNT = 0;
    public static final int PRODUCT_ID_INDEX = 0;
    public static final int PRODUCT_QUANTITY_INDEX = 1;
    public static final int PRODUCT_ID_AND_QUANTITY_ARR_LENGTH = 2;
}