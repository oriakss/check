package ru.clevertec.check.utils;

public final class Constants {

    public static final String PRODUCT_ID_AND_QUANTITY_REGEX = "^[0-9-0-9]{3,5}$";
    public static final String CORRECT_DISCOUNT_CARD_REGEX = "^discountCard=[0-9]{4}$";
    public static final String SOME_DISCOUNT_CARD_REGEX = "^(discountCard=)[\\w]{0,}$";
    public static final String NONE_DISCOUNT_CARD_REGEX = "^discountCard=(NONE|none)$";
    public static final String BALANCE_DEBIT_CARD_REGEX = "^balanceDebitCard=-?[0-9]{1,100}[.]?[0-9]{1,100}$";
    public static final String INCORRECT_INPUT_PRODUCT_ID_AND_QUANTITY_MESSAGE = "Incorrect input product id and quantity!";
    public static final String INCORRECT_INPUT_DISCOUNT_CARD_MESSAGE = "Incorrect input discount card!";
    public static final String INCORRECT_INPUT_BALANCE_DEBIT_CARD_MESSAGE = "Incorrect input balance debit card!";
    public static final String INCORRECT_INPUT_MESSAGE = "Incorrect input!";
    public static final String DATE_TIME_STR = "Date;Time\n";
    public static final String PRODUCTS_PARAMS_STR = "QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL\n";
    public static final String TOTAL_PARAMS_STR = "\nTOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n";
    public static final String BAD_REQUEST_STR = "ERROR\nBAD REQUEST\n";
    public static final String SEMICOLON_STR = ";";
    public static final int DISCOUNT_CARD_NUM_BEGIN_INDEX = 13;
    public static final int BALANCE_DEBIT_CARD_BEGIN_INDEX = 17;
    public static final int WHOLESALE_QUANTITY = 5;
    public static final int WHOLESALE_DISCOUNT = 10;
}