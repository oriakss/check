package ru.clevertec.check.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private final Integer id;
    private final String description;
    private final BigDecimal price;
    private Integer availableQuantity;
    private Integer quantityToPurchase;
    private final boolean wholesale;

    public Product(Integer id, String description, BigDecimal price, Integer availableQuantity, boolean wholesale) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.availableQuantity = availableQuantity;
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

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public Integer getQuantityToPurchase() {
        return quantityToPurchase;
    }

    public boolean isWholesale() {
        return wholesale;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public void setQuantityToPurchase(Integer quantityToPurchase) {
        this.quantityToPurchase = quantityToPurchase;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", availableQuantity=" + availableQuantity +
                ", quantityToPurchase=" + quantityToPurchase +
                ", wholesale=" + wholesale +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return wholesale == product.wholesale && Objects.equals(id, product.id)
                && Objects.equals(description, product.description) && Objects.equals(price, product.price)
                && Objects.equals(availableQuantity, product.availableQuantity)
                && Objects.equals(quantityToPurchase, product.quantityToPurchase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, availableQuantity, quantityToPurchase, wholesale);
    }
}