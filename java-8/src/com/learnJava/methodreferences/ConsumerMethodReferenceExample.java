package com.learnJava.methodreferences;

import com.learnJava.data.Student;
import com.learnJava.data.StudentDataBase;

import java.util.function.Consumer;

public class ConsumerMethodReferenceExample {

    /**
     * Class::instancemethod
     */
    static Consumer<Student> printStudentConsumer = System.out::println;


    /**
     * instance::instancemethod
     */
    static Consumer<Student> studentListOfActivitiesPrintC1 = (student -> student.printListOfActivities());
    static Consumer<Student> studentListOfActivitiesPrintC2 = (Student::printListOfActivities);

    public static void main(String[] args) {

        System.out.println("=== 1. Printing All Students ===");
        StudentDataBase.getAllStudents().forEach(printStudentConsumer);

        System.out.println("\n=== 2. Printing List of Activities - Version 1 ===");
        StudentDataBase.getAllStudents().forEach(studentListOfActivitiesPrintC1);

        System.out.println("\n=== 3. Printing List of Activities - Version 2 ===");
        StudentDataBase.getAllStudents().forEach(studentListOfActivitiesPrintC2);
    }

}
