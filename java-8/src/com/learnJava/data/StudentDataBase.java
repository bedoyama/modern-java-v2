package com.learnJava.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class StudentDataBase {

    public static List<Student> getAllStudents() {

        /* 2nd Grade */
        Student adam = new Student("Adam", 2, 3.6, "male", 10,
                Arrays.asList("swimming", "basketball", "volleyball"));

        Student jenny = new Student("Jenny", 2, 3.8, "female", 11,
                Arrays.asList("swimming", "gymnastics", "soccer"));
        jenny.setBike(Optional.of(new Bike("Giant", "Contend AR 4")));

        Student lisa = new Student("Lisa", 2, 3.4, "female", 9,
                Arrays.asList("swimming", "piano", "chess"));   // <-- New student

        /* 3rd Grade */
        Student emily = new Student("Emily", 3, 4.0, "female", 12,
                Arrays.asList("swimming", "gymnastics", "aerobics"));

        Student dave = new Student("Dave", 3, 4.0, "male", 15,
                Arrays.asList("swimming", "gymnastics", "soccer"));
        dave.setBike(Optional.of(new Bike("Trek", "Marlin 8")));

        Student michael = new Student("Michael", 3, 3.7, "male", 13,
                Arrays.asList("swimming", "football", "tennis"));   // <-- New student

        /* 4th Grade */
        Student sophia = new Student("Sophia", 4, 3.5, "female", 10,
                Arrays.asList("swimming", "dancing", "football"));
        sophia.setBike(Optional.of(new Bike("Specialized", "Rockhopper Comp")));

        Student james = new Student("James", 4, 3.9, "male", 22,
                Arrays.asList("swimming", "basketball", "baseball", "football"));

        Student kevin = new Student("Kevin", 4, 3.2, "male", 8,
                Arrays.asList("swimming", "video games", "reading"));   // <-- New student

        return Arrays.asList(adam, jenny, lisa, emily, dave, michael, sophia, james, kevin);
    }

    // Keep existing methods for compatibility
    public static Supplier<Student> studentSupplier = () ->
            new Student("Adam", 2, 4.0, "male", Arrays.asList("swimming", "basketball", "volleyball"));

    public static Optional<Student> getOptionalStudent() {
        Student student = new Student("Adam", 2, 4.0, "male",
                Arrays.asList("swimming", "basketball", "volleyball"));
        student.setBike(Optional.of(new Bike("Client123", "Client123")));
        return Optional.of(student);
    }
}