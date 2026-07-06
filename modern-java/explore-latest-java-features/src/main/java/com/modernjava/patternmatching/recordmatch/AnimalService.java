package com.modernjava.patternmatching.recordmatch;

public class AnimalService {

    public String retrieveName(Animal animal) {
        return switch (animal) {
            case Cat cat -> cat.name();
            case Dog dog -> dog.name();
            case null -> ""; // This handles the null pointer exception.
        };

    }

    public String retrieveNamePatternMatching(Animal animal) {
        return switch (animal) {
            case Cat(var name, var _) -> name;
            case Dog(var name, var _) -> name;
            case null -> ""; // This handles the null pointer exception.
        };
    }

    public String retrieveNameGuardedPatternMatching(Animal animal) {
        return switch (animal) {
            case Cat(var name, var _) when name == null -> "";
            case Cat(var name, var _) -> name;
            case Dog(var name, var _) when name == null -> "";
            case Dog(var name, var _) -> name;
            case null -> ""; // This handles the null pointer exception.
        };
    }

}
