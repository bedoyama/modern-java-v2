package com.modernjava.patternmatching;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatternMatchingExampleTest {

    PatternMatchingExample patternMatchingExample
            = new PatternMatchingExample();

    @ParameterizedTest
    @MethodSource("input")
    void pattern(Object value, String expectedResult) {

        var output = patternMatchingExample.pattern(value);
        assertEquals(expectedResult, output);
    }

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of("Dilip", "String of length:5"),
                Arguments.of(1, "Integer:1"),
                Arguments.of(null, "Not a String or Integer")
        );
    }

    @ParameterizedTest
    @MethodSource("inputWithBindingVariable")
    void patternWithBindingVariable(Object value, String expectedResult) {

        var output = patternMatchingExample.patternWithBindingVariable(value);
        assertEquals(expectedResult, output);
    }

    private static Stream<Arguments> inputWithBindingVariable() {
        return Stream.of(
                Arguments.of("Dilip", "String of length:5"),
                Arguments.of(1, "Integer:1"),
                Arguments.of(null, "Not a String or Integer with binding variable")
        );
    }

    @ParameterizedTest
    @MethodSource("inputWithGuard")
    void patternWithGuard(Object value, String expectedResult) {

        var output = patternMatchingExample.patternWithGuard(value);
        assertEquals(expectedResult, output);
    }

    private static Stream<Arguments> inputWithGuard() {
        return Stream.of(
                Arguments.of("Dilip", "Not a String or Integer with guard"),
                Arguments.of("Dilip Kumar", "String of length greater than 5:11"),
                Arguments.of(1, "Not a String or Integer with guard"),
                Arguments.of(11, "Integer greater than 10:11"),
                Arguments.of(null, "Not a String or Integer with guard")
        );
    }

    @ParameterizedTest
    @MethodSource("inputWithMatchingSwitch")
    void patternMatchingWithSwitch(Object value, String expectedResult) {

        var output = patternMatchingExample.patternMatchingWithSwitch(value);
        assertEquals(expectedResult, output);
    }

    private static Stream<Arguments> inputWithMatchingSwitch() {
        return Stream.of(
                Arguments.of("Dilip", "String of length:5"),
                Arguments.of(1, "Integer:1"),
                Arguments.of(1.0, "Number:1.0"),
                Arguments.of(null, "Not a String or Integer with switch")
        );
    }
}