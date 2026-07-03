package com.learnJava.functionalInterfaces;

import com.learnJava.data.Student;
import com.learnJava.data.StudentDataBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class BiFunctionExample {

    static BiFunction<List<Student>, Predicate<Student>, Map<String, Double>> biFunction =
            (students, studentPredicate) -> {
                Map<String, Double> studentGradeMap = new HashMap<>();

                students.forEach((student -> {
                    if (studentPredicate.test(student)) {
                        studentGradeMap.put(student.getName(), student.getGpa());
                    }
                }));

                return studentGradeMap;
            };

    // Example data for the second BiFunction
    private static final Map<String, String> loginPageLocs = Map.of(
            "username", "id=username",
            "password", "id=password",
            "loginButton", "id=login"
    );

    static BiFunction<String, String, String> getLoginLocs = (locator, elementType) ->
            loginPageLocs.getOrDefault(locator, "Locator not found");

    public static void main(String[] args) {
        System.out.println("Students with good GPA:");
        System.out.println(biFunction.apply(StudentDataBase.getAllStudents(),
                PredicateStudentExample.gpaIsGood));

        System.out.println("\nStudents with good grade level:");
        System.out.println(biFunction.apply(StudentDataBase.getAllStudents(),
                PredicateStudentExample.gradeLevelIsGood));

        System.out.println("\nLogin locators:");
        System.out.println(getLoginLocs.apply("username", "elementType"));
        System.out.println(getLoginLocs.apply("password", "elementType"));
    }
}