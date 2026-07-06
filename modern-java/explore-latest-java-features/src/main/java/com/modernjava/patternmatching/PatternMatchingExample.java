package com.modernjava.patternmatching;

public class PatternMatchingExample {

    public String pattern(Object o) {
        if (o instanceof Integer) {
            var i = (Integer) o;
            return "Integer:" + i;
        }
        if (o instanceof String) {
            var i = (String) o;
            return "String of length:" + i.length();
        }
        return "Not a String or Integer";

    }

    public String patternWithBindingVariable(Object o) {
        if (o instanceof Integer i) {
            return "Integer:" + i;
        }
        if (o instanceof String s) {
            return "String of length:" + s.length();
        }
        return "Not a String or Integer with binding variable";
    }

    public String patternWithGuard(Object o) {
        if (o instanceof Integer i && i > 10) {
            return "Integer greater than 10:" + i;
        }
        if (o instanceof String s && s.length() > 5) {
            return "String of length greater than 5:" + s.length();
        }
        return "Not a String or Integer with guard";
    }

    public String patternMatchingWithSwitch(Object o) {
        return switch (o) {
            case Integer i -> "Integer:" + i;
            case String s -> "String of length:" + s.length();
            case Number n -> "Number:" + n;
            case null, default -> "Not a String or Integer with switch";
        };
    }
}
