package com.learnJava.methodreferences;

import com.learnJava.data.Student;
import com.learnJava.data.StudentDataBase;

import java.util.function.Supplier;

public class SupplierMethodReferenceExample {

    // Constructor Reference - Student::new
    static Supplier<Student> studentSupplier = Student::new;

    public static void main(String[] args) {

        System.out.println("=== Creating a new Student using Constructor Reference ===");

        // Using the Supplier to create a fresh Student object
        Student newStudent = studentSupplier.get();

        System.out.println("New Student created: " + newStudent);

        // More realistic usage: Creating and then modifying the student
        newStudent.setName("Elrich Backmann");
        newStudent.setGpa(3.8);
        newStudent.setGradeLevel(12);

        System.out.println("After setting properties: " + newStudent);
        System.out.println("GPA: " + newStudent.getGpa());
    }
}