package com.learnJava.functionalInterfaces;

import com.learnJava.data.Student;
import com.learnJava.data.StudentDataBase;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateStudentExample {

    static Predicate<Student> gradeLevelIsGood = (s) -> s.getGradeLevel()>=3;

    static Predicate<Student> gpaIsGood = (s) -> s.getGpa()>=3.9;

    static Function<Integer,Predicate<Integer>> gradePredicate = (toCompare) -> (number) -> number > toCompare;
    static Function<Double,Predicate<Double>>  gpaPredicate = (toCompare) -> (number) -> number > toCompare;


    public static void main(String[] args) {

        List<Student> studentList = StudentDataBase.getAllStudents();

        System.out.println("studentList -> " + studentList);
        System.out.println("studentList Len -> " + studentList.toArray().length);

        List<Student> newStudentList = studentList.stream()
                .filter(PredicateStudentExample.gradeLevelIsGood)
                .collect(Collectors.toList());

        // refined after more concepts apllied
//        List<Student> newStudentList = studentList.stream()
//                .filter(PredicateStudentExample.gradeLevelIsGood)
//                .toList();

        System.out.println("newStudentList -> " + newStudentList);
        System.out.println("newStudentList Len -> " + newStudentList.toArray().length);
        newStudentList.forEach((student -> {
            if(gpaIsGood.test(student)){
                System.out.println(student);
            }
        }));

        boolean result = gradePredicate.apply(2).test(5) && gpaPredicate.apply(3.6).test(3.9);
        System.out.println("Result from Predicate : " + result);

    }
}
