package com.modernjava.records;

import java.math.BigDecimal;
import java.util.Objects;

public record Product(String name, BigDecimal cost, String type) {

    public Product {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        if (cost == null || cost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product cost cannot be null or negative");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Product type cannot be null or blank");
        }
    }

    public Product(String name, BigDecimal cost) {
        this(name, cost, "GENERAL");
    }

    // You can optionally override equals and hashCode methods if needed
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(type, product.type) && Objects.equals(cost, product.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost, type);
    }
}
