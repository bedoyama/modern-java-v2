package com.modernjava.unnamedvariables;

import java.util.List;
import java.util.Map;

/**
 * Advanced examples of unnamed variables in Java
 * <p>
 * Shows best practices and various scenarios where unnamed variables
 * improve code readability and express intent clearly.
 */
public class UnnamedVariables {


    private static Integer getInteger(String s) {
        try {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException | ArithmeticException _) {
                System.out.println("Failed to parse: " + s);
                return null;
            }
        } catch (Exception _) {
            return null;
        }
    }

    private static Integer getIntegerV2(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception _) {
            System.out.println("Failed to parse: " + s);
            return null;
        }
    }

    public static void demonstrateExceptionHandling() {
        System.out.println("=== Multiple Unnamed Variables ===");

        // Nested try-catch with multiple exceptions we don't care about
        var operations = List.of("123", "divide", "10");
        var intList = operations.stream()
            .map(s -> getInteger(s))
            // .map(UnnamedVariables::getInteger)
            .toList();

        System.out.println("Parsed integers: " + intList);
    }

    private static void demonstrateLambdaWithUnnamed() {
        System.out.println("Slide 2: lambda parameters with unnamed variable");

        Map<String, Integer> freq = Map.of("a", 1, "b", 2, "c", 3);
        freq.forEach((k, _) -> System.out.println("key: " + k));
    }

    public static void main(String[] args) {
        demonstrateExceptionHandling();
        demonstrateLambdaWithUnnamed();
    }

}
