package com.modernjava.sealed;

public sealed class Car extends Vehicle implements SmartMediaPlayer permits ElectricCar, GasolineCar {

    @Override
    public String drive() {
        return "Car";
    }

    @Override
    public void connectPhone() {
        // Implementation for connecting a phone
        System.out.println("Connecting phone to Car's media player...");
    }
}
