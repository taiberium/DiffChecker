package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class DemoApplicationTests {

    static Stream<Arguments> successTestInputParamsProvider() {
        return Stream.of(
                Arguments.of("bcefh", "bcefgh", 'g'),
                Arguments.of("abcd", "abcde", 'e'),
                Arguments.of("aiou", "aeiou", 'e'),
                Arguments.of("mnoqrst", "stmnporq", 'p'),
                Arguments.of("aaabbbbccd", "aaaabbbbccd", 'a'),
                Arguments.of("", "a", 'a'),
                Arguments.of("a", "Aa", 'A')
        );
    }

    @ParameterizedTest
    @MethodSource("successTestInputParamsProvider")
    void testSuccessScenarios(String initialString, String derivedString, Character expectedResult) {

        Character resultChar = DiffChecker.findTheDifference(initialString, derivedString);

        Assertions.assertEquals(expectedResult, resultChar);
    }

    static Stream<Arguments> exceptionTestInputParamsProvider() {
        return Stream.of(
                Arguments.of("aaabbbbccd", "aaabbbbccd"),  // no DiffScenario
                Arguments.of("", ""), // empty InputParams Scenario
                Arguments.of(" ", " "), // blank InputParams Scenario
                Arguments.of(null, null), // null InputParams Scenario
                Arguments.of("abcd", "abc"), // derivedString Less Than InitString
                Arguments.of("abcd", "abc"), // derivedString Much Bigger Than InitString
                Arguments.of("abv", "abcd") // initial String Contain more than one Diff Chars
        );
    }

    @ParameterizedTest
    @MethodSource("exceptionTestInputParamsProvider")
    void testExceptionScenarios(String initialString, String derivedString) {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> DiffChecker.findTheDifference(initialString, derivedString));
    }
}
