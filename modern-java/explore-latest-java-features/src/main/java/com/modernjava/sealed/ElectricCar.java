package com.modernjava.sealed;

public final class ElectricCar extends Car {
    // This is optional
    @Override
    public String drive() {
        return "Electric";
    }
}
