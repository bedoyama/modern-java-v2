package com.learnJava.streams;


import com.learnJava.data.Student;
import com.learnJava.data.StudentDataBase;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamsExercises {

    // Exercise 1: List of student names with GPA appended
    // Input: List<Student> students
    // Output: List<String> where each element is in format "name - GPA"
    // Example: ["Jenny - 3.8", "Mike - 3.9"]
    // Note: The GPA should be included as is, without rounding
    public static List<String> namesWithGpa(List<Student> students) {
        // TODO: Implement using streams

        return students.stream()
                .map((s) -> s.getName() + " - " + s.getGpa())
                .collect(Collectors.toList());
    }

    // Exercise 2: Set of all unique student activities
    // Input: List<Student> students where each student has a list of activities
    // Output: Set<String> containing all unique activities across all students
    // Example: ["swimming", "basketball", "volleyball", "dancing"]
    // Note: Duplicates should be automatically removed
    public static Set<String> uniqueActivities(List<Student> students) {
        // TODO: Implement using streams
        return students.stream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }

    // Exercise 3: Map of student name to their activity count
    // Input: List<Student> students
    // Output: Map<String, Integer> where key is student name and value is number of activities
    // Example: {"Jenny": 3, "Mike": 2} where numbers represent activity count
    // Note: Students with no activities should have count 0
    public static Map<String, Integer> nameToActivityCount(List<Student> students) {
        // TODO: Implement using streams
//        return students.stream() // Stream<Student>
//                .collect(Collectors.toMap(Student::getName, student -> {
//                    return student.getActivities().size();
//                }));
        return students.stream() // Stream<Student>
                .collect(Collectors.toMap(Student::getName, student -> student.getActivities().size()));
    }

    // Exercise 4: List of names of students with more than 2 notebooks
// Input: List<Student> students
// Output: List<String> containing names of students with notebooks > 2
// Example: If Jenny has 3 notebooks and Mike has 2, return ["Jenny"]
// Note: Order should be preserved as in input list
    public static List<String> namesWithMoreThanTwoNotebooks(List<Student> students) {
        // TODO: Implement using streams
        return students.stream() // Stream<Student>
                .filter(student -> {
                    return student.getNoteBooks() > 2;
                })
                .map(Student::getName)
                .collect(Collectors.toList());
    }

    // Exercise 5: Map of student name to bike model
// Input: List<Student> students
// Output: Map<String, String> where key is student name and value is bike model or "No Bike"
// Example: {"Jenny": "BMX", "Mike": "No Bike"}
// Note: Use "No Bike" as default value when bike information is missing
    public static Map<String, String> nameToBikeModel(List<Student> students) {
        // TODO: Implement using streams
        return null;
    }

    // Exercise 6: List of student names with GPA above 3.5
// Input: List<Student> students
// Output: List<String> containing names of students with GPA > 3.5
// Example: If Jenny has 3.8 GPA and Mike has 3.2, return ["Jenny"]
// Note: Only include names, not GPAs, in the output list
    public static List<String> namesWithHighGpa(List<Student> students) {
        // TODO: Implement using streams
        return students.stream()
                .filter(student -> student.getGpa() > 3.5)
                .map(Student::getName)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Student> students = StudentDataBase.getAllStudents();
        System.out.println("Names with GPA: " + namesWithGpa(students));
        System.out.println("Unique Activities: " + uniqueActivities(students));
        System.out.println("Name to Activity Count: " + nameToActivityCount(students));
        System.out.println("Names with >2 Notebooks: " + namesWithMoreThanTwoNotebooks(students));
        System.out.println("Name to Bike Model: " + nameToBikeModel(students));
        System.out.println("Names with High GPA: " + namesWithHighGpa(students));
    }
}
