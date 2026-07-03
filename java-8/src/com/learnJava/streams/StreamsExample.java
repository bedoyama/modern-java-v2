package com.learnJava.streams;

import com.learnJava.data.Student;
import com.learnJava.data.StudentDataBase;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamsExample {

    public static void main(String[] args) {

        Predicate<Student> gradePredicate = student -> student.getGradeLevel() >= 3;
        Predicate<Student> gpaPredicate = student -> student.getGpa() >= 3.9;

/*
        List<String> names = Arrays.asList("adam","dan","jenny");
        names.stream();
        names.parallelStream();
        StudentDataBase.getAllStudents().stream();
        StudentDataBase.getAllStudents().parallelStream();
*/


//        Map<String, List<String>> studentMap = StudentDataBase.getAllStudents().stream() //.parallelStream dont forger.
//                .filter(gpaPredicate) // Stream<Student>
//                .filter(gradePredicate)
//                .collect(Collectors.toMap(Student::getName, Student::getActivities));

        // debug 1
//        Map<String, List<String>> studentMap = StudentDataBase.getAllStudents().stream() //.parallelStream dont forger.
//                .peek(System.out::println)
//                .filter(gpaPredicate) // Stream<Student>
//                .peek(x -> {
//                    System.out.println("GPA" + x);
//                })
//                .filter(gradePredicate)
//                .peek(x -> {
//                    System.out.println("GPA and Grade" + x);
//                })
//                .collect(Collectors.toMap(Student::getName, Student::getActivities));

        // debug 2
        Map<String, List<String>> studentMap = StudentDataBase.getAllStudents().stream() //.parallelStream dont forger.
                .peek(x -> {
                    System.out.println("AllR " + x.getName());
                })
                .filter(gradePredicate) // Stream<Student>
                .peek(x -> {
                    System.out.println("Grade only --- " + x.getName());
                })
                .filter(gpaPredicate)
                .peek(x -> {
                    System.out.println("GPA and Grade +++ " + x.getName());
                })
                .collect(Collectors.toMap(Student::getName, Student::getActivities));

        System.out.println("studentMap  : " + studentMap);

//        List<String> studentActivities = StudentDataBase.getAllStudents().
//                stream() // Stream<Student>
//                .map(Student::getActivities) //<Stream<List<Activites>>
//                .flatMap(List::stream) //<Stream<String>
//                .distinct() // removes duplicates
//                .collect(Collectors.toList()); //collects it to a list.
//
//        List<String> namesList = StudentDataBase.getAllStudents().
//                stream() // Stream<Student>
//                .peek((student -> {
//                    System.out.println(student);
//                }))
//                .map(Student::getName) //<Stream<List<Activites>>
//                .peek(System.out::println)
//                .distinct() // removes duplicates
//                .collect(Collectors.toList()); //collects it to a list.

//        System.out.println("namesList  : " + namesList);


    }
}
