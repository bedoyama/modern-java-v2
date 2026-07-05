package com.learnJava.numericstreams;

import java.util.List;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.toList;

public class NumericStreamMapExample {

    public static List<Integer> mapToObj() {
        return IntStream.rangeClosed(1, 5)
                .mapToObj(i -> i)                    // autoboxing
                .collect(toList());
    }

    public static List<String> mapToObjWithTransformation() {
        return IntStream.rangeClosed(1, 5)
                .mapToObj(i -> "Number-" + i)        // mapping to String
                .collect(toList());
    }

    public static double mapToDouble() {
        return IntStream.rangeClosed(1, 5)
                .mapToDouble(i -> i)                 // primitive double
                .sum();
    }

    public static double mapToDoubleWithCalculation() {
        return IntStream.rangeClosed(1, 5)
                .mapToDouble(i -> i * i)             // square each number
                .sum();
    }

    public static long mapToLong() {
        return IntStream.rangeClosed(1, 5)
                .mapToLong(i -> i)
                .sum();
    }

    public static void main(String[] args) {

        System.out.println("=== mapToObj() - Integer ===");
        System.out.println(mapToObj());

        System.out.println("=== mapToObj() - String transformation ===");
        System.out.println(mapToObjWithTransformation());

        System.out.println("=== mapToDouble() - Simple sum ===");
        System.out.println(mapToDouble());

        System.out.println("=== mapToDouble() - Sum of squares ===");
        System.out.println(mapToDoubleWithCalculation());

        System.out.println("=== mapToLong() ===");
        System.out.println(mapToLong());
    }
}