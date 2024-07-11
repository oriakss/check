# Check Application

This Java application is designed to generate a check when purchasing groceries in a store.

## Installation

1. Clone the repository:
```bash
git clone https://github.com/oriakss/check.git
```

## Usage

The application is launched from the command line with the following parameters: product ID and quantity, discount card number consisting of only 4 digits (if it is assumed that there is no discount card, then instead of the number you must write the word “none”), debit card balance.

##### Examples
- **java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java 3-1 2-5 5-1 discountCard=1111 balanceDebitCard=100**
- **java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java 10-5 discountCard=none balanceDebitCard=-350**

## Technologies Used

- Java 21
