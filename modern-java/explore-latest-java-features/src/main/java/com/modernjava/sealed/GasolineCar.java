package com.modernjava.sealed;

public final class GasolineCar extends Car {
    // This is optional
    @Override
    public String drive() {
        return "Gas";
    }
}
