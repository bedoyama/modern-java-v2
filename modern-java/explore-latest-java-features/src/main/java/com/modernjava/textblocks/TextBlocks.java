package com.modernjava.textblocks;

import java.util.SplittableRandom;

public class TextBlocks {



    public static String multiLineString() {

        var multiLine = "This is a\n" +
                "    multiline string\n" +
                "with newlines inside";

        return  multiLine;
    }

    public static String textBlockString() {

        var textBlock = """
                This is a
                    multiline string
                with newlines inside""";

        return  textBlock;
    }

    public static String textBlockWithSQLQuery() {
        return """
                SELECT *
                FROM users
                WHERE age > 18
                ORDER BY name
                """;
    }

    public static String textBlockWithFormattedName(String name) {
        return """
                Hello, %s!
                Welcome to the text blocks example.
                """.formatted(name);
    }

    public static String textBlockWithRandomNumber() {
        var random = new SplittableRandom();
        int randomNumber = random.nextInt(1, 100);
        return """
                Your random number is: %d
                """.formatted(randomNumber);
    }

    public static String textBlockWithEscapedCharacters() {
        return """
                This is a text block with escaped characters:
                - Newline: \\n
                - Tab: \\t
                - Backslash: \\\\
                """;
    }

    public static String textBlockWithUnicodeCharacters() {
        return """
                This is a text block with Unicode characters:
                - Smiley: \u263A
                - Heart: \u2764
                - Star: \u2605
                """;
    }

    public static String textBlockWithHTMLContent() {
        return """
                <html>
                    <head>
                        <title>Text Block Example</title>
                    </head>
                    <body>
                        <h1>Hello, World!</h1>
                        <p>This is an example of a text block containing HTML content.</p>
                    </body>
                </html>
                """;
    }

    public static String textBlockWithJSONContent() {
        return """
                {
                    "name": "John Doe",
                    "age": 30,
                    "email": "john.doe@example.com"
                }
                """;
    }

    public static void main(String[] args) {

        System.out.println("multiLineString = " + multiLineString());
        System.out.println("textBlockString = " + textBlockString());
        System.out.println("textBlockWithSQLQuery = " + textBlockWithSQLQuery());
        System.out.println("textBlockWithFormattedName = " + textBlockWithFormattedName("Alice"));
        System.out.println("textBlockWithRandomNumber = " + textBlockWithRandomNumber());
        System.out.println("textBlockWithEscapedCharacters = " + textBlockWithEscapedCharacters());
        System.out.println("textBlockWithUnicodeCharacters = " + textBlockWithUnicodeCharacters());
        System.out.println("textBlockWithHTMLContent = " + textBlockWithHTMLContent());
        System.out.println("textBlockWithJSONContent = " + textBlockWithJSONContent());
    }
}
