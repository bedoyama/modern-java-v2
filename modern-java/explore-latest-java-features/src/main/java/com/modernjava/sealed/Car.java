package com.modernjava.sealed;

public sealed class Car extends Vehicle permits ElectricCar, GasolineCar {

    @Override
    public String drive() {
        return "Car";
    }
}
