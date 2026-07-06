package com.modernjava.records;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @org.junit.jupiter.api.Test
    void testProductRecord() {
        Product product = new Product("Laptop", new java.math.BigDecimal("999.99"), "Electronics");

        assertEquals("Laptop", product.name());
        assertEquals(new java.math.BigDecimal("999.99"), product.cost());
        assertEquals("Electronics", product.type());
    }

}