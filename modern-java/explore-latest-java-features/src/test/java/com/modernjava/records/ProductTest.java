package com.modernjava.records;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testProductRecord() {
        Product product = new Product("Laptop", new java.math.BigDecimal("999.99"), "Electronics");

        assertEquals("Laptop", product.name());
        assertEquals(new java.math.BigDecimal("999.99"), product.cost());
        assertEquals("Electronics", product.type());
    }

    @Test
    void testProductRecordWithDefaultType() {
        Product product = new Product("Laptop", new java.math.BigDecimal("999.99"));

        assertEquals("Laptop", product.name());
        assertEquals(new java.math.BigDecimal("999.99"), product.cost());
        assertEquals("GENERAL", product.type());
    }

    @Test
    void testProductNameCannotBeNull() {
        var exception = assertThrows(IllegalArgumentException.class,
            () -> new Product(null, new java.math.BigDecimal("999.99"), "Electronics"));

        assertEquals("Product name cannot be null or blank", exception.getMessage());
    }

    @Test
    void testProductCostCannotBeNull() {
        var exception = assertThrows(IllegalArgumentException.class,
            () -> new Product("Laptop", null, "Electronics"));

        assertEquals("Product cost cannot be null or negative", exception.getMessage());
    }

    @Test
    void testProductCostCannotBeNegative() {
        var exception = assertThrows(IllegalArgumentException.class,
            () -> new Product("Laptop", new java.math.BigDecimal("-1"), "Electronics"));

        assertEquals("Product cost cannot be null or negative", exception.getMessage());
    }

    @Test
    void testProductTypeCannotBeNull() {
        var exception = assertThrows(IllegalArgumentException.class,
            () -> new Product("Laptop", new java.math.BigDecimal("999.99"), null));

        assertEquals("Product type cannot be null or blank", exception.getMessage());
    }

    @Test
    void testProductComparison() {
        Product product1 = new Product("Laptop", new java.math.BigDecimal("999.99"), "Electronics");
        Product product2 = new Product("Laptop", new java.math.BigDecimal("999.99"), "Electronics");
        Product product3 = new Product("Smartphone", new java.math.BigDecimal("499.99"), "Electronics");

        assertEquals(product1, product2);
        assertNotEquals(product1, product3);
    }

}