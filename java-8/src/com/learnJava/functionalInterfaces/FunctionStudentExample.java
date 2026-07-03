package com.learnJava.functionalInterfaces;

import com.learnJava.data.Student;
import com.learnJava.data.StudentDataBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionStudentExample {

    static Function<List<Student>, Map<String, Double>> getGoodStudents = (students -> {

        Map<String, Double> studentGradeMap = new HashMap<>();
        students.forEach((student -> {

            if (PredicateStudentExample.gpaIsGood.test(student)) {
                studentGradeMap.put(student.getName(), student.getGpa());
            }
        }));

        return studentGradeMap;

    });

    static Function<List<Student>, Map<String, Double>> getAllStudents = (students -> {

        Map<String, Double> studentGradeMap = new HashMap<>();
        students.forEach((student -> studentGradeMap.put(student.getName(), student.getGpa())));

        return studentGradeMap;

    });

    // Later in the course
    static Function<List<Student>, Map<String, Integer>> getGoodStudents2 =
            students -> students.stream()
                    .filter(PredicateStudentExample.gradeLevelIsGood)
                    .collect(Collectors.toMap(Student::getName, Student::getGradeLevel));

    static Function<List<Student>, Map<String, Integer>> getAllStudents2 =
            students -> students.stream()
                    .collect(Collectors.toMap(Student::getName, Student::getGradeLevel));

    public static void main(String[] args) {

        System.out.println(getAllStudents.apply(StudentDataBase.getAllStudents()));
        System.out.println(getGoodStudents.apply(StudentDataBase.getAllStudents()));

        System.out.println("------------------------------------");
        System.out.println(getAllStudents2.apply(StudentDataBase.getAllStudents()));
        System.out.println(getGoodStudents2.apply(StudentDataBase.getAllStudents()));

    }
}
