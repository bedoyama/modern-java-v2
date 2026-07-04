package com.learnJava.streams;

import com.learnJava.data.Student;
import com.learnJava.data.StudentDataBase;

public class StreamMapReduceExample {

    private static int noOfNotebooks() {
        return StudentDataBase.getAllStudents().stream()
                .filter(student -> student.getGradeLevel() >= 3)
                .filter(student -> student.getGender().equals("female"))
                .map(Student::getNoteBooks)
                .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        System.out.println("No of notebooks = " + noOfNotebooks());
    }
}
