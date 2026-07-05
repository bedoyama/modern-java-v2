package com.learnJava.streams_terminal;

import com.learnJava.data.Student;
import com.learnJava.data.StudentDataBase;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamsMinByMaxByExample {


    public static Optional<Student> minBy() {

        Optional<Student> studentOptional = StudentDataBase.getAllStudents().stream()
                .collect(Collectors.minBy(Comparator.comparing(Student::getGpa)));

//        Optional<Student> studentOptional = StudentDataBase.getAllStudents().stream()
//                .min(Comparator.comparing(Student::getGpa));

        return studentOptional;
    }

    public static Optional<Student> maxBy() {

        Optional<Student> studentOptional = StudentDataBase.getAllStudents().stream()
                .collect(Collectors.maxBy(Comparator.comparing(Student::getGpa)));

        return studentOptional;
    }

    public static List<Student> maxByMultipleStudents() {

        List<Student> maxStudents = new ArrayList<>();
        Optional<Student> studentOptional = StudentDataBase.getAllStudents().stream()
                .collect(Collectors.maxBy(Comparator.comparing(Student::getGpa)));

        Student maxStudent = studentOptional.isPresent() ? studentOptional.get() : null;
        System.out.println("maxStudent : " + maxStudent);
        if (maxStudent != null) {

            maxStudents = StudentDataBase.getAllStudents().stream()
                    .filter(student -> maxStudent.getGpa() == student.getGpa())
                    .collect(toList());

//            System.out.println("Max Students are : " + maxStudents);
        }
        return maxStudents;
    }

    public static List<Student> maxByMultipleStudents2() {

        List<Student> maxStudents = new ArrayList<>();
        OptionalDouble studentOptional = StudentDataBase.getAllStudents().stream()
                .map(Student::getGpa)
                .mapToDouble(i -> i)
                .max();

        Double maxStudentGpa = studentOptional.isPresent() ? studentOptional.getAsDouble() : null;
        System.out.println("maxStudentGpa : " + maxStudentGpa);
        if (maxStudentGpa != null) {

            maxStudents = StudentDataBase.getAllStudents().stream()
                    .filter(student -> maxStudentGpa == student.getGpa())
                    .collect(toList());

//            System.out.println("Max Students are : " + maxStudents);
        }
        return maxStudents;
    }


    public static void main(String[] args) {

        System.out.println(minBy());
        System.out.println();

        System.out.println(maxBy());
        System.out.println();

        System.out.println("Max Students are : " + maxByMultipleStudents());
        System.out.println();

        System.out.println("Max Students are : " + maxByMultipleStudents2());
        System.out.println();
    }
}
