package com.learnJava.functionalInterfaces;

import java.util.function.Predicate;

public class PredicateExample {

    static Predicate<Integer> isEvenPredicate = (i) -> {return  i%2 ==0;};

    static Predicate<Integer> isEvenPredicate1 = (i) -> i%2 ==0;

    static Predicate<Integer> divisibleBy5Predicate = (i) -> i%5 ==0;


    public static void predicateAnd(){

        System.out.println("Result in predicateAnd : " + isEvenPredicate1.and(divisibleBy5Predicate).test(10));
    }

    public static void predicateOr(){

        System.out.println("Result in predicateOr : " + isEvenPredicate1.or(divisibleBy5Predicate).test(4));
    }

    public static void predicateNegate(){

        System.out.println("Result in predicateNegate : " + isEvenPredicate1.and(divisibleBy5Predicate).negate().test(4)); //equivalent to reversing the result
    }


    public static void main(String[] args) {

        System.out.println("Result is p : " + isEvenPredicate.test(2));

        System.out.println("Result is p1 : " + isEvenPredicate1.test(3));

        predicateAnd();

        predicateOr();

        predicateNegate();

    }


}
