package com.learnJava.streams;

import java.time.LocalDate;
import java.util.stream.Stream;

public class StreamsIterateExample {

    public static void main(String[] args) {

        System.out.println("=== 1. Powers of 2 (Basic iterate) ===");
        Stream.iterate(1, x -> x * 2)
                .limit(10)
                .forEach(System.out::println);

        System.out.println("\n=== 2. Odd Numbers ===");
        Stream.iterate(1, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);

        System.out.println("\n=== 3. Fibonacci Sequence ===");
        Stream.iterate(new int[]{0, 1}, f -> new int[]{f[1], f[0] + f[1]})
                .limit(10)
                .map(f -> f[0])
                .forEach(System.out::println);

        System.out.println("\n=== 4. Dates (Next 7 days) ===");
        Stream.iterate(LocalDate.now(), date -> date.plusDays(1))
                .limit(7)
                .forEach(System.out::println);

        System.out.println("\n=== 5. Iterate with Predicate (Java 9+) ===");
        // This is the more advanced version with a stopping condition
        Stream.iterate(1,
                        n -> n < 1000,           // predicate - continue while true
                        n -> n * 2)              // next function
                .forEach(System.out::println);
    }
}