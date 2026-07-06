package com.modernjava.sealed;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @org.junit.jupiter.api.Test
    void testVehicleHierarchy() {
        Vehicle car = new Car();
        Vehicle truck = new Truck();

        assertTrue(car instanceof Vehicle);
        assertTrue(truck instanceof Vehicle);
    }

    @org.junit.jupiter.api.Test
    void testDogNotAllowedToExtendVehicle() {
        // The following line would cause a compilation error if uncommented,
        // because Dog is not permitted to extend the sealed class Vehicle.
        // Dog dog = new Dog();

        // Instead, we can just assert that Dog is not a subclass of Vehicle.
        assertFalse(Vehicle.class.isAssignableFrom(Dog.class));
    }

}