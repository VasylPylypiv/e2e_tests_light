package io.testomat.e2e_tests_light.utils;

import org.jetbrains.annotations.NotNull;

public class StringParsers {

    @NotNull
    public static Integer parseIntegerFromString(String countOfTests) {
        String totalProjectsText = countOfTests.replaceAll("\\D+", "");
        return Integer.parseInt(totalProjectsText);
    }

    public static Double parseDoubleFromString(String targetText) {
        String doubleText = targetText.replaceAll("[^\\d.]", ""); // Keeps only digits and the decimal point
        return Double.parseDouble(doubleText);
    }
}
