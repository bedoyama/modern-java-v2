package com.modernjava.var;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VarTypeExample {


    //var in the class properties are not allowed.
    //private var x = "abc";


    public static void main(String[] args) {

        var name = "Peter";

        IO.println("Hello, World! " + transform(name));

        var names = List.of(
                "Peter",
                "John",
                "Mary"
        );

        names.forEach(name1 -> IO.println("Hello, World! " + transform(name1)));

        var map = Map.ofEntries(
                Map.entry("Peter", 1),
                Map.entry("John", 2),
                Map.entry("Mary", 3)
        );

        map.forEach((key, value) -> IO.println("Map Entry " + transform(key) + " with value: " + value));

    }

    static String transform(String name) { // var in the function argument is not allowed

        return name.toUpperCase();

    }

}

