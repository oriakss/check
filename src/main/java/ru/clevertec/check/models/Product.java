package ru.clevertec.check.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private final Integer id;
    private final String description;
    private final BigDecimal price;
    private final Integer quantity;
    private final boolean wholesale;

    public Product(Integer id, String description, BigDecimal price, Integer quantity, boolean wholesale) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.wholesale = wholesale;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isWholesale() {
        return wholesale;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", wholesale=" + wholesale +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return wholesale == product.wholesale && Objects.equals(id, product.id)
                && Objects.equals(description, product.description)
                && Objects.equals(price, product.price) && Objects.equals(quantity, product.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, quantity, wholesale);
    }
}