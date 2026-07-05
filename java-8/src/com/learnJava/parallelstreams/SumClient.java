package com.learnJava.parallelstreams;

import java.util.stream.IntStream;

public class SumClient {

    public static void main(String[] args) {

        // Sequential version
        System.out.println("=== Sequential Sum ===");
        Sum sumSeq = new Sum();
        long startSeq = System.currentTimeMillis();

        IntStream.rangeClosed(1, 1000)
                .forEach(sumSeq::performSum);

        long durationSeq = System.currentTimeMillis() - startSeq;
        System.out.println("Result: " + sumSeq.getTotal());
        System.out.println("Duration: " + durationSeq + " ms\n");

        // Parallel version
        System.out.println("=== Parallel Sum ===");
        Sum sumPar = new Sum();
        long startPar = System.currentTimeMillis();

        IntStream.rangeClosed(1, 1000)
                .parallel()
                .forEach(sumPar::performSum);

        long durationPar = System.currentTimeMillis() - startPar;
        System.out.println("Result: " + sumPar.getTotal());
        System.out.println("Duration: " + durationPar + " ms");
    }
}