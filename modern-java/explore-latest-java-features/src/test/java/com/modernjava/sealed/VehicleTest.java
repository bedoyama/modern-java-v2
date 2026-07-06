package com.modernjava.sealed;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void testVehicleHierarchy() {
        Vehicle car = new Car();
        Vehicle truck = new Truck();

        assertTrue(car instanceof Vehicle);
        assertTrue(truck instanceof Vehicle);
    }

    @Test
    void testDogNotAllowedToExtendVehicle() {
        // The following line would cause a compilation error if uncommented,
        // because Dog is not permitted to extend the sealed class Vehicle.
        // Dog dog = new Dog();

        // Instead, we can just assert that Dog is not a subclass of Vehicle.
        assertFalse(Vehicle.class.isAssignableFrom(Dog.class));
    }

    @Test
    void testDrive(){
        var car = new Car();
        var truck = new Truck();
        var electricCar = new ElectricCar();
        var gasolineCar = new GasolineCar();

        assertEquals("Car", car.drive());
        assertEquals("Truck", truck.drive());
        assertEquals("Electric", electricCar.drive());
        assertEquals("Gas", gasolineCar.drive());
    }
}