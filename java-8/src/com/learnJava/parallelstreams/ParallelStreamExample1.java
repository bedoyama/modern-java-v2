package com.learnJava.parallelstreams;

import com.learnJava.data.Student;
import com.learnJava.data.StudentDataBase;

import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

public class ParallelStreamExample1 {


    public static long checkPerformanceResult(Supplier<Integer> sum, int numberOfTimes) {

        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfTimes; i++) {
            sum.get();
        }

        long end = System.currentTimeMillis();
        return end - start;
    }

    public static Integer sequentialStream() {

//        long start = System.currentTimeMillis();
        List<String> studentActivities = StudentDataBase.getAllStudents()
                .stream()
                .map(Student::getActivities) //Stream<List<String>>
                .flatMap(List::stream) //<Stream<String>
                .collect(toList());

//        long duration = System.currentTimeMillis() - start;
//        System.out.println("Duration in sequential stream : " + duration);

        return studentActivities.size();
    }

    public static Integer parallelStream() {

//        long start = System.currentTimeMillis();
        List<String> studentActivities = StudentDataBase.getAllStudents()
                .parallelStream()
                .map(Student::getActivities) //Stream<List<String>>
                .flatMap(List::stream) //<Stream<String>
                .collect(toList());

//        long duration = System.currentTimeMillis() - start;
//        System.out.println("Duration in parallel stream : " + duration);

        return studentActivities.size();

    }


    public static void main(String[] args) {


//        System.out.println("sequentialStream : " + sequentialStream());
//
//        System.out.println("parallelStream : " + parallelStream());

        System.out.println("# of available processors: " + Runtime.getRuntime().availableProcessors());

        long sequentialDuration = checkPerformanceResult(ParallelStreamExample1::sequentialStream, 100);
        System.out.println("sequentialDuration in MilliSeconds: " + sequentialDuration);
        //System.out.println("sequentialDuration in MilliSecs : " + TimeUnit.NANOSECONDS.toMillis(sequentialDuration));
        long parallelDuration = checkPerformanceResult(ParallelStreamExample1::parallelStream, 100);

        System.out.println("parallelDuration in MilliSeconds: " + parallelDuration);
        //  System.out.println("parallelDuration in MilliSecs : " + TimeUnit.NANOSECONDS.toMillis(parallelDuration));


    }
}
