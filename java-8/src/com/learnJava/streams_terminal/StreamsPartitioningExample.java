package com.learnJava.streams_terminal;

import com.learnJava.data.Student;
import com.learnJava.data.StudentDataBase;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import static java.util.stream.Collectors.*;

public class StreamsPartitioningExample {

    public static void partitioningBy_1() {
        Predicate<Student> gpaPredicate = student -> student.getGpa() >= 3.8;

        Map<Boolean, List<Student>> studentMap = StudentDataBase.getAllStudents()
                .stream()
                .collect(partitioningBy(gpaPredicate));

        System.out.println("=== Partitioning by GPA >= 3.8 ===");
        printPartitionedMap(studentMap);
    }

    public static void partitioningBy_2() {
        Predicate<Student> gpaPredicate = student -> student.getGpa() >= 3.8;

        Map<Boolean, Set<Student>> studentMap = StudentDataBase.getAllStudents()
                .stream()
                .collect(partitioningBy(gpaPredicate, toSet()));

        System.out.println("=== Partitioning by GPA >= 3.8 (as Set) ===");
        printPartitionedMap(studentMap);
    }

    public static void partitioningBy_3() {
        Predicate<Student> gpaPredicate = student -> student.getGpa() >= 3.8;

        Map<Boolean, Map<String, List<String>>> studentMap = StudentDataBase.getAllStudents()
                .stream()
                .collect(partitioningBy(gpaPredicate,
                        toMap(Student::getName, Student::getActivities)));

        System.out.println("=== Partitioning + Mapping to Name -> Activities ===");
        printNestedPartitionedMap(studentMap);
    }

    // Helper method for better readability
    private static void printPartitionedMap(Map<Boolean, ? extends Object> map) {
        System.out.println("High GPA (true): ");
        System.out.println(map.get(true));
        System.out.println("\nLow GPA (false): ");
        System.out.println(map.get(false));
    }

    // Helper for deeper nested structure
    private static void printNestedPartitionedMap(Map<Boolean, Map<String, List<String>>> map) {
        System.out.println("High GPA Students:");
        map.get(true).forEach((name, activities) ->
                System.out.println("  " + name + " -> " + activities));

        System.out.println("\nLow GPA Students:");
        map.get(false).forEach((name, activities) ->
                System.out.println("  " + name + " -> " + activities));
    }

    public static void main(String[] args) {
        partitioningBy_1();
        System.out.println("\n" + "=".repeat(60) + "\n");

        partitioningBy_2();
        System.out.println("\n" + "=".repeat(60) + "\n");

        partitioningBy_3();
    }
}