package ru.clevertec.check.models;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductToPurchase {

    private Integer id;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private boolean wholesale;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isWholesale() {
        return wholesale;
    }

    public void setWholesale(boolean wholesale) {
        this.wholesale = wholesale;
    }

    @Override
    public String toString() {
        return "ProductToPurchase{" +
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
        ProductToPurchase product = (ProductToPurchase) o;
        return wholesale == product.wholesale && Objects.equals(id, product.id)
                && Objects.equals(description, product.description)
                && Objects.equals(price, product.price)
                && Objects.equals(quantity, product.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, quantity, wholesale);
    }
}