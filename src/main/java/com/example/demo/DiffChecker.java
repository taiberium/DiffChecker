package com.example.demo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiffChecker {

    public static Character findTheDifference(String initialString, String derivedString) {
        validateInput(initialString, derivedString);

        Map<Character, Long> initialCharCounterMap = fillMap(initialString);
        Map<Character, Long> derivedCharCounterMap = fillMap(derivedString);
        Map<Character, Long> resultCharCounterMap = subtractMaps(initialCharCounterMap, derivedCharCounterMap);

        return findDiffChar(resultCharCounterMap);
    }

    private static Map<Character, Long> subtractMaps(
            Map<Character, Long> initialCharCounterMap,
            Map<Character, Long> derivedCharCounterMap
    ) {
        Map<Character, Long> resultCharCounterMap = new HashMap<>();

        Set<Character> allChars = new HashSet<>();
        allChars.addAll(initialCharCounterMap.keySet());
        allChars.addAll(derivedCharCounterMap.keySet());

        for (Character character : allChars) {
            long initMapCharCounter = initialCharCounterMap.getOrDefault(character, 0L);
            long derivedMapCharCounter = derivedCharCounterMap.getOrDefault(character, 0L);
            long result = derivedMapCharCounter - initMapCharCounter;
            if (result != 0) {
                resultCharCounterMap.put(character, result);
            }
        }

        return resultCharCounterMap;
    }

    private static Character findDiffChar(Map<Character, Long> resultCharCounterMap) {
        long diffCounterValue = resultCharCounterMap.values().stream().findFirst().orElse(0L);

        if (resultCharCounterMap.size() > 1 || diffCounterValue > 1) {
            throw new IllegalArgumentException("findTheDifference find more than one different char between strings");
        }

        if (resultCharCounterMap.size() == 0 || diffCounterValue == 0) {
            throw new IllegalArgumentException("findTheDifference find no difference between strings");
        }

        return resultCharCounterMap.keySet().stream().findFirst().get();
    }

    private static Map<Character, Long> fillMap(String derivedString) {
        return derivedString.chars()
                .mapToObj(character -> (char) character)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private static void validateInput(String initialString, String derivedString) {
        if (initialString == null || derivedString == null) {
            throw new IllegalArgumentException("findTheDifference method don't support null params");
        }

        if (initialString.equals(derivedString)) {
            throw new IllegalArgumentException("findTheDifference method don't support equal params");
        }

        if (derivedString.length() < initialString.length()) {
            throw new IllegalArgumentException("findTheDifference method don't support derivedString length less than initialString");
        }

        if (derivedString.length() > initialString.length() + 1) {
            throw new IllegalArgumentException("findTheDifference method don't support" +
                    " derivedString length that differs on more than one symbol from initialString");
        }
    }
}
