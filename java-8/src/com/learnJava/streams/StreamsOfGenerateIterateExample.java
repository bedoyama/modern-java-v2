package com.learnJava.streams;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StreamsOfGenerateIterateExample {

    public static void main(String[] args) {

        System.out.println("=== Stream.of() ===");
        Stream.of("adam", "dan", "jenny", "dave")
                .forEach(System.out::println);

        System.out.println("\n=== Stream.iterate() - Powers of 2 ===");
        List<Integer> powersOfTwo = Stream.iterate(1, x -> x * 2)
                .limit(10)
                .toList();
        System.out.println(powersOfTwo);

        System.out.println("\n=== Stream.iterate() - Fibonacci ===");
        List<Integer> fibonacci = Stream.iterate(new int[]{0, 1},
                        arr -> new int[]{arr[1], arr[0] + arr[1]})
                .limit(10)
                .map(arr -> arr[0])
                .toList();
        System.out.println(fibonacci);

        System.out.println("\n=== Stream.iterate() with Predicate (Java 9+) ===");
        List<Integer> numbersUnder1000 = Stream.iterate(2, n -> n < 1000, n -> n * 2)
                .toList();
        System.out.println(numbersUnder1000);

        System.out.println("\n=== Stream.generate() Examples ===");

        // 1. Random (existing)
        Supplier<Integer> randomSupplier = new Random()::nextInt;
        System.out.println("Random numbers: " +
                Stream.generate(randomSupplier).limit(5).toList());

        // 2. Good real-world example: Incremental ID generator
        Supplier<Integer> idGenerator = new Supplier<>() {
            private int counter = 100;
            @Override
            public Integer get() {
                return counter++;
            }
        };
        System.out.println("Generated IDs: " +
                Stream.generate(idGenerator).limit(5).toList());

        // 3. Date sequence generator
        Supplier<LocalDate> dateGenerator = () -> LocalDate.now().plusDays(1);
        System.out.println("Future dates: " +
                Stream.generate(dateGenerator).limit(5).toList());

        // 4. Constant value generator
        System.out.println("Constant values: " +
                Stream.generate(() -> "DEFAULT").limit(5).toList());
    }
}