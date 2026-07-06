package com.modernjava.sealed;

public sealed class Car extends Vehicle permits ElectricCar, GasolineCar {

}
