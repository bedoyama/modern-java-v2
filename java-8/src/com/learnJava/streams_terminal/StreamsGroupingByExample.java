package com.learnJava.streams_terminal;

import com.learnJava.data.Student;
import com.learnJava.data.StudentDataBase;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

public class StreamsGroupingByExample {

    // 1. Simple grouping by one property
    public static void groupingByGender() {
        Map<String, List<Student>> studentMap = StudentDataBase.getAllStudents()
                .stream()
                .collect(Collectors.groupingBy(Student::getGender));

        System.out.println("Students grouped by Gender:");
        System.out.println(studentMap);
    }

    // 2. Grouping with custom classification
    public static void groupByGrade() {
        Map<String, List<Student>> studentMap = StudentDataBase.getAllStudents()
                .stream()
                .collect(Collectors.groupingBy(student ->
                        student.getGpa() >= 3.8 ? "OUTSTANDING" : "AVERAGE"));

        System.out.println("Students grouped by Grade Level:");
        System.out.println(studentMap);
    }

    // 3. Two-level grouping
    public static void twoLevelGrouping() {
        Map<Integer, Map<String, List<Student>>> studentMap =
                StudentDataBase.getAllStudents().stream()
                        .collect(groupingBy(Student::getGradeLevel,
                                groupingBy(student -> student.getGpa() >= 3.8 ? "OUTSTANDING" : "AVERAGE")));

        System.out.println("Two-level grouping (Grade Level -> Performance):");
        System.out.println(studentMap);
    }

    // 4. Grouping + aggregation (sum)
    public static void twoLevelGrouping_2() {
        Map<String, Integer> nameNoteBooksMap = StudentDataBase.getAllStudents().stream()
                .collect(groupingBy(Student::getName, summingInt(Student::getNoteBooks)));

        System.out.println("Total notebooks per student:");
        System.out.println(nameNoteBooksMap);
    }

    // 5. Grouping into Set instead of List
    public static void twoLevelGrouping_3() {
        Map<String, Set<Student>> nameNoteBooksMap = StudentDataBase.getAllStudents().stream()
                .collect(groupingBy(Student::getName, toSet()));

        System.out.println("Students grouped by name into Set:");
        System.out.println(nameNoteBooksMap);
    }

    // 6. Three-argument groupingBy (control map type)
    public static void threeArgumentGroupingBy() {
        LinkedHashMap<String, Set<Student>> studentMap = StudentDataBase.getAllStudents().stream()
                .collect(groupingBy(Student::getName, LinkedHashMap::new, toSet()));

        System.out.println("Preserving insertion order with LinkedHashMap:");
        System.out.println(studentMap);
    }

    // 7. Finding top student per group
    public static void calculateTopGpaStudentInEachGrade() {
        Map<Integer, Optional<Student>> topGpaOptional = StudentDataBase.getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,
                        maxBy(Comparator.comparingDouble(Student::getGpa))));

        System.out.println("Top GPA student per grade (with Optional):");
        System.out.println(topGpaOptional);

        // Cleaner version without Optional in result
        Map<Integer, Student> topGpa = StudentDataBase.getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,
                        collectingAndThen(maxBy(Comparator.comparingDouble(Student::getGpa)), Optional::get)));

        System.out.println("\nTop GPA student per grade:");
        System.out.println(topGpa);
    }

    // 8. Finding lowest GPA student per group
    public static void calculateLeastGpaStudentInEachGrade() {
        Map<Integer, Student> leastGpa = StudentDataBase.getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,
                        collectingAndThen(minBy(Comparator.comparingDouble(Student::getGpa)), Optional::get)));

        System.out.println("Lowest GPA student per grade:");
        System.out.println(leastGpa);
    }

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);

//        System.out.println("Choose an example to run:");
//        System.out.println("1. Grouping by Gender");
//        System.out.println("2. Grouping by Grade");
//        System.out.println("3. Two Level Grouping");
//        System.out.println("4. Grouping + Sum");
//        System.out.println("5. Grouping into Set");
//        System.out.println("6. Three Argument GroupingBy");
//        System.out.println("7. Top GPA per Grade");
//        System.out.println("8. Lowest GPA per Grade");
//        System.out.print("Enter choice (1-8): ");

//        int choice = scanner.nextInt();
        int choice = 7;

        switch (choice) {
            case 1 -> groupingByGender();
            case 2 -> groupByGrade();
            case 3 -> twoLevelGrouping();
            case 4 -> twoLevelGrouping_2();
            case 5 -> twoLevelGrouping_3();
            case 6 -> threeArgumentGroupingBy();
            case 7 -> calculateTopGpaStudentInEachGrade();
            case 8 -> calculateLeastGpaStudentInEachGrade();
            default -> System.out.println("Invalid choice!");
        }
    }
}